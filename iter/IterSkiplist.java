package iter;

import java.util.ArrayList;

public class IterSkiplist {
    
    /* 
    Other Details:
    Allow duplicates
    */

    class SkipListNode {
        Integer val;
        
        SkipListNode left;
        SkipListNode right;
        SkipListNode up;
        SkipListNode down;

        public SkipListNode(Integer a) {
            val = a;
            left = null;
            right = null;
            up = null;
            down = null;
        }
    }

    SkipListNode head;
    SkipListNode tail;
    Integer size; // # elems
    Integer maxHeight;

    /* Everyone */
    public IterSkiplist() {
        head = new SkipListNode(Integer.MIN_VALUE);
        tail = new SkipListNode(Integer.MAX_VALUE);
        size = 0;
        maxHeight = 0;
    }

    /* Huy Le */
    public boolean insert(Integer a) {
        return false;
    }

    /* Sammy */
    /**
     * Deletes an entire tower with the input value and returns true. 
     * If the input value is not in any Node, then the function returns false.
     * @param a Target value of the Node to delete from SkipList
     * @return boolean
     */
    public boolean delete(Integer a) {
        if (!isPresent(a)) return false;
        SkipListNode toDelete = findPosition(a);
        while (toDelete != null) {
            toDelete.left.right = toDelete.right;
            toDelete.right.left = toDelete.left;
            toDelete = toDelete.up;
        }
        return false;
    }

    /* David Chao */
    public boolean isPresent(Integer a) {
        SkipListNode p = head;
        while(true){
            while((p.right != tail)&&p.right.val<=a){
                p = p.right;
            }
            if(p.val == a){
                return true;
            }else if(p.down!=null){
                p=p.down;
            }else{
                return false;
            }
        }
        return false;
    }

    /* 
    Returns # of elems 
    */
    public Integer size() {
        return size;
    }

    public Integer height() {
        return maxHeight;
    }

    /* Clara Johnson */
    public void printg() {

    }

    /**
     * This method returns the SkipListNode with a value that matches 
     * the target. If there are duplicate values in the SkipList, 
     * this will return the lowest SkipListNode in the tallest tower. 
     * If the value does not exist in the SkipList, then the method 
     * will return the node with the largest value less than the param target value.
     * @param target Integer value of Node to find
     * @return SkipListNode
     */
    private SkipListNode findPosition(Integer target) {
        SkipListNode currentNode = head;
        while(true) {
            // Search right until larger entry is found
            while(currentNode.right.val < target) {
                currentNode = currentNode.right;
            }
            // Try to go down a layer
            if (currentNode.down != null) {
                currentNode = currentNode.down;
            } else {
                break;
            }
        }
        return currentNode;
    }

}
