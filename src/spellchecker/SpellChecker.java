package spellchecker;

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
        if (suggestions.isEmpty()) {
            suggestions.add("No suggestions");
        } else if (suggestions.size() == 1 && suggestions.get(0).equalsIgnoreCase(word)) {
            suggestions.clear();
            suggestions.add("Spelled correctly");
        }
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

        for (int i = 0; i < 26; i++) {
            if (node.children[i] != null) {
                char c = (char) (i+'a');
                String newWord = currentWord + c;
                int newDistance = c == word.charAt(0) ? distance : distance - 1;
                searchSuggestions(node.children[i], word, newWord, newDistance, suggestions);
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
        String trueWord = word.toLowerCase();
        for (String candidate : lexicon) {
            int editDistance = editDistance(trueWord, candidate);

            if (editDistance <= maxEditDistance) {

                // If the word is spelled correctly and in the lexicon, return it as the only suggestion
                if (lexicon.contains(trueWord) && editDistance == 0) {
                    suggestions.clear();
                    suggestions.add("Spelled correctly");
                    break;
                } else {
                    suggestions.add(candidate);
                }
            }
        }
        if(suggestions.size() == 0){
            suggestions.add("No suggestions");
        }
        Collections.sort(suggestions);
        return suggestions;
    }
}

