package spellchecker;

import java.util.Collections;
import java.util.ArrayList;
import java.util.List;

public class SpellChecker {
    private TrieNode root;
    private List<String> lexicon;

    public SpellChecker(List<String> lexicon) {
        root = new TrieNode();
        this.lexicon = lexicon;
        for (String word : lexicon) {
            insertWord(word);
        }
    }

    public boolean spelledCorrectly(String word) {
        return lexicon.contains(word.toLowerCase());
    }

    public List<String> suggestWords(String word, int maxEditDistance) {
        List<String> suggestions = new ArrayList<>();
        //populate the suggestions list
        searchSuggestions(root, word, "", maxEditDistance, suggestions);
        //if the suggestion list is empty, tell the user there are no suggestions
        if (suggestions.isEmpty()) {
            suggestions.add("No suggestions");
            //if the suggestion list only contains 1 item, and that word is equal to the word provided,
            //tell the user they spelled the word correctly
        }else if(spelledCorrectly(word)) {
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

    private void searchSuggestions(TrieNode node, String word, String currentWord, int maxDistance, List<String> suggestions) {
        int distance = editDistance(word, currentWord);

        if (distance < 0) {
            return;
        }

        if (distance <= maxDistance && lexicon.contains(currentWord)) {
            suggestions.add(currentWord);
        }

        for (int i = 0; i < 26; i++) {
            if (node.children[i] != null) {
                char c = (char) (i + 'a');
                String newWord = currentWord + c;
                searchSuggestions(node.children[i], word, newWord, maxDistance, suggestions);
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

