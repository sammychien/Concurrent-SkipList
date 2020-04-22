package concurrent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Random;
import java.util.concurrent.ConcurrentSkipListSet;

import org.junit.Test;

import concurrent.TestUtils.Operation;

public class ConcurrentSkipListSetTest {
	final int threads = 10;


    /*********************************************************************************************/
    /* DELETION TESTS */
    @Test
    public void CSLSSimpleDeletionTest() {
        ConcurrentSkipListSet<Integer> sl = new ConcurrentSkipListSet<Integer>();
        for (int i = 0; i < 10; i++) {
            assertTrue(sl.add(i));
        }
        assertTrue(sl.add(10));
        assertTrue(sl.remove(10));
        assertFalse(sl.remove(10));
        assertTrue(sl.remove(1));
    }

    @Test
    public void CSLSComplexDeletionTest() {
        ConcurrentSkipListSet<Integer> sl = new ConcurrentSkipListSet<Integer>();
        HashSet<Integer> my = new HashSet<>();
        int[]p = new int[1000];
        Random rand = new Random();
        for(int i = 0;i<1000;i++){
            int n = rand.nextInt(5000);
            while(my.contains(n)){
                n = rand.nextInt(5000);
            }
            assertTrue(sl.add(n));
            my.add(n);
            p[i] = n;
        }
        for(int i = 0;i<1000;i++){
            assertTrue(sl.contains(p[i]));
        }
        assertEquals(1000, sl.size());
        for(int i = 0;i<500;i++){
            int r = rand.nextInt(5000);
            while(!my.contains(r)){
                r = rand.nextInt(5000);
            }
            my.remove(r);
            assertTrue(sl.remove(r));
        }
        assertEquals(500, sl.size());
    }

    @Test
    public void CSLSThreadedDeletionTest() {
        ConcurrentSkipListSet<Integer> sl = new ConcurrentSkipListSet<Integer>();
        TestUtils.modNElemsCSLS(10000, threads, sl, Operation.INSERT);
        assertEquals(10001, sl.size());
    	final long start = System.currentTimeMillis();
        TestUtils.modNElemsCSLS(10000, threads, sl, Operation.DELETE);
        final long stop = System.currentTimeMillis();
        assertEquals(0, sl.size());
        System.out.println("CSLSThreadedDeletionTest: " + (stop - start));
    }
    @Test
    public void CSLSComplexThreadedDeletionTest() {
        ConcurrentSkipListSet<Integer> sl = new ConcurrentSkipListSet<Integer>();
        TestUtils.modNElemsCSLS(0,100, threads, sl, Operation.INSERT);
        TestUtils.modNElemsCSLS(101,200, threads, sl, Operation.INSERT);
        TestUtils.modNElemsCSLS(201,300, threads, sl, Operation.INSERT);
        TestUtils.modNElemsCSLS(500,1000, threads, sl, Operation.INSERT);
        TestUtils.modNElemsCSLS(1500,1750, threads, sl, Operation.INSERT);

        assertEquals(1053,sl.size());
    	final long start = System.currentTimeMillis();
        TestUtils.modNElemsCSLS(50,120, threads, sl, Operation.DELETE);
        TestUtils.modNElemsCSLS(500,565, threads, sl, Operation.DELETE);
        TestUtils.modNElemsCSLS(1556,1678, threads, sl, Operation.DELETE);
        final long stop = System.currentTimeMillis();
        assertEquals(793,sl.size());

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
        System.out.println("CSLSComplexThreadedDeletionTest: " + (stop - start));
    }


    /*********************************************************************************************/




    /*********************************************************************************************/
    /* CONTAINS TESTS */

    @Test
    public void CSLSSimpleContainsTest() {
        ConcurrentSkipListSet<Integer> sl = new ConcurrentSkipListSet<Integer>();
        for (int i = 0; i < 10000; i++) {
            assertTrue(sl.add(i));
        }
        for(int i = 0;i< 10000;i++){
            assertTrue(sl.contains(i));
        }
    }

    @Test
    public void CSLSComplexContainsTest() {
        ConcurrentSkipListSet<Integer> sl = new ConcurrentSkipListSet<Integer>();
        HashSet<Integer> my = new HashSet<>();
        int[]p = new int[10000];
        Random rand = new Random();
        for(int i = 0;i<10000;i++){
            int n = rand.nextInt(50000);
            while(my.contains(n)){
                n = rand.nextInt(50000);
            }
            assertTrue(sl.add(n));
            my.add(n);
            p[i] = n;
        }
        for(int i = 0;i<10000;i++){
            assertTrue(sl.contains(p[i]));
        }
        assertEquals(10000, sl.size());
    }

    @Test
    public void CSLSThreadedContainsTest() {
        ConcurrentSkipListSet<Integer> sl = new ConcurrentSkipListSet<Integer>();
        TestUtils.modNElemsCSLS(10000, threads, sl, Operation.INSERT);
        assertEquals(10001, sl.size());
    	final long start = System.currentTimeMillis();
        for(int i = 0;i<=10000;i++){
            assertTrue(sl.contains(i));
        }
        final long stop = System.currentTimeMillis();
        System.out.println("CSLSThreadedContainsTest: " + (stop - start));
    }
    @Test
    public void CSLSComplexThreadedContainsTest() {
        ConcurrentSkipListSet<Integer> sl = new ConcurrentSkipListSet<Integer>();
        TestUtils.modNElemsCSLS(0,100, threads, sl, Operation.INSERT);
        TestUtils.modNElemsCSLS(101,200, threads, sl, Operation.INSERT);
        TestUtils.modNElemsCSLS(201,300, threads, sl, Operation.INSERT);
        TestUtils.modNElemsCSLS(500,1000, threads, sl, Operation.INSERT);
        TestUtils.modNElemsCSLS(1500,1750, threads, sl, Operation.INSERT);

        assertEquals(1053,sl.size());

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
        System.out.println("CSLSComplexThreadedContainsTest: " + (stop - start));
    }



    /*********************************************************************************************/



    /*********************************************************************************************/
    /* INSERTION TESTS */

    @Test
    public void CSLSSimpleInsertionTest() {
        ConcurrentSkipListSet<Integer> sl = new ConcurrentSkipListSet<Integer>();
        for (int i = 0; i < 10000; i++) {
            assertTrue(sl.add(i));
        }
        for (int i = 0; i < 10000; i++) {
            assertTrue(sl.contains(i));
        }
        for (int i = 0; i < 10000; i++){
            assertFalse(sl.add(i));
        }
        assertEquals(10000, sl.size());
    }

    @Test
    public void CSLSComplexInsertionTest() {
    	ConcurrentSkipListSet<Integer> sl = new ConcurrentSkipListSet<Integer>();
        HashSet<Integer> my = new HashSet<>();
        int[]p = new int[10000];
        Random rand = new Random();
        for (int i = 0; i < 10000; i++){
            int n = rand.nextInt(50000);
            while (my.contains(n)){
                n = rand.nextInt(50000);
            }
            assertTrue(sl.add(n));
            my.add(n);
            p[i] = n;
        }
        for(int i = 0; i < 10000; i++){
            assertFalse(sl.add(p[i]));
        }
        assertEquals(10000, sl.size());
    }

    @Test
    public void CSLSThreadedInsertionTest() {
        ConcurrentSkipListSet<Integer> sl = new ConcurrentSkipListSet<Integer>();
    	final long start = System.currentTimeMillis();
        TestUtils.modNElemsCSLS(10000, threads, sl, Operation.INSERT);
        final long stop = System.currentTimeMillis();
        assertEquals(10001, sl.size());
        for(int i = 0; i <= 10000; i++){
            assertFalse(sl.add(i));
        }
        System.out.println("CSLSThreadedInsertionTest: " + (stop - start));
    }

    @Test
    public void CSLSComplexThreadedInsertionTest() {
        ConcurrentSkipListSet<Integer> sl = new ConcurrentSkipListSet<Integer>();
    	final long start = System.currentTimeMillis();
        TestUtils.modNElemsCSLS(0, 200, threads, sl, Operation.INSERT);
        TestUtils.modNElemsCSLS(1000, 2000, threads, sl, Operation.INSERT);
        final long stop = System.currentTimeMillis();
        
        assertEquals(1053,sl.size());

        for(int i = 0; i <= 200; i++){
            assertTrue(sl.contains(i));
        }
        for(int i = 201; i < 1000; i++){
            assertFalse(sl.contains(i));
        }
        System.out.println("CSLSComplexThreadedInsertionTest: " + (stop - start));

    }



    /*********************************************************************************************/

}
