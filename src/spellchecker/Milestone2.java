package spellchecker;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Milestone2 {

    public static void main(String[] args) throws IOException {
        int editDistance = 2;
        if (args.length > 0) {
            try {
                editDistance = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.out.println("Invalid command line argument. Using default edit distance of 2.");
            }
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter a word:");
        String word = reader.readLine();

        // Assuming lexicon is a List of words in the English language
        ArrayList<String> lexicon = new ArrayList<>();
        File file = new File("resources/lexicon.txt");
        Scanner fileScan = new Scanner(file);

        while (fileScan.hasNextLine()) {
            lexicon.add(fileScan.nextLine().toLowerCase());
        }

        SpellChecker spellChecker = new  SpellChecker(lexicon);

        ArrayList<String> suggestions = new ArrayList<>(spellChecker.suggestWords(word, editDistance));

        System.out.println("Suggested words: ");
        for (String item : suggestions) {
            System.out.println(item);
        }
    }
}


