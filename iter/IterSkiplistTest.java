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
        assertFalse(myList.contains(5));
    }

    @Test
    public void testInsertion(){
        IterSkiplist myList = new IterSkiplist();
        assertTrue(myList.insert(5)); //[5]
        assertEquals(myList.size.intValue(),1);
        assertTrue(myList.contains(5));
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
            assertTrue(myList.contains(p[i]));

        }
    }

    @Test
    public void testDeletion2() {
        IterSkiplist myList = new IterSkiplist();
        for (int i = -10; i < 11; i++) {
            assertTrue(myList.insert(i));
        }
        for (int i = 10; i >= -10; i--) {
            assertTrue(myList.delete(i));
            assertTrue(myList.insert(i));
        }
        for (int i = -10; i < 11; i++) {
            assertTrue(myList.delete(i));
        }
        assertTrue(myList.size() == 0);
        assertTrue(myList.height() == 1);
    }

    @Test
    public void testDeletion(){
        IterSkiplist myList = new IterSkiplist();
        assertTrue(myList.insert(5));
        assertTrue(myList.insert(104));
        myList.printg();

        assertTrue(myList.contains(5));
        assertTrue(myList.contains(104));
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
            assertTrue(myList.contains(i));
        }
        myList.delete(500);
        assertFalse(myList.contains(500));
        assertTrue(myList.contains(499));
        assertTrue(myList.contains(501));
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
            assertTrue(myList.contains(p[i]));
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
        System.out.println("20 is in the list " + myList.contains(20));
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
        System.out.println("4 is in the list " + myList.contains(4));
        System.out.println("List: ");
        myList.printg();
        myList.delete(4);
        System.out.println("List Height: " + myList.height() + "; List size: " + myList.size());
        System.out.println("List: ");
        myList.printg();
        System.out.println("4 is in the list " + myList.contains(4));
        System.out.println("Size is " + myList.size());
        System.out.println("Height is " + myList.height());
    }


        /*********************************************************************************************/
        /* DELETION TESTS */
        @Test
        public void IterSimpleDeletionTest() {
        	IterSkiplist sl = new IterSkiplist();
            for (int i = 0; i < 10; i++) {
                assertTrue(sl.insert(i));
            }
            assertTrue(sl.insert(10));
            assertTrue(sl.delete(10));
            assertFalse(sl.delete(10));
            assertTrue(sl.delete(1));
        }

        @Test
        public void IterComplexDeletionTest() {
        	IterSkiplist sl = new IterSkiplist();
            HashSet<Integer> my = new HashSet<>();
            int[]p = new int[1000];
            Random rand = new Random();
            for(int i = 0;i<1000;i++){
                int n = rand.nextInt(5000);
                while(my.contains(n)){
                    n = rand.nextInt(5000);
                }
                assertTrue(sl.insert(n));
                my.add(n);
                p[i] = n;
            }
            for(int i = 0;i<1000;i++){
                assertTrue(sl.contains(p[i]));
            }
            assertEquals((Integer)1000, sl.size());
            for(int i = 0;i<500;i++){
                int r = rand.nextInt(5000);
                while(!my.contains(r)){
                    r = rand.nextInt(5000);
                }
                my.remove(r);
                assertTrue(sl.delete(r));
            }
            assertEquals((Integer)500, sl.size());
        }

        @Test
        public void IterThreadedDeletionTest() {
        	IterSkiplist sl = new IterSkiplist();
            //TestUtils.modNElems(10000, threads, sl, Operation.INSERT);
            for(int i = 0; i <= 10000; i++) {
            	sl.insert(i);
            }
        	assertEquals((Integer)10001, sl.size());
        	final long start = System.currentTimeMillis();
            //TestUtils.modNElems(10000, threads, sl, Operation.DELETE);
        	for(int i = 0; i <= 10000; i++) {
        		sl.delete(i);
        	}
            final long stop = System.currentTimeMillis();
            assertEquals((Integer)0, sl.size());
            System.out.println("IterThreadedDeletionTest: " + (stop - start));
        }
        @Test
        public void IterComplexThreadedDeletionTest() {
        	IterSkiplist sl = new IterSkiplist();
            /*TestUtils.modNElems(0,100, threads, sl, Operation.INSERT);
            TestUtils.modNElems(101,200, threads, sl, Operation.INSERT);
            TestUtils.modNElems(201,300, threads, sl, Operation.INSERT);
            TestUtils.modNElems(500,1000, threads, sl, Operation.INSERT);
            TestUtils.modNElems(1500,1750, threads, sl, Operation.INSERT);*/
        	for(int i = 0; i <= 100; i++) {
        		sl.insert(i);
        	}
        	for(int i = 101; i <= 200; i++) {
        		sl.insert(i);
        	}
        	for(int i = 201; i <= 300; i++) {
        		sl.insert(i);
        	}
        	for(int i = 500; i <= 1000; i++) {
        		sl.insert(i);
        	}
        	for(int i = 1500; i <= 1750; i++) {
        		sl.insert(i);
        	}

            assertEquals((Integer)1053,sl.size());
        	final long start = System.currentTimeMillis();
            /*TestUtils.modNElems(50,120, threads, sl, Operation.DELETE);
            TestUtils.modNElems(500,565, threads, sl, Operation.DELETE);
            TestUtils.modNElems(1556,1678, threads, sl, Operation.DELETE);*/
        	for(int i = 50; i <= 120; i++) {
        		sl.delete(i);
        	}
        	for(int i = 500; i <= 565; i++) {
        		sl.delete(i);
        	}
        	for(int i = 1556; i <= 1678; i++) {
        		sl.delete(i);
        	}
            final long stop = System.currentTimeMillis();
            assertEquals((Integer)793,sl.size());

            for(int i = 0;i<50;i++){
                assertTrue(sl.contains(i));
            }
            for(int i = 121;i<=300;i++){
                assertTrue(sl.contains(i));
            }
            for(int i = 566;i<=1000;i++){
                assertTrue(sl.contains(i));
            }
            for(int i = 1500;i<1556;i++){
                assertTrue(sl.contains(i));
            }
            for(int i = 1679;i<=1750;i++){
                assertTrue(sl.contains(i));
            }
            System.out.println("IterComplexThreadedDeletionTest: " + (stop - start));
        }


        /*********************************************************************************************/




        /*********************************************************************************************/
        /* CONTAINS TESTS */

        @Test
        public void IterSimpleContainsTest() {
        	IterSkiplist sl = new IterSkiplist();
            for (int i = 0; i < 10000; i++) {
                assertTrue(sl.insert(i));
            }
            for(int i = 0;i< 10000;i++){
                assertTrue(sl.contains(i));
            }
        }

        @Test
        public void IterComplexContainsTest() {
        	IterSkiplist sl = new IterSkiplist();
            HashSet<Integer> my = new HashSet<>();
            int[]p = new int[10000];
            Random rand = new Random();
            for(int i = 0;i<10000;i++){
                int n = rand.nextInt(50000);
                while(my.contains(n)){
                    n = rand.nextInt(50000);
                }
                assertTrue(sl.insert(n));
                my.add(n);
                p[i] = n;
            }
            for(int i = 0;i<10000;i++){
                assertTrue(sl.contains(p[i]));
            }
            assertEquals((Integer)10000, sl.size());
        }

        @Test
        public void IterThreadedContainsTest() {
        	IterSkiplist sl = new IterSkiplist();
            //TestUtils.modNElems(10000, threads, sl, Operation.INSERT);
        	for(int i = 0; i <= 10000; i++) {
        		sl.insert(i);
        	}
            assertEquals((Integer)10001, sl.size());
        	final long start = System.currentTimeMillis();
            for(int i = 0;i<=10000;i++){
                assertTrue(sl.contains(i));
            }
            final long stop = System.currentTimeMillis();
            System.out.println("IterThreadedContainsTest: " + (stop - start));
        }
        @Test
        public void IterComplexThreadedContainsTest() {
        	IterSkiplist sl = new IterSkiplist();
            /*TestUtils.modNElems(0,100, threads, sl, Operation.INSERT);
            TestUtils.modNElems(101,200, threads, sl, Operation.INSERT);
            TestUtils.modNElems(201,300, threads, sl, Operation.INSERT);
            TestUtils.modNElems(500,1000, threads, sl, Operation.INSERT);
            TestUtils.modNElems(1500,1750, threads, sl, Operation.INSERT);*/
        	for(int i = 0; i <= 100; i++) {
        		sl.insert(i);
        	}
        	for(int i = 101; i <= 200; i++) {
        		sl.insert(i);
        	}
        	for(int i = 201; i <= 300; i++) {
        		sl.insert(i);
        	}
        	for(int i = 500; i <= 1000; i++) {
        		sl.insert(i);
        	}
        	for(int i = 1500; i <= 1750; i++) {
        		sl.insert(i);
        	}

            assertEquals((Integer)1053,sl.size());

        	final long start = System.currentTimeMillis();
            for(int i = 0;i<=300;i++){
                assertTrue(sl.contains(i));
            }
            for(int i = 500;i<=1000;i++){
                assertTrue(sl.contains(i));
            }
            for(int i = 1500;i<=1750;i++){
                assertTrue(sl.contains(i));
            }
            final long stop = System.currentTimeMillis();
            System.out.println("IterComplexThreadedContainsTest: " + (stop - start));
        }



        /*********************************************************************************************/



        /*********************************************************************************************/
        /* INSERTION TESTS */

        @Test
        public void IterSimpleInsertionTest() {
        	IterSkiplist sl = new IterSkiplist();
            for (int i = 0; i < 10000; i++) {
                assertTrue(sl.insert(i));
            }
            for (int i = 0; i < 10000; i++) {
                assertTrue(sl.contains(i));
            }
            for (int i = 0; i < 10000; i++){
                assertFalse(sl.insert(i));
            }
            assertEquals((Integer)10000, sl.size());
        }

        @Test
        public void IterComplexInsertionTest() {
        	IterSkiplist sl = new IterSkiplist();
            HashSet<Integer> my = new HashSet<>();
            int[]p = new int[10000];
            Random rand = new Random();
            for (int i = 0; i < 10000; i++){
                int n = rand.nextInt(50000);
                while (my.contains(n)){
                    n = rand.nextInt(50000);
                }
                assertTrue(sl.insert(n));
                my.add(n);
                p[i] = n;
            }
            for(int i = 0; i < 10000; i++){
                assertFalse(sl.insert(p[i]));
            }
            assertEquals((Integer)10000, sl.size());
        }

        @Test
        public void IterThreadedInsertionTest() {
        	IterSkiplist sl = new IterSkiplist();
            final long start = System.currentTimeMillis();
            //TestUtils.modNElems(10000, threads, sl, Operation.INSERT);
            for(int i = 0; i <= 10000; i++) {
            	sl.insert(i);
            }
            final long stop = System.currentTimeMillis();
            assertEquals((Integer)10001, sl.size());
            for(int i = 0; i <= 10000; i++){
                assertFalse(sl.insert(i));
            }
            System.out.println("IterThreadedInsertionTest: " + (stop - start));
        }

        @Test
        public void IterComplexThreadedInsertionTest() {
        	IterSkiplist sl = new IterSkiplist();
            final long start = System.currentTimeMillis();
            /*TestUtils.modNElems(0, 200, threads, sl, Operation.INSERT);
            TestUtils.modNElems(1000, 2000, threads, sl, Operation.INSERT);*/
            for(int i = 0; i <= 200; i++) {
            	sl.insert(i);
            }
            for(int i = 1000; i <= 2000; i++) {
            	sl.insert(i);
            }
            final long stop = System.currentTimeMillis();
            
            assertEquals((Integer)1053,sl.size());

            for(int i = 0; i <= 200; i++){
                assertTrue(sl.contains(i));
            }
            for(int i = 201; i < 1000; i++){
                assertFalse(sl.contains(i));
            }
            System.out.println("IterComplexThreadedInsertionTest: " + (stop - start));
        }



        /*********************************************************************************************/

}
