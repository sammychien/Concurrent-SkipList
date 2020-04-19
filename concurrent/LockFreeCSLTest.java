package concurrent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Random;

import org.junit.Test;

import concurrent.TestUtils.Operation;

public class LockFreeCSLTest {

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
        TestUtils.modNElems(10000, 10, sl, Operation.INSERT);
        assertEquals((Integer)10001, sl.size());
        TestUtils.modNElems(10000, 10, sl, Operation.DELETE);    
        assertEquals((Integer)0, sl.size());
    }

    /*********************************************************************************************/

}