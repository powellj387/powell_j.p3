//@authors Julian Powell and Alex Csorba
package Test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spellchecker.ULTrie;
import static org.junit.jupiter.api.Assertions.*;

public class ULTrieTest {
    private ULTrie trie;

    @BeforeEach
    public void setUp() {
        trie = new ULTrie();
    }

    @Test
    public void testInsertAndSearchWord() {
        assertFalse(trie.searchWord("apple"));
        assertFalse(trie.searchWord("banana"));
        assertFalse(trie.searchWord("cherry"));

        trie.insertWord("apple");
        assertTrue(trie.searchWord("apple"));
        assertFalse(trie.searchWord("app"));
        assertFalse(trie.searchWord("appl"));

        trie.insertWord("banana");
        assertTrue(trie.searchWord("banana"));
        assertFalse(trie.searchWord("ban"));
        assertFalse(trie.searchWord("banan"));

        trie.insertWord("cherry");
        assertTrue(trie.searchWord("cherry"));
        assertFalse(trie.searchWord("cher"));
        assertFalse(trie.searchWord("cherries"));
    }

    @Test
    public void testSearchWordWithEmptyTrie() {
        assertFalse(trie.searchWord("apple"));
        assertFalse(trie.searchWord("banana"));
        assertFalse(trie.searchWord("cherry"));
    }
    @Test
    public void testRootNode() {
        ULTrie.TrieNode root = trie.getRoot();
        assertNotNull(root);
        assertNull(root.getValue());
        assertFalse(root.isEndOfWord());
        assertTrue(root.getChildren().isEmpty());
    }
    @Test
    public void testChildNodes() {
        trie.insertWord("apple");
        trie.insertWord("app");
        trie.insertWord("banana");

        ULTrie.TrieNode root = trie.getRoot();
        assertTrue(root.getChildren().containsKey('a'));

        ULTrie.TrieNode aNode = root.getChildren().get('a');
        assertNotNull(aNode);
        assertNull(root.getValue()); // The value of the root should be null
        assertFalse(aNode.isEndOfWord());

        assertTrue(aNode.getChildren().containsKey('p'));
        assertTrue(root.getChildren().containsKey('b'));

        ULTrie.TrieNode pNode = aNode.getChildren().get('p');
        assertNotNull(pNode);
        assertEquals('p', pNode.getValue()); // The value of 'a' node should be 'p'
        assertFalse(pNode.isEndOfWord());

        ULTrie.TrieNode bNode = root.getChildren().get('b');
        assertNotNull(bNode);
        assertEquals('b', bNode.getValue()); // The value of 'a' node should be 'b'
        assertFalse(bNode.isEndOfWord());
    }
}
