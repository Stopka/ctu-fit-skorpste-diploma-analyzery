package cz.cvut.skorpste.dip.stemmer.dolamicsavoy;


import cz.cvut.skorpste.dip.stemmer.dolamicsavoy.impl.CzechStemmer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.util.ResourceLoader;
import org.apache.lucene.analysis.util.ResourceLoaderAware;
import org.apache.lucene.analysis.util.TokenFilterFactory;

import java.io.IOException;
import java.util.Map;

/**
 * Factory for {@link org.apache.lucene.analysis.cz.CzechStemFilter}.
 * <pre class="prettyprint">
 * &lt;fieldType name="text_cz_agressive" class="solr.TextField" positionIncrementGap="100"&gt;
 * &lt;analyzer&gt;
 * &lt;tokenizer class="solr.StandardTokenizerFactory"/&gt;
 * &lt;filter class="solr.LowerCaseFilterFactory"/&gt;
 * &lt;filter class="cz.cvut.skorpste.dip.stemmer.dolamicsavoy.CzechStemFilterFactory" implementation="Agressive"/&gt;
 * &lt;/analyzer&gt;
 * &lt;/fieldType&gt;
 * &lt;fieldType name="text_cz_light" class="solr.TextField" positionIncrementGap="100"&gt;
 * &lt;analyzer&gt;
 * &lt;tokenizer class="solr.StandardTokenizerFactory"/&gt;
 * &lt;filter class="solr.LowerCaseFilterFactory"/&gt;
 * &lt;filter class="cz.cvut.skorpste.dip.stemmer.dolamicsavoy.CzechStemFilterFactory" implementation="Light"/&gt;
 * &lt;/analyzer&gt;
 * &lt;/fieldType&gt;
 * </pre>
 */
public class CzechStemFilterFactory extends TokenFilterFactory implements ResourceLoaderAware {
    private final String implementation;
    private Class<? extends CzechStemmer> stemClass;
    /**
     * Creates a new CzechStemFilterFactory
     * @param args key-value pairs of parameters to change behavior
     */
    public CzechStemFilterFactory(Map<String, String> args) {
        super(args);
        implementation = get(args, "implementation", "Light");
        if (!args.isEmpty()) {
            throw new IllegalArgumentException("Unknown parameters: " + args);
        }
    }


    public void inform(ResourceLoader loader) throws IOException {
        String className = "cz.cvut.skorpste.dip.stemmer.dolamicsavoy.impl.Czech" + implementation + "Stemmer";

        stemClass = loader.findClass(className, CzechStemmer.class);
    }

    @Override
    public TokenStream create(TokenStream input) {
        CzechStemmer stemmer;
        try {
            stemmer = stemClass.newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Error instantiating stemmer" + implementation + "from class " + stemClass, e);
        }

        return new CzechStemFilter(input, stemmer);
    }
}
