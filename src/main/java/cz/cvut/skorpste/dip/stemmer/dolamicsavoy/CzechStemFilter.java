package cz.cvut.skorpste.dip.stemmer.dolamicsavoy;

import cz.cvut.skorpste.dip.stemmer.dolamicsavoy.impl.CzechAgressiveStemmer;
import cz.cvut.skorpste.dip.stemmer.dolamicsavoy.impl.CzechLightStemmer;
import cz.cvut.skorpste.dip.stemmer.dolamicsavoy.impl.CzechStemmer;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.KeywordAttribute;

import java.io.IOException;


/**
 * A {@link org.apache.lucene.analysis.TokenFilter} that applies {@link org.apache.lucene.analysis.cz.CzechStemmer} to stem Czech words using Dolamic's implementation.
 * <p><b>NOTE</b>: Input is expected to be in lowercase,
 * but with diacritical marks</p>
 */
public final class CzechStemFilter extends TokenFilter {
    private final CzechStemmer stemmer;
    private final CharTermAttribute termAtt = addAttribute(CharTermAttribute.class);
    private final KeywordAttribute keywordAttr = addAttribute(KeywordAttribute.class);

    public CzechStemFilter(TokenStream input, CzechStemmer stemmer) {
        super(input);
        this.stemmer=stemmer;
    }

    @Override
    public boolean incrementToken() throws IOException {
        if (input.incrementToken()) {
            if (!keywordAttr.isKeyword()) {
                String string=new String(termAtt.buffer()).substring(0,termAtt.length());
                //System.out.println("|"+termAtt.length()+"|");
                //System.out.println(">"+string+">");
                //System.out.println("#"+string.length()+"#");
                string=stemmer.stem(string);
                //System.out.println("<"+string+"<");
                //System.out.println("#"+string.length()+"#");
                termAtt.setLength(string.length());
            }
            return true;
        } else {
            return false;
        }
    }
}
