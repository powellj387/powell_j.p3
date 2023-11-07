package Test;

import org.junit.jupiter.api.Test;
import spellchecker.SpellChecker;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SpellCheckerTest {

    @Test
    void testSpelledCorrectly() {
        // Create a lexicon with three words
        List<String> lexicon = Arrays.asList("apple", "banana", "cherry");
        // Create a SpellChecker with the lexicon
        SpellChecker spellChecker = new SpellChecker(lexicon);

        // Test that a word in the lexicon is spelled correctly
        assertTrue(spellChecker.spelledCorrectly("apple"));
        // Test that a word not in the lexicon is spelled incorrectly
        assertFalse(spellChecker.spelledCorrectly("aple"));
        // Test that a word with one character removed is spelled incorrectly
        assertFalse(spellChecker.spelledCorrectly("appl"));
        // Test that a word with one character added is spelled incorrectly
        assertFalse(spellChecker.spelledCorrectly("appple"));
        // Test that a word with one character changed is spelled incorrectly
        assertFalse(spellChecker.spelledCorrectly("aple"));
        // Test that a word with two characters removed is spelled incorrectly
        assertFalse(spellChecker.spelledCorrectly("ap"));
    }

    @Test
    void testSuggestWords() {
        // Create a lexicon with three words
        List<String> lexicon = Arrays.asList("apple", "banana", "cherry");
        // Create a SpellChecker with the lexicon
        SpellChecker spellChecker = new SpellChecker(lexicon);

        // Test that a word with one character changed suggests the correct word with a max edit distance of 1
        List<String> suggestions = spellChecker.suggestWords("aple", 1);
        assertTrue(suggestions.contains("apple"));
        assertFalse(suggestions.contains("banana"));

        // Test that a word with one character changed suggests the correct word with a max edit distance of 2
        suggestions = spellChecker.suggestWords("aple", 2);
        assertTrue(suggestions.contains("apple"));
        assertFalse(suggestions.contains("banana"));

        // Test that a word with two characters removed suggests the correct word with a max edit distance of 2
        suggestions = spellChecker.suggestWords("ap", 2);
        assertFalse(suggestions.contains("apple"));
        assertFalse(suggestions.contains("banana"));

        // Test that a word with two characters removed does not suggest the correct word with a max edit distance of 1
        suggestions = spellChecker.suggestWords("ap", 1);
        assertFalse(suggestions.contains("apple"));
        assertFalse(suggestions.contains("banana"));
    }
}