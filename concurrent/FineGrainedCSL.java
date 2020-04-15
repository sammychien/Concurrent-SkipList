package concurrent;


public class FineGrainedCSL<T> {
    final static int MAX_LEVEL = 32;
    final FineGrainedCSLNode<T> head = new FineGrainedCSLNode<T>(Integer.MIN_VALUE);
    final FineGrainedCSLNode<T> tail = new FineGrainedCSLNode<T>(Integer.MAX_VALUE);
    public LazySkipList() {
        for (int i = 0; i < head.next.length; i++) {
            head.next[i] = tail;
        }
    }

    
}
