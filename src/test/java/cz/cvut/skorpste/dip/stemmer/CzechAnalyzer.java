package cz.cvut.skorpste.dip.stemmer;

import cz.cvut.skorpste.dip.stemmer.dolamicsavoy.impl.CzechAgressiveStemmer;
import cz.cvut.skorpste.dip.stemmer.dolamicsavoy.impl.CzechLightStemmer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.core.LowerCaseFilter;
import org.apache.lucene.analysis.core.StopFilter;
import org.apache.lucene.analysis.snowball.SnowballFilter;
import org.apache.lucene.analysis.standard.StandardFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.util.CharArraySet;
import org.apache.lucene.analysis.util.StopwordAnalyzerBase;
import org.apache.lucene.analysis.util.WordlistLoader;
import org.apache.lucene.util.IOUtils;
import cz.cvut.skorpste.dip.stemmer.dolamicsavoy.CzechStemFilter;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

public final class CzechAnalyzer extends StopwordAnalyzerBase {
    public static final String DEFAULT_STOPWORD_FILE = "stopwords.txt";
    private final CharArraySet stemExclusionTable;

    public static final CharArraySet getDefaultStopSet() {
        return CzechAnalyzer.DefaultSetHolder.DEFAULT_SET;
    }

    public enum StemmerImpl{
        AGRESSIVE,
        LIGHT,
        HELEBRAND
    }

    StemmerImpl si=StemmerImpl.HELEBRAND;

    public CzechAnalyzer(StemmerImpl si) {
        this(CharArraySet.EMPTY_SET);
        this.si=si;
    }

    public CzechAnalyzer() {
        this(CzechAnalyzer.DefaultSetHolder.DEFAULT_SET);
    }

    public CzechAnalyzer(CharArraySet stopwords) {
        this(stopwords, CharArraySet.EMPTY_SET);
    }

    public CzechAnalyzer(CharArraySet stopwords, CharArraySet stemExclusionTable) {
        super(stopwords);
        this.stemExclusionTable = CharArraySet.unmodifiableSet(CharArraySet.copy(stemExclusionTable));
    }

    protected TokenStreamComponents createComponents(String fieldName, Reader reader) {
        StandardTokenizer source = new StandardTokenizer(this.getVersion(), reader);
        StandardFilter result = new StandardFilter(this.getVersion(), source);
        LowerCaseFilter result1 = new LowerCaseFilter(this.getVersion(), result);
        Object result2 = new StopFilter(this.getVersion(), result1, this.stopwords);
        switch (si){
            case AGRESSIVE:
                result2 = new CzechStemFilter((TokenStream) result2,new CzechAgressiveStemmer());
                break;
            case LIGHT:
                result2 = new CzechStemFilter((TokenStream) result2,new CzechLightStemmer());
                break;
            case HELEBRAND:
                result2 = new SnowballFilter((TokenStream) result2, "CzechHelebrand");
                break;
        }
        return new TokenStreamComponents(source, (TokenStream) result2);
    }

    private static class DefaultSetHolder {
        private static final CharArraySet DEFAULT_SET;

        private DefaultSetHolder() {
        }

        static {
            try {
                DEFAULT_SET = WordlistLoader.getWordSet(IOUtils.getDecodingReader(CzechAnalyzer.class, "stopwords.txt", StandardCharsets.UTF_8), "#");
            } catch (IOException var1) {
                throw new RuntimeException("Unable to load default stopword set");
            }
        }
    }
}


