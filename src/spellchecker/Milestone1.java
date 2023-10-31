package spellchecker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Milestone1 {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        SpellChecker spellChecker = new SpellChecker(new ArrayList<>()); // Pass an empty lexicon

        while (true) {
            System.out.println("Enter two words:");
            String word1 = reader.readLine().toLowerCase();
            String word2 = reader.readLine().toLowerCase();

            int distance = spellChecker.editDistance(word1, word2);
            System.out.println("The edit distance is: " + distance);
        }
    }
}
