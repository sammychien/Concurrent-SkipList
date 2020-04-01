package iter;

import java.util.ArrayList;

public class IterSkiplist {
    
    /* 
    Other Details:
    Allow duplicates
    */

    class Node {
        int val;
        int highest_level; 
        ArrayList<Node> next;

        public Node(int a) {

        }
    }

    Node head;
    Node tail;
    int size; // # elems
    int maxHeight;

    /* Everyone */
    public IterSkiplist() {

    }

    /* Huy Le */
    public boolean insert(int a) {
        return false;
    }

    /* Sammy */
    public boolean delete(int a) {
        return false;
    }

    /* David Chao */
    public boolean isPresent(int a) {
        return false;
    }

    /* 
    Returns # of elems 
    */
    public int size() {
        return size;
    }

    public int height() {
        return maxHeight;
    }

    /* Clara Johnson */
    public void printg() {

    }

}
