//@authors Julian Powell and Alex Csorba
package Test;

import org.junit.jupiter.api.Test;
import spellchecker.SpellChecker;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

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

    @Test
    void testCorrectResultList() throws FileNotFoundException {
        //constructs an arraylist with the provided results from running the word "tully"
        ArrayList<String> correctOutput = new ArrayList<>();
        File file = new File("resources/correctOutput.txt");
        Scanner fileScan = new Scanner(file);

        while (fileScan.hasNextLine()) {
            correctOutput.add(fileScan.nextLine().toLowerCase());
        }

        ArrayList<String> lexicon = new ArrayList<>();
        File lexFile = new File("resources/lexicon.txt");
        Scanner lexFileScan = new Scanner(lexFile);

        //adds every word from the file into the lexicon arrayList
        while (lexFileScan.hasNextLine()) {
            lexicon.add(lexFileScan.nextLine().toLowerCase());
        }
        //creates the spellChecker
        SpellChecker spellChecker = new  SpellChecker(lexicon);

        ArrayList<String> suggestions = new ArrayList<>(spellChecker.suggestWords("tully", 2));

        //checks to make sure the output from the code is correct
        for(int i=0;i< suggestions.size();i++){
            assert(Objects.equals(correctOutput.get(i), suggestions.get(i)));
        }
    }
}