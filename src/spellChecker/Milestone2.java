package spellChecker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Milestone2 {

    public Milestone2() {
    }

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

        public static String spellCheck(String word, Set<String> dictionary) {
            int minDistance = Integer.MAX_VALUE;
            String closestWord = null;

            for (String dictWord : dictionary) {
                int distance = calculateEditDistance(word, dictWord);

                if (distance < minDistance) {
                    minDistance = distance;
                    closestWord = dictWord;
                }
            }

            return closestWord;
        }

        System.out.println("Spelled Correctly");
    }
}
