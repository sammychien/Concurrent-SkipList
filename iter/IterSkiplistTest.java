package iter;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Random;


public class IterSkiplistTest {

    @Test
    public void simpleTest() {
        IterSkiplist myList = new IterSkiplist();
       // System.out.println("List: ");
        assertEquals(myList.size.intValue(),0);
        assertEquals(myList.maxHeight.intValue(),0);
        assertEquals(myList.head.val.intValue(),Integer.MIN_VALUE);
        assertEquals(myList.tail.val.intValue(),Integer.MAX_VALUE);
        assertEquals(myList.head.right,myList.tail);
        assertEquals(myList.tail.left,myList.head);
        assertNull(myList.head.left);
        assertNull(myList.head.up);
        assertNull(myList.head.down);
        assertNull(myList.tail.up);
        assertNull(myList.tail.down);
        assertNull(myList.tail.right);
        myList.printg(); //should print an empty list
        assertFalse(myList.delete(5));
        assertFalse(myList.isPresent(5));
    }

    @Test
    public void testInsertion(){
        IterSkiplist myList = new IterSkiplist();
        assertTrue(myList.insert(5)); //[5]
        assertEquals(myList.size.intValue(),1);
        assertTrue(myList.isPresent(5));
        assertTrue(myList.head.right.val==5);
        assertTrue(myList.tail.left.val==5);
        myList.printg();
    }
    @Test
    public void complexInsertion(){
        IterSkiplist myList = new IterSkiplist();
        HashSet<Integer> my = new HashSet<>();
        int[]p = new int[1000];
        Random rand = new Random();
        for(int i = 0;i<1000;i++){
            int n = rand.nextInt(5000);
            while(my.contains(n)){
                n = rand.nextInt(5000);
            }
            assertTrue(myList.insert(n));
            my.add(n);
            p[i] = n;
        }
        myList.printg();
        for(int i = 0;i<1000;i++){
            assertTrue(myList.isPresent(p[i]));

        }
    }

    @Test
    public void testDeletion(){
        IterSkiplist myList = new IterSkiplist();
        assertTrue(myList.insert(5));
        assertTrue(myList.insert(104));
        myList.printg();

        assertTrue(myList.isPresent(5));
        assertTrue(myList.isPresent(104));
        assertTrue(myList.size==2);
        assertTrue(myList.delete(5));
        assertTrue(myList.size==1);
        assertFalse(myList.delete(5));
        assertFalse(myList.delete(13));
        assertTrue(myList.delete(104));
        assertTrue(myList.size==0);
        myList.printg();
    }

    @Test
    public void specialTest(){
        IterSkiplist myList = new IterSkiplist();
        for(int i = 0;i<1000;i++){
            assertTrue(myList.insert(i));
        }
        for(int i = 0;i<1000;i++){
            assertTrue(myList.isPresent(i));
        }
        myList.delete(500);
        assertFalse(myList.isPresent(500));
        assertTrue(myList.isPresent(499));
        assertTrue(myList.isPresent(501));
    }

    @Test
    public void complexDeletion(){
        IterSkiplist myList = new IterSkiplist();
        HashSet<Integer> my = new HashSet<>();
        int[]p = new int[1000];
        Random rand = new Random();
        for(int i = 0;i<1000;i++){
            int n = rand.nextInt(5000);
            while(my.contains(n)){
                n = rand.nextInt(5000);
            }
            assertTrue(myList.insert(n));
            my.add(n);
            p[i] = n;
        }
        myList.printg();
        for(int i = 0;i<1000;i++){
            assertTrue(myList.isPresent(p[i]));
        }
        assertTrue(myList.size==1000);
        for(int i = 0;i<500;i++){
            int r = rand.nextInt(5000);
            while(!my.contains(r)){
                r = rand.nextInt(5000);
            }
            my.remove(r);
            assertTrue(myList.delete(r));
        }
        assertTrue(myList.size==500);
        myList.printg();


    }
    @Test
    public void printingTest() {
        IterSkiplist myList= new IterSkiplist();
        System.out.println("List: ");
        myList.printg(); //should print an empty list
        System.out.println("List Height: " + myList.height() + "; List size: " + myList.size());
        System.out.println("20 is in the list " + myList.isPresent(20));
        myList.insert(5); //[5]
        System.out.println("List Height: " + myList.height() + "; List size: " + myList.size());
        System.out.println("List: ");
        myList.printg();
        myList.insert(9); //[5, 9]
        System.out.println("List Height: " + myList.height() + "; List size: " + myList.size());
        System.out.println("List: ");
        myList.printg();
        myList.insert(2);
        System.out.println("List Height: " + myList.height() + "; List size: " + myList.size());
        myList.insert(7); //[2, 5, 7, 9]
        System.out.println("List Height: " + myList.height() + "; List size: " + myList.size());
        System.out.println("List: ");
        myList.printg();
        myList.insert(1);
        System.out.println("List Height: " + myList.height() + "; List size: " + myList.size());
        myList.insert(3);
        System.out.println("List Height: " + myList.height() + "; List size: " + myList.size());
        myList.insert(4);
        System.out.println("List Height: " + myList.height() + "; List size: " + myList.size());
        myList.insert(6);
        System.out.println("List Height: " + myList.height() + "; List size: " + myList.size());
        myList.insert(8);
        System.out.println("List Height: " + myList.height() + "; List size: " + myList.size());
        myList.insert(10); //[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
        System.out.println("List Height: " + myList.height() + "; List size: " + myList.size());
        System.out.println("List: ");
        myList.printg();
        System.out.println("4 is in the list " + myList.isPresent(4));
        System.out.println("List: ");
        myList.printg();
        myList.delete(4);
        System.out.println("List Height: " + myList.height() + "; List size: " + myList.size());
        System.out.println("List: ");
        myList.printg();
        System.out.println("4 is in the list " + myList.isPresent(4));
        System.out.println("Size is " + myList.size());
        System.out.println("Height is " + myList.height());
    }
}
