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
        head.right = tail;
        tail.left = head;
    }

    /* Huy Le */
    public boolean insert(Integer a) {
        if (isPresent(a)) return true;
        size++;

        int level = 0;

        while (Math.random() > 0.5) level++;
        while (level > maxHeight) {
            head.right.insert(null);
            maxHeight++;
        }
        SkipListNode p = new SkipListNode(a);
        SkipListNode current;
        while (level >= 0) {
            current = head;
            while (current.val != Integer.MAX_VALUE) {
                if (current.right.val < a) current = current.right;
                else {
                    p.right = current.right;
                    p.left = current;
                    current.right.left = p;
                    current.right = p;
                    if (level != maxHeight) p.up = p;
                    if (level != 0) p.down = p;
                    break;
                }
            }
            level--;
        }
        return true;
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
    //Note: looks pretty for up to 3 digit integers; can be changed to work for larger integers
    public void printg() {
    	if(head == null) {
    		System.out.println("[]");
    		return;
    	}
    	SkipListNode currentNode = head;
    	SkipListNode lastRowHead = head;
    	
    	//Find last row so that the printing can be pretty
    	while(lastRowHead.down != null) {
    		lastRowHead = lastRowHead.down;
    	}
    	SkipListNode lastRowMoving = lastRowHead;
    	
    	while(currentNode != null) {
    		currentNode = currentNode.right; //don't want to print the head of a row
    		//print a row
    		System.out.print("[ ");
        	while(currentNode.val != Integer.MAX_VALUE) {
        		//figure out how many padding spaces to print and print them
        		int count = 0;
        		while(lastRowMoving.val != currentNode.val) {
        			count++;
        			lastRowMoving = lastRowMoving.right;
        		}
        		for(int i = 0; i < count - 1; i++) {
        			System.out.print("   ");//4 spaces - 3 spaces for the missing number, 1 for the separation
        		}
        		if(currentNode.val < 10) {
        			System.out.print("  "); //To pad because only 1 digit
        		} else if(currentNode.val < 100) {
        			System.out.print(" "); //To pad because only 2 digits
        		}
        		
        		//print the value
        		System.out.print(currentNode.val.toString() + " ");
        		currentNode = currentNode.right;
        	}
        	System.out.print("]");
        	System.out.print("\n");
        	
        	//Traverse back to the beginning of the row
        	while(currentNode.left != null) {
        		currentNode = currentNode.left;
        	}
        	
        	currentNode = currentNode.down; //currentNode is now the head of the next row down
        	lastRowMoving = lastRowHead;
    	}
    	
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
