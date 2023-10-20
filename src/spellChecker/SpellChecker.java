package spellChecker;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;


public class SpellChecker {
    private TrieNode root;

    public SpellChecker(List<String> lexicon) {
        root = new TrieNode();
        for (String word : lexicon) {
            insertWord(word);
        }
    }

    public boolean spelledCorrectly(String word) {
        TrieNode node = searchNode(word);
        return (node != null && node.isEndOfWord);
    }

    public List<String> suggestWords(String word, int maxEditDistance) {
        List<String> suggestions = new ArrayList<>();
        searchSuggestions(root, word, "", maxEditDistance, suggestions);
        return suggestions;
    }

    private void insertWord(String word) {
        TrieNode current = root;
        for (char c : word.toCharArray()) {
            int index = c - 'a';
            if (current.children[index] == null) {
                current.children[index] = new TrieNode();
            }
            current = current.children[index];
        }
        current.isEndOfWord = true;
    }
    private TrieNode searchNode(String word) {
        TrieNode current = root;
        for (char c : word.toCharArray()) {
            int index = c - 'a';
            if (current.children[index] == null) {
                return null;
            }
            current = current.children[index];
        }
        return current;
    }

    private void searchSuggestions(TrieNode node, String word, String currentWord, int distance, List<String> suggestions) {
        if (distance < 0) {
            return;
        }

        if (node.isEndOfWord && distance >= 0) {
            suggestions.add(currentWord);
        }

        for (char c = 'a'; c <= 'z'; c++) {
            for (int i = 0; i < 26; i++) {
                if (node.children[i] != null) {
                    String newWord = currentWord + c;
                    searchSuggestions(node.children[i], word, newWord, c == word.charAt(0) ? distance : distance - 1, suggestions);
                }
            }
        }
    }

    private class TrieNode {
        TrieNode[] children;
        boolean isEndOfWord;

        TrieNode() {
            children = new TrieNode[26]; // Assuming only lowercase letters
            isEndOfWord = false;
        }
    }

    public static int editDistance(String s1, String s2) {
        int m = s1.length();
        int n = s2.length();

        int[][] dp = new int[m + 1][n + 1];

        // Initialize the DP table
        for (int i = 0; i <= m; i++) {
            for (int j = 0; j <= n; j++) {
                if (i == 0) {
                    dp[i][j] = j;
                } else if (j == 0) {
                    dp[i][j] = i;
                } else if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = 1 + Math.min(dp[i - 1][j], Math.min(dp[i][j - 1], dp[i - 1][j - 1]));
                }
            }
        }

        return dp[m][n];
    }
    public static List<String> suggestWordsTest(String word, int maxEditDistance, List<String> lexicon) {
        List<String> suggestions = new ArrayList<>();

        for (String candidate : lexicon) {
            int editDistance = editDistance(word, candidate);
            if (editDistance <= maxEditDistance) {
                suggestions.add(candidate);
            }
        }

        // If the word is spelled correctly and in the lexicon, return it as the only suggestion
        if (lexicon.contains(word) && !suggestions.contains(word)) {
            suggestions.clear();
            suggestions.add(word);
        }

        return suggestions;
    }
}
