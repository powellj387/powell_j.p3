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

            public static int calculateEditDistance(String word1, String word2) {
                int len1 = word1.length();
                int len2 = word2.length();

                int[][] dp = new int[len1 + 1][len2 + 1];

                for (int i = 0; i <= len1; i++) {
                    dp[i][0] = i;
                }

                for (int j = 0; j <= len2; j++) {
                    dp[0][j] = j;
                }

                for (int i = 1; i <= len1; i++) {
                    for (int j = 1; j <= len2; j++) {
                        int cost = (word1.charAt(i - 1) == word2.charAt(j - 1)) ? 0 : 1;

                        dp[i][j] = Math.min(Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1), dp[i - 1][j - 1] + cost);
                    }
                }

                return dp[len1][len2];
            }

            System.out.println("The edit distance is: ");
        }
    }
}
