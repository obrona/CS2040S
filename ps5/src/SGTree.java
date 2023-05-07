import com.sun.source.tree.Tree;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * ScapeGoat Tree class
 *
 * This class contains some of the basic code for implementing a ScapeGoat tree.
 * This version does not include any of the functionality for choosing which node
 * to scapegoat.  It includes only code for inserting a node, and the code for rebuilding
 * a subtree.
 */

public class SGTree {

    // Designates which child in a binary tree
    enum Child {LEFT, RIGHT}

    /**
     * TreeNode class.
     *
     * This class holds the data for a node in a binary tree.
     *
     * Note: we have made things public here to facilitate problem set grading/testing.
     * In general, making everything public like this is a bad idea!
     *
     */
    public static class TreeNode {

        public int key;
        public int weight = 0;
        public TreeNode left = null;
        public TreeNode right = null;

        TreeNode(int k) {
            key = k;
            weight=1;
        }

        @Override
        public String toString() {
            return "" + this.key;
        }

    }

    // Root of the binary tree
    public TreeNode root = null;



    public int countNODES2(TreeNode node) {
        if(node == null) {
            return 0;
        }
        else {
            return 1 + countNODES2(node.left) + countNODES2(node.right);
        }


    }
    /**
     * Counts the number of nodes in the specified subtree
     *
     * @param node  the parent node, not to be counted
     * @param child the specified subtree
     * @return number of nodes
     */



    public int countNodes(TreeNode node, Child child) {
        // TODO: Implement this
      if(child == Child.LEFT) {
          return countNODES2(node.left);
      }

      else {
          return countNODES2(node.right);
      }



    }




    //Pointer for the array output
    public int pointer = 0;

    TreeNode[] output ;
    public void helperForEnumerate(TreeNode node) {



        if(node == null) {
          //do nothing
        }
        else {
            helperForEnumerate(node.left);
            output[pointer] = node;
            pointer++;
            helperForEnumerate(node.right);

        }


    }

    /**
     * Builds an array of nodes in the specified subtree
     *
     * @param node  the parent node, not to be included in returned array
     * @param child the specified subtree
     * @return array of nodes
     */





    public TreeNode[] enumerateNodes(TreeNode node, Child child) {
        // TODO: Implement this
        pointer = 0;

        if(child == Child.LEFT) {
            output = new TreeNode[countNODES2(node.left)];
            helperForEnumerate(node.left);
            return output;
        }
        else {
            output = new TreeNode[countNODES2(node.right)];
            helperForEnumerate(node.right);
            return output;
        }
    }



    public TreeNode helperForBuildTree(TreeNode[] x, int start, int end) {
        if(start > end||end < start) {
            return null;
        }
        else if (start == end) {
            if (start >= x.length) {
                return null;
            } else {
                TreeNode s = x[start];
                s.left = null;
                s.right = null;
                s.weight = 1;
                return s;
            }
        }
        else {
            int median = (start+end)/2;
            TreeNode r = x[median];
            r.left = helperForBuildTree(x,start,median-1);
            r.right = helperForBuildTree(x,median+1,end);
            int right = (r.right == null) ? 0 : r.right.weight;
            int left = (r.left == null) ? 0 : r.left.weight;

            r.weight = right + left + 1;
            return r;



        }


    }

    /**
     * Builds a tree from the list of nodes
     * Returns the node that is the new root of the subtree
     *
     * @param nodeList ordered array of nodes
     * @return the new root node
     */


    public TreeNode buildTree(TreeNode[] nodeList) {
        // TODO: Implement this
        return helperForBuildTree(nodeList,0,nodeList.length-1);
    }

    /**
    * Rebuilds the specified subtree of a node
    * 
    * @param node the part of the subtree to rebuild
    * @param child specifies which child is the root of the subtree to rebuild
    */
    public void rebuild(TreeNode node, Child child) {
        // Error checking: cannot rebuild null tree
        if (node == null) return;
        // First, retrieve a list of all the nodes of the subtree rooted at child
        TreeNode[] nodeList = enumerateNodes(node, child);
        // Then, build a new subtree from that list
        TreeNode newChild = buildTree(nodeList);
        // Finally, replace the specified child with the new subtree
        if (child == Child.LEFT) {
            node.left = newChild;
        } else if (child == Child.RIGHT) {
            node.right = newChild;
        }
    }

    public boolean checkBalance(TreeNode u) {
        if(u == null) {
            return true;
        } else {
            int w = u.weight;
            int right = (u.right == null) ? 0 : u.right.weight;
            int left = (u.left == null) ? 0 : u.left.weight;
            return (left > (2 * w / 3) || right > (2 * w /3)) ? false : true;
        }

    }







    /**
    * Inserts a key into the tree
    *
    * @param key the key to insert
    */
    public void insert(int key) {
        if (root == null) {
            root = new TreeNode(key);
            return;
        }

        TreeNode node = root;
        Queue<TreeNode> visitedNodes = new LinkedList<TreeNode>();
        while (true) {
            if (key <= node.key) {
                node.weight++;
                visitedNodes.add(node);
                if (node.left == null) break;
                node = node.left;

            } else {
                node.weight++;
                visitedNodes.add(node);
                if (node.right == null) break;
                node = node.right;
            }
        }

        if (key <= node.key) {
            node.left = new TreeNode(key);

        } else {
            node.right = new TreeNode(key);
        }
        while(visitedNodes.size()>1) {
            TreeNode parent = visitedNodes.poll();
            TreeNode child = visitedNodes.peek();
            if(parent.left == child) {
                if (!checkBalance(child)) {
                    rebuild(parent, Child.LEFT);
                    break;
                }
            } else {
                    if (!checkBalance(child)) {
                        rebuild(parent, Child.RIGHT);
                        break;
                    }
                }
            }
        }



    // Simple main function for debugging purposes
    public static void main(String[] args) {
        SGTree tree = new SGTree();
        for (int i = 0; i < 100; i++) {
            tree.insert(i);
        }
        //tree.rebuild(tree.root, Child.RIGHT);
        //tree.rebuild(tree.root, Child.LEFT);
        //TreeNode[] x = tree.enumerateNodes(tree.root,Child.RIGHT);
        //System.out.println(Arrays.toString(x));
        //System.out.println(tree.checkBalance(tree.root));
        System.out.println(tree.root.right.left.weight);
        System.out.println(tree.root.right.right.weight);
    }
}
