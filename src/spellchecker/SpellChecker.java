//@authors Julian Powell and Alex Csorba
package spellchecker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SpellChecker {
    private ULTrie trie;
    private List<String> lexicon;

    public SpellChecker(List<String> lexicon) {
        trie = new ULTrie();
        this.lexicon = lexicon;
        //adds every word from the lexicon into the trie
        for (String word : lexicon) {
            trie.insertWord(word);
        }
    }

    public boolean spelledCorrectly(String word) {
        return trie.searchWord(word.toLowerCase());
    }

    public List<String> suggestWords(String word, int maxEditDistance) {
        List<String> suggestions = new ArrayList<>();
        // Populate the suggestions list
        searchSuggestions(trie.getRoot(), word, "", maxEditDistance, suggestions);
        // If the suggestion list is empty, tell the user there are no suggestions
        if (suggestions.isEmpty()) {
            suggestions.add("No suggestions");
            //if the word is spelled correctly, tell the user that
        } else if (spelledCorrectly(word)) {
            suggestions.clear();
            suggestions.add("Spelled correctly");
        }
        return suggestions;
    }

    private void searchSuggestions(ULTrie.TrieNode node, String word, String currentWord, int maxDistance, List<String> suggestions) {
        int distance = editDistance(word, currentWord);
        //if the edit distance between the given word and the current
        //word is less than the max desired edit distance and is within the trie
        //add it to the suggestions list
        if (distance <= maxDistance && trie.searchWord(currentWord)) {
            suggestions.add(currentWord);
        }
        //recurse for every word in the trie which would be within the edit distance
        for (char c = 'a'; c <= 'z'; c++) {
            ULTrie.TrieNode childNode = node.getChildren().get(c);
            if (childNode != null) {
                String newWord = currentWord + c;
                searchSuggestions(childNode, word, newWord, maxDistance, suggestions);
            }
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
        if (suggestions.isEmpty()) {
            suggestions.add("No suggestions");
        }
        Collections.sort(suggestions);
        return suggestions;
    }
}