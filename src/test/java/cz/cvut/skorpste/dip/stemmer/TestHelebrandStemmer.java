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
public class TestHelebrandStemmer extends BaseTestCase {
  
  /**
   * Test showing how masculine noun forms conflate
   */
  public void testMasculineNouns() throws IOException {
    CzechAnalyzer cz = createCzechAnalyzer();
    
    /* animate ending with a hard consonant */
    assertAnalyzesTo(cz, "pán", new String[] { "pan" });
    assertAnalyzesTo(cz, "páni", new String[] { "pat" });
    assertAnalyzesTo(cz, "pánové", new String[] { "pat" });
    assertAnalyzesTo(cz, "pána", new String[] { "pat" });
    assertAnalyzesTo(cz, "pánů", new String[] { "pat" });
    assertAnalyzesTo(cz, "pánovi", new String[] { "pat" });
    assertAnalyzesTo(cz, "pánům", new String[] { "panum" });
    assertAnalyzesTo(cz, "pány", new String[] { "pat" });
    assertAnalyzesTo(cz, "páne", new String[] { "pat" });
    assertAnalyzesTo(cz, "pánech", new String[] { "panech" });
    assertAnalyzesTo(cz, "pánem", new String[] { "panem" });
    
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
    assertAnalyzesTo(cz, "muž", new String[] { "muz" });
    assertAnalyzesTo(cz, "muži", new String[] { "muzat" });
    assertAnalyzesTo(cz, "muže", new String[] { "muzat" });
    assertAnalyzesTo(cz, "mužů", new String[] { "muzat" });
    assertAnalyzesTo(cz, "mužům", new String[] { "muzat" });
    assertAnalyzesTo(cz, "mužích", new String[] { "muzat" });
    assertAnalyzesTo(cz, "mužem", new String[] { "muzat" });
    
    /* inanimate ending with a soft consonant */
    assertAnalyzesTo(cz, "stroj", new String[] { "strot" });
    assertAnalyzesTo(cz, "stroje", new String[] { "strot" });
    assertAnalyzesTo(cz, "strojů", new String[] { "strot" });
    assertAnalyzesTo(cz, "stroji", new String[] { "strot" });
    assertAnalyzesTo(cz, "strojům", new String[] { "strot" });
    assertAnalyzesTo(cz, "strojích", new String[] { "strot" });
    assertAnalyzesTo(cz, "strojem", new String[] { "strot" });
    
    /* ending with a */
    assertAnalyzesTo(cz, "předseda", new String[] { "predsed" });
    assertAnalyzesTo(cz, "předsedové", new String[] { "predsed" });
    assertAnalyzesTo(cz, "předsedy", new String[] { "predsed" });
    assertAnalyzesTo(cz, "předsedů", new String[] { "predsed" });
    assertAnalyzesTo(cz, "předsedovi", new String[] { "predsed" });
    assertAnalyzesTo(cz, "předsedům", new String[] { "predsed" });
    assertAnalyzesTo(cz, "předsedu", new String[] { "predsed" });
    assertAnalyzesTo(cz, "předsedo", new String[] { "predsed" });
    assertAnalyzesTo(cz, "předsedech", new String[] { "predsed" });
    assertAnalyzesTo(cz, "předsedou", new String[] { "predsedt" });
    
    /* ending with e */
    assertAnalyzesTo(cz, "soudce", new String[] { "soud" });
    assertAnalyzesTo(cz, "soudci", new String[] { "soud" });
    assertAnalyzesTo(cz, "soudců", new String[] { "soud" });
    assertAnalyzesTo(cz, "soudcům", new String[] { "soud" });
    assertAnalyzesTo(cz, "soudcích", new String[] { "soud" });
    assertAnalyzesTo(cz, "soudcem", new String[] { "soud" });
  }
  
  /**
   * Test showing how feminine noun forms conflate
   */
  public void testFeminineNouns() throws IOException {
    CzechAnalyzer cz = createCzechAnalyzer();
    
    /* ending with hard consonant */
    assertAnalyzesTo(cz, "kost", new String[] { "kost" });
    assertAnalyzesTo(cz, "kosti", new String[] { "kostit" });
    assertAnalyzesTo(cz, "kostí", new String[] { "kos" });
    assertAnalyzesTo(cz, "kostem", new String[] { "kost" });
    assertAnalyzesTo(cz, "kostech", new String[] { "kost" });
    assertAnalyzesTo(cz, "kostmi", new String[] { "kostm" });
    
    /* ending with a soft consonant */
    // note: in this example sing nom. and sing acc. don't conflate w/ the rest
    assertAnalyzesTo(cz, "píseň", new String[] { "pisit" });
    assertAnalyzesTo(cz, "písně", new String[] { "pisit" });
    assertAnalyzesTo(cz, "písni", new String[] { "pisit" });
    assertAnalyzesTo(cz, "písněmi", new String[] { "pisit" });
    assertAnalyzesTo(cz, "písních", new String[] { "pisit" });
    assertAnalyzesTo(cz, "písním", new String[] { "pisit" });
    
    /* ending with e */
    assertAnalyzesTo(cz, "růže", new String[] { "ruzat" });
    assertAnalyzesTo(cz, "růží", new String[] { "ruzat" });
    assertAnalyzesTo(cz, "růžím", new String[] { "ruzat" });
    assertAnalyzesTo(cz, "růžích", new String[] { "ruzat" });
    assertAnalyzesTo(cz, "růžemi", new String[] { "ruzat" });
    assertAnalyzesTo(cz, "růži", new String[] { "ruzat" });
    
    /* ending with a */
    assertAnalyzesTo(cz, "žena", new String[] { "zit" });
    assertAnalyzesTo(cz, "ženy", new String[] { "zit" });
    assertAnalyzesTo(cz, "žen", new String[] { "zen" });
    assertAnalyzesTo(cz, "ženě", new String[] { "zenet" });
    assertAnalyzesTo(cz, "ženám", new String[] { "zenat" });
    assertAnalyzesTo(cz, "ženu", new String[] { "zenout" });
    assertAnalyzesTo(cz, "ženo", new String[] { "zit" });
    assertAnalyzesTo(cz, "ženách", new String[] { "zenach" });
    assertAnalyzesTo(cz, "ženou", new String[] { "zen" });
    assertAnalyzesTo(cz, "ženami", new String[] { "zenam" });
  }

  /**
   * Test showing how neuter noun forms conflate
   */
  public void testNeuterNouns() throws IOException {
    CzechAnalyzer cz = createCzechAnalyzer();
    
    /* ending with o */
    assertAnalyzesTo(cz, "město", new String[] { "mest" });
    assertAnalyzesTo(cz, "města", new String[] { "mest" });
    assertAnalyzesTo(cz, "měst", new String[] { "mest" });
    assertAnalyzesTo(cz, "městu", new String[] { "mest" });
    assertAnalyzesTo(cz, "městům", new String[] { "mest" });
    assertAnalyzesTo(cz, "městě", new String[] { "mesit" });
    assertAnalyzesTo(cz, "městech", new String[] { "mest" });
    assertAnalyzesTo(cz, "městem", new String[] { "mest" });
    assertAnalyzesTo(cz, "městy", new String[] { "mest" });
    
    /* ending with e */
    assertAnalyzesTo(cz, "moře", new String[] { "mor" });
    assertAnalyzesTo(cz, "moří", new String[] { "mor" });
    assertAnalyzesTo(cz, "mořím", new String[] { "mor" });
    assertAnalyzesTo(cz, "moři", new String[] { "mor" });
    assertAnalyzesTo(cz, "mořích", new String[] { "mor" });
    assertAnalyzesTo(cz, "mořem", new String[] { "mor" });

    /* ending with ě */
    assertAnalyzesTo(cz, "kuře", new String[] { "kur" });
    assertAnalyzesTo(cz, "kuřata", new String[] { "kur" });
    assertAnalyzesTo(cz, "kuřete", new String[] { "kurit" });
    assertAnalyzesTo(cz, "kuřat", new String[] { "kur" });
    assertAnalyzesTo(cz, "kuřeti", new String[] { "kurit" });
    assertAnalyzesTo(cz, "kuřatům", new String[] { "kur" });
    assertAnalyzesTo(cz, "kuřatech", new String[] { "kur" });
    assertAnalyzesTo(cz, "kuřetem", new String[] { "kurit" });
    assertAnalyzesTo(cz, "kuřaty", new String[] { "kur" });
    
    /* ending with í */
    assertAnalyzesTo(cz, "stavení", new String[] { "stav" });
    assertAnalyzesTo(cz, "stavením", new String[] { "stav" });
    assertAnalyzesTo(cz, "staveních", new String[] { "stav" });
    assertAnalyzesTo(cz, "staveními", new String[] { "stav" });
  }
  
  /**
   * Test showing how adjectival forms conflate
   */
  public void testAdjectives() throws IOException {
    CzechAnalyzer cz = createCzechAnalyzer();
    
    /* ending with ý/á/é */
    assertAnalyzesTo(cz, "mladý", new String[] { "mlad" });
    assertAnalyzesTo(cz, "mladí", new String[] { "mlad" });
    assertAnalyzesTo(cz, "mladého", new String[] { "mladeh" });
    assertAnalyzesTo(cz, "mladých", new String[] { "mlad" });
    assertAnalyzesTo(cz, "mladému", new String[] { "mlad" });
    assertAnalyzesTo(cz, "mladým", new String[] { "mlad" });
    assertAnalyzesTo(cz, "mladé", new String[] { "mlad" });
    assertAnalyzesTo(cz, "mladém", new String[] { "mlad" });
    assertAnalyzesTo(cz, "mladými", new String[] { "mlad" }); 
    assertAnalyzesTo(cz, "mladá", new String[] { "mlad" });
    assertAnalyzesTo(cz, "mladou", new String[] { "mladt" });

    /* ending with í */
    assertAnalyzesTo(cz, "jarní", new String[] { "jar" });
    assertAnalyzesTo(cz, "jarního", new String[] { "jarnih" });
    assertAnalyzesTo(cz, "jarních", new String[] { "jar" });
    assertAnalyzesTo(cz, "jarnímu", new String[] { "jar" });
    assertAnalyzesTo(cz, "jarním", new String[] { "jar" });
    assertAnalyzesTo(cz, "jarními", new String[] { "jar" });
  }
  
  /**
   * Test some possessive suffixes
   */
  public void testPossessive() throws IOException {
    CzechAnalyzer cz = createCzechAnalyzer();
    assertAnalyzesTo(cz, "Karlův", new String[] { "karl" });
    assertAnalyzesTo(cz, "jazykový", new String[] { "jazyk" });
  }
  
  /**
   * Test some exceptional rules, implemented as rewrites.
   */
  public void testExceptions() throws IOException {
    CzechAnalyzer cz = createCzechAnalyzer();
    
    /* rewrite of št -> sk */
    assertAnalyzesTo(cz, "český", new String[] { "cesk" });
    assertAnalyzesTo(cz, "čeští", new String[] { "ces" });
    
    /* rewrite of čt -> ck */
    assertAnalyzesTo(cz, "anglický", new String[] { "angl" });
    assertAnalyzesTo(cz, "angličtí", new String[] { "anglic" });
    
    /* rewrite of z -> h */
    assertAnalyzesTo(cz, "kniha", new String[] { "knih" });
    assertAnalyzesTo(cz, "knize", new String[] { "knizit" });
    
    /* rewrite of ž -> h */
    assertAnalyzesTo(cz, "mazat", new String[] { "mazit" });
    assertAnalyzesTo(cz, "mažu", new String[] { "mazat" });
    
    /* rewrite of c -> k */
    assertAnalyzesTo(cz, "kluk", new String[] { "kluk" });
    assertAnalyzesTo(cz, "kluci", new String[] { "kluc" });
    assertAnalyzesTo(cz, "klucích", new String[] { "kluc" });
    
    /* rewrite of č -> k */
    assertAnalyzesTo(cz, "hezký", new String[] { "hezit" });
    assertAnalyzesTo(cz, "hezčí", new String[] { "hez" });
    
    /* rewrite of *ů* -> *o* */
    assertAnalyzesTo(cz, "hůl", new String[] { "hul" });
    assertAnalyzesTo(cz, "hole", new String[] { "hol" });
    
    /* rewrite of e* -> * */
    assertAnalyzesTo(cz, "deska", new String[] { "des" });
    assertAnalyzesTo(cz, "desek", new String[] { "des" });
  }
  
  /**
   * Test that very short words are not stemmed.
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
    return CzechAnalyzer.StemmerImpl.HELEBRAND;
  }
}
