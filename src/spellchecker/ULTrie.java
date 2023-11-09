//@authors Julian Powell and Alex Csorba
package spellchecker;

import java.util.HashMap;

public class ULTrie {
    private TrieNode root;

    public ULTrie() {
        root = new TrieNode(null); // Set the root node value to null
    }

    public TrieNode getRoot() {
        return root;
    }

    public void insertWord(String word) {
        TrieNode current = root;
        for (char c : word.toCharArray()) {
            if (!current.children.containsKey(c)) {
                TrieNode newNode = new TrieNode(c);
                current.children.put(c, newNode);
            }
            current = current.children.get(c);
        }
        current.isEndOfWord = true;
    }

    public boolean searchWord(String word) {
        TrieNode current = root;
        boolean found = true; // Assume the word is found initially
        boolean continueSearch = true; // Use this variable to control the loop
        for (char c : word.toCharArray()) {
            if (!current.children.containsKey(c)) {
                found = false; // If the character is not found, set found to false
                continueSearch = false; // Set the control variable to false
            }
            if (continueSearch) {
                current = current.children.get(c);
            }
        }
        return found && current.isEndOfWord; // Check both conditions
    }


    public static class TrieNode {
        private Character value;
        private HashMap<Character, TrieNode> children = new HashMap<>();
        private boolean isEndOfWord;

        public TrieNode(Character value) {
            this.value = value;
        }

        public Character getValue() {
            return value;
        }

        public HashMap<Character, TrieNode> getChildren() {
            return children;
        }

        public boolean isEndOfWord() {
            return isEndOfWord;
        }
    }
}
