package spellChecker;

import java.util.Collections;

public class SpellChecker {

    public SpellChecker(java.util.List<java.lang.String> lexicon){}

    public boolean spelledCorrectly(java.lang.String word){return false;}

    public java.util.List<java.lang.String> suggestWords(java.lang.String word, int maxEditDistance){return Collections.singletonList("bleh");}

    public static int editDistance(java.lang.String s1, java.lang.String s2){}

    public static java.util.List<java.lang.String> suggestWordsTest(java.lang.String word, int maxEditDistance, java.util.List<java.lang.String> lexicon){}
}
