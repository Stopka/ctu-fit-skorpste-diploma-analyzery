package cz.cvut.skorpste.dip.stemmer;

/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.MockTokenizer;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.KeywordTokenizer;
import org.apache.lucene.analysis.cz.CzechStemFilter;
import org.apache.lucene.analysis.miscellaneous.SetKeywordMarkerFilter;
import org.apache.lucene.analysis.util.CharArraySet;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

/**
 * Test the Czech Stemmer.
 * 
 * Note: its algorithmic, so some stems are nonsense
 *
 */
public class TestLightStemmer extends BaseTestCase {
  
  /**
   * Test showing how masculine noun forms conflate
   */
  public void testMasculineNouns() throws IOException {
    CzechAnalyzer cz = createCzechAnalyzer();
    
    /* animate ending with a hard consonant */
    assertAnalyzesTo(cz, "pán", new String[] { "pán" });
    assertAnalyzesTo(cz, "páni", new String[] { "pán" });
    assertAnalyzesTo(cz, "pánové", new String[] { "pán" });
    assertAnalyzesTo(cz, "pána", new String[] { "pán" });
    assertAnalyzesTo(cz, "pánů", new String[] { "pán" });
    assertAnalyzesTo(cz, "pánovi", new String[] { "pán" });
    assertAnalyzesTo(cz, "pánům", new String[] { "pán" });
    assertAnalyzesTo(cz, "pány", new String[] { "pán" });
    assertAnalyzesTo(cz, "páne", new String[] { "pán" });
    assertAnalyzesTo(cz, "pánech", new String[] { "pán" });
    assertAnalyzesTo(cz, "pánem", new String[] { "pán" });
    
    /* inanimate ending with hard consonant */
    assertAnalyzesTo(cz, "hrad", new String[] { "hrad" });
    assertAnalyzesTo(cz, "hradu", new String[] { "hrad" });
    assertAnalyzesTo(cz, "hrade", new String[] { "hrad" });
    assertAnalyzesTo(cz, "hradem", new String[] { "hrad" });
    assertAnalyzesTo(cz, "hrady", new String[] { "hrad" });
    assertAnalyzesTo(cz, "hradech", new String[] { "hrad" });
    assertAnalyzesTo(cz, "hradům", new String[] { "hrad" });
    assertAnalyzesTo(cz, "hradů", new String[] { "hrad" });
    
    /* animate ending with a soft consonant */
    assertAnalyzesTo(cz, "muž", new String[] { "muž" });
    assertAnalyzesTo(cz, "muži", new String[] { "muž" });
    assertAnalyzesTo(cz, "muže", new String[] { "muž" });
    assertAnalyzesTo(cz, "mužů", new String[] { "muž" });
    assertAnalyzesTo(cz, "mužům", new String[] { "muž" });
    assertAnalyzesTo(cz, "mužích", new String[] { "muž" });
    assertAnalyzesTo(cz, "mužem", new String[] { "muž" });
    
    /* inanimate ending with a soft consonant */
    assertAnalyzesTo(cz, "stroj", new String[] { "stroj" });
    assertAnalyzesTo(cz, "stroje", new String[] { "stroj" });
    assertAnalyzesTo(cz, "strojů", new String[] { "stroj" });
    assertAnalyzesTo(cz, "stroji", new String[] { "stroj" });
    assertAnalyzesTo(cz, "strojům", new String[] { "stroj" });
    assertAnalyzesTo(cz, "strojích", new String[] { "stroj" });
    assertAnalyzesTo(cz, "strojem", new String[] { "stroj" });
    
    /* ending with a */
    assertAnalyzesTo(cz, "předseda", new String[] { "předsed" });
    assertAnalyzesTo(cz, "předsedové", new String[] { "předsed" });
    assertAnalyzesTo(cz, "předsedy", new String[] { "předsed" });
    assertAnalyzesTo(cz, "předsedů", new String[] { "předsed" });
    assertAnalyzesTo(cz, "předsedovi", new String[] { "předsed" });
    assertAnalyzesTo(cz, "předsedům", new String[] { "předsed" });
    assertAnalyzesTo(cz, "předsedu", new String[] { "předsed" });
    assertAnalyzesTo(cz, "předsedo", new String[] { "předsed" });
    assertAnalyzesTo(cz, "předsedech", new String[] { "předsed" });
    assertAnalyzesTo(cz, "předsedou", new String[] { "předsed" });
    
    /* ending with e */
    assertAnalyzesTo(cz, "soudce", new String[] { "soudc" });
    assertAnalyzesTo(cz, "soudci", new String[] { "soudc" });
    assertAnalyzesTo(cz, "soudců", new String[] { "soudc" });
    assertAnalyzesTo(cz, "soudcům", new String[] { "soudc" });
    assertAnalyzesTo(cz, "soudcích", new String[] { "soudc" });
    assertAnalyzesTo(cz, "soudcem", new String[] { "soudc" });
  }
  
  /**
   * Test showing how feminine noun forms conflate
   */
  public void testFeminineNouns() throws IOException {
    CzechAnalyzer cz = createCzechAnalyzer();
    
    /* ending with hard consonant */
    assertAnalyzesTo(cz, "kost", new String[] { "kost" });
    assertAnalyzesTo(cz, "kosti", new String[] { "kost" });
    assertAnalyzesTo(cz, "kostí", new String[] { "kost" });
    assertAnalyzesTo(cz, "kostem", new String[] { "kost" });
    assertAnalyzesTo(cz, "kostech", new String[] { "kost" });
    assertAnalyzesTo(cz, "kostmi", new String[] { "kost" });
    
    /* ending with a soft consonant */
    // note: in this example sing nom. and sing acc. don't conflate w/ the rest
    assertAnalyzesTo(cz, "píseň", new String[] { "píseň" });
    assertAnalyzesTo(cz, "písně", new String[] { "písn" });
    assertAnalyzesTo(cz, "písni", new String[] { "písn" });
    assertAnalyzesTo(cz, "písněmi", new String[] { "písn" });
    assertAnalyzesTo(cz, "písních", new String[] { "písn" });
    assertAnalyzesTo(cz, "písním", new String[] { "pís" });
    
    /* ending with e */
    assertAnalyzesTo(cz, "růže", new String[] { "růž" });
    assertAnalyzesTo(cz, "růží", new String[] { "růž" });
    assertAnalyzesTo(cz, "růžím", new String[] { "rů" });
    assertAnalyzesTo(cz, "růžích", new String[] { "růž" });
    assertAnalyzesTo(cz, "růžemi", new String[] { "růž" });
    assertAnalyzesTo(cz, "růži", new String[] { "růž" });
    
    /* ending with a */
    assertAnalyzesTo(cz, "žena", new String[] { "žen" });
    assertAnalyzesTo(cz, "ženy", new String[] { "žen" });
    assertAnalyzesTo(cz, "žen", new String[] { "žen" });
    assertAnalyzesTo(cz, "ženě", new String[] { "žen" });
    assertAnalyzesTo(cz, "ženám", new String[] { "žen" });
    assertAnalyzesTo(cz, "ženu", new String[] { "žen" });
    assertAnalyzesTo(cz, "ženo", new String[] { "žen" });
    assertAnalyzesTo(cz, "ženách", new String[] { "žen" });
    assertAnalyzesTo(cz, "ženou", new String[] { "žen" });
    assertAnalyzesTo(cz, "ženami", new String[] { "žen" });
  }

  /**
   * Test showing how neuter noun forms conflate
   */
  public void testNeuterNouns() throws IOException {
    CzechAnalyzer cz = createCzechAnalyzer();
    
    /* ending with o */
    assertAnalyzesTo(cz, "město", new String[] { "měst" });
    assertAnalyzesTo(cz, "města", new String[] { "měst" });
    assertAnalyzesTo(cz, "měst", new String[] { "měst" });
    assertAnalyzesTo(cz, "městu", new String[] { "měst" });
    assertAnalyzesTo(cz, "městům", new String[] { "měst" });
    assertAnalyzesTo(cz, "městě", new String[] { "měst" });
    assertAnalyzesTo(cz, "městech", new String[] { "měst" });
    assertAnalyzesTo(cz, "městem", new String[] { "měst" });
    assertAnalyzesTo(cz, "městy", new String[] { "měst" });
    
    /* ending with e */
    assertAnalyzesTo(cz, "moře", new String[] { "moř" });
    assertAnalyzesTo(cz, "moří", new String[] { "moř" });
    assertAnalyzesTo(cz, "mořím", new String[] { "mo" });
    assertAnalyzesTo(cz, "moři", new String[] { "moř" });
    assertAnalyzesTo(cz, "mořích", new String[] { "moř" });
    assertAnalyzesTo(cz, "mořem", new String[] { "moř" });

    /* ending with ě */
    assertAnalyzesTo(cz, "kuře", new String[] { "kuř" });
    assertAnalyzesTo(cz, "kuřata", new String[] { "kuř" });
    assertAnalyzesTo(cz, "kuřete", new String[] { "kuřet" });
    assertAnalyzesTo(cz, "kuřat", new String[] { "kuř" });
    assertAnalyzesTo(cz, "kuřeti", new String[] { "kuřet" });
    assertAnalyzesTo(cz, "kuřatům", new String[] { "kuř" });
    assertAnalyzesTo(cz, "kuřatech", new String[] { "kuř" });
    assertAnalyzesTo(cz, "kuřetem", new String[] { "kuřet" });
    assertAnalyzesTo(cz, "kuřaty", new String[] { "kuř" });
    
    /* ending with í */
    assertAnalyzesTo(cz, "stavení", new String[] { "staven" });
    assertAnalyzesTo(cz, "stavením", new String[] { "stave" });
    assertAnalyzesTo(cz, "staveních", new String[] { "staven" });
    assertAnalyzesTo(cz, "staveními", new String[] { "staven" });
  }
  
  /**
   * Test showing how adjectival forms conflate
   * @throws IOException on analyzer creation error
   */
  public void testAdjectives() throws IOException {
    CzechAnalyzer cz = createCzechAnalyzer();
    
    /* ending with ý/á/é */
    assertAnalyzesTo(cz, "mladý", new String[] { "mlad" });
    assertAnalyzesTo(cz, "mladí", new String[] { "mlad" });
    assertAnalyzesTo(cz, "mladého", new String[] { "mlad" });
    assertAnalyzesTo(cz, "mladých", new String[] { "mlad" });
    assertAnalyzesTo(cz, "mladému", new String[] { "mlad" });
    assertAnalyzesTo(cz, "mladým", new String[] { "mlad" });
    assertAnalyzesTo(cz, "mladé", new String[] { "mlad" });
    assertAnalyzesTo(cz, "mladém", new String[] { "mla" });
    assertAnalyzesTo(cz, "mladými", new String[] { "mlad" }); 
    assertAnalyzesTo(cz, "mladá", new String[] { "mlad" });
    assertAnalyzesTo(cz, "mladou", new String[] { "mlad" });

    /* ending with í */
    assertAnalyzesTo(cz, "jarní", new String[] { "jarn" });
    assertAnalyzesTo(cz, "jarního", new String[] { "jarn" });
    assertAnalyzesTo(cz, "jarních", new String[] { "jarn" });
    assertAnalyzesTo(cz, "jarnímu", new String[] { "jarním" });
    assertAnalyzesTo(cz, "jarním", new String[] { "jar" });
    assertAnalyzesTo(cz, "jarními", new String[] { "jarn" });
  }
  
  /**
   * Test some possessive suffixes
   * @throws IOException on analyzer creation error
   */
  public void testPossessive() throws IOException {
    CzechAnalyzer cz = createCzechAnalyzer();
    assertAnalyzesTo(cz, "Karlův", new String[] { "karl" });
    assertAnalyzesTo(cz, "jazykový", new String[] { "jazyk" });
  }
  
  /**
   * Test some exceptional rules, implemented as rewrites.
   * @throws IOException on analyzer creation error
   */
  public void testExceptions() throws IOException {
    CzechAnalyzer cz = createCzechAnalyzer();
    
    /* rewrite of št -> sk */
    assertAnalyzesTo(cz, "český", new String[] { "česk" });
    assertAnalyzesTo(cz, "čeští", new String[] { "češt" });
    
    /* rewrite of čt -> ck */
    assertAnalyzesTo(cz, "anglický", new String[] { "anglick" });
    assertAnalyzesTo(cz, "angličtí", new String[] { "angličt" });
    
    /* rewrite of z -> h */
    assertAnalyzesTo(cz, "kniha", new String[] { "knih" });
    assertAnalyzesTo(cz, "knize", new String[] { "kniz" });
    
    /* rewrite of ž -> h */
    assertAnalyzesTo(cz, "mazat", new String[] { "maz" });
    assertAnalyzesTo(cz, "mažu", new String[] { "maž" });
    
    /* rewrite of c -> k */
    assertAnalyzesTo(cz, "kluk", new String[] { "kluk" });
    assertAnalyzesTo(cz, "kluci", new String[] { "kluc" });
    assertAnalyzesTo(cz, "klucích", new String[] { "kluc" });
    
    /* rewrite of č -> k */
    assertAnalyzesTo(cz, "hezký", new String[] { "hezk" });
    assertAnalyzesTo(cz, "hezčí", new String[] { "hezč" });
    
    /* rewrite of *ů* -> *o* */
    assertAnalyzesTo(cz, "hůl", new String[] { "hůl" });
    assertAnalyzesTo(cz, "hole", new String[] { "hol" });
    
    /* rewrite of e* -> * */
    assertAnalyzesTo(cz, "deska", new String[] { "desk" });
    assertAnalyzesTo(cz, "desek", new String[] { "desek" });
  }
  
  /**
   * Test that very short words are not stemmed.
   * @throws IOException on analyzer creation error
   */
  public void testDontStem() throws IOException {
    CzechAnalyzer cz = createCzechAnalyzer();
    assertAnalyzesTo(cz, "e", new String[] { "e" });
    assertAnalyzesTo(cz, "zi", new String[] { "zi" });
  }
  
  public void testEmptyTerm() throws IOException {
    Analyzer a = new Analyzer() {
      @Override
      protected TokenStreamComponents createComponents(String fieldName, Reader reader) {
        Tokenizer tokenizer = new KeywordTokenizer(reader);
        return new TokenStreamComponents(tokenizer, new CzechStemFilter(tokenizer));
      }
    };
    checkOneTerm(a, "", "");
  }

  @Override
  CzechAnalyzer.StemmerImpl getStemmerImpl() {
    return CzechAnalyzer.StemmerImpl.LIGHT;
  }
}
