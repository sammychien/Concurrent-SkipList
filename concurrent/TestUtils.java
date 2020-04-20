package concurrent;

import static org.junit.Assert.assertTrue;

public final class TestUtils {

    enum Operation {INSERT, DELETE, NONE}

    public static void modNElems(int N, int numThreads, SkipList<Integer> sl, final Operation mod) {
        int quotient = N / numThreads;
        Thread[] threads = new Thread[numThreads];
        for (int i = 0; i < numThreads-1; i++) {
            threads[i] = new Thread(new modThread(i * quotient, (i+1) * quotient - 1, sl, mod));
        }
        threads[numThreads-1] = new Thread(new modThread((numThreads-1) * quotient, N, sl, mod));
        for (int i = 0; i < numThreads; i++) {
            threads[i].start();
        }
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void modNElems(int start, int N, int numThreads, SkipList<Integer> sl, final Operation mod) {
        int quotient = (N-start+1) / numThreads;
        Thread[] threads = new Thread[numThreads];
        for (int i = 0; i < numThreads-1; i++) {
            threads[i] = new Thread(new modThread((i * quotient)+start, start+((i+1) * quotient - 1), sl, mod));
        }
        threads[numThreads-1] = new Thread(new modThread(((numThreads-1) * quotient)+start, N, sl, mod));
        for (int i = 0; i < numThreads; i++) {
            threads[i].start();
        }
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static class modThread implements Runnable {
        int begin, end;
        SkipList<Integer> sl;
        final Operation mod;
        public modThread(int begin, int end, SkipList<Integer> sl, final Operation mod) {
            this.begin = begin; this.end = end; this.sl = sl; this.mod = mod;
        }
        @Override
        public void run() {
            if (mod == Operation.INSERT) {
                for (int i = begin; i <= end; ++i) {
                    assertTrue(sl.insert(i));
                }
            } else if (mod == Operation.DELETE) {
                for (int i = begin; i <= end; ++i) {
                    assertTrue(sl.delete(i));
                }
            }
        }
    }

}