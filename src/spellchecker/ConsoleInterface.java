//@authors Julian Powell and Alex Csorba
package spellchecker;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

public abstract class ConsoleInterface {

    public static void main(String[] args) throws IOException {
        // sets a default edit distance of 2 if
        // nothing is passed in through the command line
        int editDistance = 2;
        //grabs the edit distance from the command line
        if (args.length > 0) {
            try {
                editDistance = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.out.println("Invalid command line argument. Using default edit distance of 2.");
            }
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter a word:");
        //reads the word which the user writes
        String word = reader.readLine();

        // Assuming lexicon is a List of words in the English language
        ArrayList<String> lexicon = new ArrayList<>();
        File file = new File("resources/lexicon.txt");
        Scanner fileScan = new Scanner(file);

        //adds every word from the file into the lexicon arrayList
        while (fileScan.hasNextLine()) {
            lexicon.add(fileScan.nextLine().toLowerCase());
        }
        //creates the spellChecker
        SpellChecker spellChecker = new  SpellChecker(lexicon);

        //constructs the list of suggestions based on the
        //spellchecker, provided word, and edit distance
        ArrayList<String> suggestions = new ArrayList<>(spellChecker.suggestWords(word, editDistance));

        //prints out every suggested word
        System.out.println("Suggested words: ");
        for (String item : suggestions) {
            System.out.println(item);
        }
    }
}
