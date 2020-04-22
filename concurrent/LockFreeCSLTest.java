package concurrent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Random;

import org.junit.Test;

import concurrent.TestUtils.Operation;

public class LockFreeCSLTest {
	final int threads = 10;

    /*********************************************************************************************/
    /* DELETION TESTS */
    @Test
    public void LFSimpleDeletionTest() {
        SkipList<Integer> sl = new LockFreeCSL<Integer>();
        for (int i = 0; i < 10; i++) {
            assertTrue(sl.insert(i));
        }
        assertTrue(sl.insert(10));
        assertTrue(sl.delete(10));
        assertFalse(sl.delete(10));
        assertTrue(sl.delete(1));
    }

    @Test
    public void LFComplexDeletionTest() {
        SkipList<Integer> sl = new LockFreeCSL<Integer>();
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
    public void LFThreadedDeletionTest() {
        SkipList<Integer> sl = new LockFreeCSL<Integer>();
        TestUtils.modNElems(10000, threads, sl, Operation.INSERT);
        assertEquals((Integer)10001, sl.size());
        final long start = System.currentTimeMillis();
        TestUtils.modNElems(10000, threads, sl, Operation.DELETE);
        final long stop = System.currentTimeMillis();
        assertEquals((Integer)0, sl.size());
        System.out.println("LFThreadedDeletionTest: " + (stop - start));
    }
    @Test
    public void LFComplexThreadedDeletionTest() {
        SkipList<Integer> sl = new LockFreeCSL<Integer>();
        TestUtils.modNElems(0,100, threads, sl, Operation.INSERT);
        TestUtils.modNElems(101,200, threads, sl, Operation.INSERT);
        TestUtils.modNElems(201,300, threads, sl, Operation.INSERT);
        TestUtils.modNElems(500,1000, threads, sl, Operation.INSERT);
        TestUtils.modNElems(1500,1750, threads, sl, Operation.INSERT);

        assertEquals((Integer)1053,sl.size());
        final long start = System.currentTimeMillis();
        TestUtils.modNElems(50,120, threads, sl, Operation.DELETE);
        TestUtils.modNElems(500,565, threads, sl, Operation.DELETE);
        TestUtils.modNElems(1556,1678, threads, sl, Operation.DELETE);
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
        System.out.println("LFComplexThreadedDeletionTest: " + (stop - start));
    }


    /*********************************************************************************************/



    /*********************************************************************************************/
    /* CONTAINS TESTS */

    @Test
    public void LFSimpleContainsTest() {
        SkipList<Integer> sl = new LockFreeCSL<Integer>();
        for (int i = 0; i < 10000; i++) {
            assertTrue(sl.insert(i));
        }
        for(int i = 0;i< 10000;i++){
            assertTrue(sl.contains(i));
        }
    }

    @Test
    public void LFComplexContainsTest() {
        SkipList<Integer> sl = new LockFreeCSL<Integer>();
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
    public void LFThreadedContainsTest() {
        SkipList<Integer> sl = new LockFreeCSL<Integer>();
        TestUtils.modNElems(10000, threads, sl, Operation.INSERT);
        assertEquals((Integer)10001, sl.size());
    	final long start = System.currentTimeMillis();
        for(int i = 0;i<=10000;i++){
            assertTrue(sl.contains(i));
        }
        final long stop = System.currentTimeMillis();
        System.out.println("LFThreadedContainsTest: " + (stop - start));
    }
    
    @Test
    public void FGComplexThreadedContainsTest() {
        SkipList<Integer> sl = new LockFreeCSL<Integer>();
        TestUtils.modNElems(0,100, threads, sl, Operation.INSERT);
        TestUtils.modNElems(101,200, threads, sl, Operation.INSERT);
        TestUtils.modNElems(201,300, threads, sl, Operation.INSERT);
        TestUtils.modNElems(500,1000, threads, sl, Operation.INSERT);
        TestUtils.modNElems(1500,1750, threads, sl, Operation.INSERT);

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
        System.out.println("LFComplexThreadedContainsTest: " + (stop - start));
    }
    
    /*********************************************************************************************/



    /*********************************************************************************************/
    /* INSERTION TESTS */

    @Test
    public void LFSimpleInsertionTest() {
        SkipList<Integer> sl = new LockFreeCSL<Integer>();
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
    public void LFComplexInsertionTest() {
        SkipList<Integer> sl = new LockFreeCSL<Integer>();
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
    public void LFThreadedInsertionTest() {
        SkipList<Integer> sl = new LockFreeCSL<Integer>();
        final long start = System.currentTimeMillis();
        TestUtils.modNElems(10000, threads, sl, Operation.INSERT);
        final long stop = System.currentTimeMillis();
        assertEquals((Integer)10001, sl.size());
        for(int i = 0; i <= 10000; i++){
            assertFalse(sl.insert(i));
        }
        System.out.println("LFThreadedInsertionTest: " + (stop - start));
    }

    @Test
    public void LFComplexThreadedInsertionTest() {
        SkipList<Integer> sl = new LockFreeCSL<Integer>();
        final long  start = System.currentTimeMillis();
        TestUtils.modNElems(0, 200, threads, sl, Operation.INSERT);
        TestUtils.modNElems(1000, 2000, threads, sl, Operation.INSERT);
        final long stop = System.currentTimeMillis();

        assertEquals((Integer)1202,sl.size());

        for(int i = 0; i <= 200; i++){
            assertTrue(sl.contains(i));
        }
        for(int i = 201; i < 1000; i++){
            assertFalse(sl.contains(i));
        }
        System.out.println("LFComplexThreadedInsertionTest: " + (stop - start));
    }



    /*********************************************************************************************/




}
