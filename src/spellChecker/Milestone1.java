package spellChecker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Milestone1 {

    public Milestone1() {
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            System.out.println("Enter two words:");
            String word1 = reader.readLine();
            String word2 = reader.readLine();

            // TODO: Implement logic to calculate edit distance here

            System.out.println("The edit distance is: ");
        }
    }
}
