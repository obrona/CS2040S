import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class Trie {

    // Wildcards
    final char WILDCARD = '.';

    private class TrieNode {
        // TODO: Create your TrieNode class here.

         int rootFlag = 0;
         int endFlag = 0;
         char key;
         //int[] presentChars = new int[256];
         TrieNode[] nodes = new TrieNode[62];

         TrieNode(char key) {
             this.key = key;
         }

         TrieNode() {
             rootFlag = 1;
         }

         public String toString() {
             return "" + this.key;
         }

    }

    public TrieNode root = null;



    public Trie() {
         // TODO: Initialise a trie class here.
         root = null;
    }

    public int charToNum(char x) {
        int v = (int) x;
        if(v >= 48 && v <=57) {
            return v - 48;
        } else if(v >= 65 && v <= 90) {
            return v - 55;
        } else {
            return v - 61;
        }
    }



    /**
     * Inserts string s into the Trie.
     *
     * @param s string to insert into the Trie
     */
    void insert(String s) {
        // TODO
        TrieNode x = root;
        if(x == null) {
            root = new TrieNode();
            x = root;
        }

        for(int i = 0; i<s.length(); i++) {
            char letter = s.charAt(i);
            int ascii = charToNum(letter);
            if( i == s.length() - 1) {
                if(!(x.nodes[ascii] == null)) {
                    x = x.nodes[ascii];
                    x.endFlag = 1;
                } else {
                    //x.presentChars[ascii] = 1;
                    x.nodes[ascii] = new TrieNode(letter);
                    x = x.nodes[ascii];
                    x.endFlag = 1;
                }
            } else {
                if (!(x.nodes[ascii] == null)) {
                    x = x.nodes[ascii];
                } else {
                    x.nodes[ascii] = new TrieNode(letter);
                    x = x.nodes[ascii];
                }
            }
        }
    }

    /**
     * Checks whether string s exists inside the Trie or not.
     *
     * @param s string to check for
     * @return whether string s is inside the Trie
     */
    boolean contains(String s) {
        TrieNode x = root;
        if(root == null) {
            return false;
        }

        for(int i = 0; i < s.length(); i++) {
            char letter = s.charAt(i);
            int ascii = charToNum(letter);
            if (i == s.length() - 1) {
                return (!(x.nodes[ascii] == null) && x.nodes[ascii].endFlag == 1);
            } else {
                if(x.nodes[ascii] == null) {
                    return false;
                } else {
                    x = x.nodes[ascii];
                }
            }
        }



        // TODO
        return true;
    }

    // Global variables for my helper functions for prefix search

    ArrayList<StringBuilder> temp = new ArrayList<>(); //stores the words
    LinkedList<Character> letters = new LinkedList<>(); //stores the char from dfs
    int max;
    public void helper(String s, int currIndex, int lastIndex, TrieNode x) {
        if (x == null) {

        }
        else if (max == 0) {

        } else if (x.rootFlag == 1) {
            if (currIndex >= lastIndex || s.charAt(currIndex) == WILDCARD) {
                for(int i = 0; i < 62 ; i++) {
                    helper(s, currIndex+1, lastIndex, x.nodes[i]);
                }
            }
            else {
                int next = charToNum(s.charAt(currIndex));
                helper(s, currIndex+1, lastIndex, x.nodes[next]);
            }
        }

        else if (currIndex >= lastIndex) {
            if (x.endFlag == 1) {
                letters.add(x.key);

                //System.out.println(letters.toString());

                StringBuilder str = new StringBuilder();
                for(int i = 0; i < letters.size(); i++) {
                    str.append(letters.get(i));
                }
                temp.add(str);

                max--;
                for(int i = 0; i < 62; i++) {
                    helper(s, currIndex+1, lastIndex, x.nodes[i]);
                }
                letters.removeLast();

                //System.out.println(letters.toString());
            } else {
                letters.add(x.key);

                //System.out.println(letters.toString());

                for(int i = 0; i < 62 ; i++) {
                    helper(s, currIndex+1, lastIndex, x.nodes[i]);
                }
                letters.removeLast();

                //System.out.println(letters.toString());
            }
        }
        else if (s.charAt(currIndex) == WILDCARD) {
            letters.add(x.key);

            //System.out.println(letters.toString());

            for(int i = 0; i < 62 ; i++) {
                helper(s, currIndex+1, lastIndex, x.nodes[i]);
            }
            letters.removeLast();

            //System.out.println(letters.toString());
        } else {
            int next = charToNum(s.charAt(currIndex));
            letters.add(x.key);

            //System.out.println(letters.toString());

            helper(s,currIndex+1, lastIndex, x.nodes[next]);
            letters.removeLast();

            //System.out.println(letters.toString());
        }
    }




    /**
     * Searches for strings with prefix matching the specified pattern sorted by lexicographical order. This inserts the
     * results into the specified ArrayList. Only returns at most the first limit results.
     *
     * @param s       pattern to match prefixes with
     * @param results array to add the results into
     * @param limit   max number of strings to add into results
     */
    public void prefixSearch(String s, ArrayList<String> results, int limit) {
        max = limit;
        temp.clear();
        letters.clear();
        helper(s, 0, s.length(), root);
        for(int i = 0; i < temp.size(); i++) {
            results.add(i, temp.get(i).toString());
        }
    }


    // Simplifies function call by initializing an empty array to store the results.
    // PLEASE DO NOT CHANGE the implementation for this function as it will be used
    // to run the test cases.
    String[] prefixSearch(String s, int limit) {
        ArrayList<String> results = new ArrayList<String>();
        prefixSearch(s, results, limit);
        return  results.toArray(new String[0]);
    }


    public static void main(String[] args) {
        Trie t = new Trie();
        t.insert("peter");
        t.insert("pip");
        t.insert("picked");
        t.insert("a");
        t.insert("peck");
        t.insert("of");
        t.insert("pickled");
        t.insert("peppers");
        t.insert("pepppito");
        t.insert("pepi");
        t.insert("pik");
        t.insert("panther");
        t.insert("archery");
        //System.out.println(t.contains("peck"));
        String[] result1 = t.prefixSearch(".", 1);
        System.out.println(Arrays.toString(result1));
        result1 = t.prefixSearch(".i" ,2);
        System.out.println(Arrays.toString(result1));

        //String[] result2 = t.prefixSearch("pe", 10);
        //System.out.println(Arrays.toString(result2));
        // result1 should be:
        // ["peck", "pepi", "peppers", "pepppito", "peter"]
        // result2 should contain the same elements with result1 but may be ordered arbitrarily
        //ArrayList<String> a = new ArrayList<String>(10);
        //System.out.println(a.size());
        //StringBuilder s = new StringBuilder("");
        //s.deleteCharAt(0);
        //TrieNode a = t.root.nodes[t.charToNum('a')];
        //System.out.println(a.endFlag);
        //System.out.println(s.charAt(0));

    }
}
