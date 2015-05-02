package cz.cvut.skorpste.dip.stemmer;

import org.apache.lucene.analysis.BaseTokenStreamTestCase;

/**
 * Created by stopka on 4.4.15.
 */
abstract public class BaseTestCase extends BaseTokenStreamTestCase {
    abstract CzechAnalyzer.StemmerImpl getStemmerImpl();
    protected CzechAnalyzer createCzechAnalyzer(){
        return new CzechAnalyzer(getStemmerImpl());
    }

    public void testRandomStrings() throws Exception {
        checkRandomData(random(), createCzechAnalyzer(), 1000*RANDOM_MULTIPLIER);
    }
}
