package spellchecker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public abstract class ConsoleInterface {

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

        // TODO: Implement spell checking logic here

        System.out.println("Spelled Correctly");
    }
}
