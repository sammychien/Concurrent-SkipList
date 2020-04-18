package concurrent;

import java.util.concurrent.atomic.AtomicMarkableReference;

public class LockFreeCSLNode<T> {
    final T value;
    final int key;
    final AtomicMarkableReference<LockFreeCSLNode<T>>[] next;
    private int topLevel;
    // constructor for sentinel nodes
    public LockFreeCSLNode(int key) {
        this.value = null;
        this.key = key;
        this.next = (AtomicMarkableReference<LockFreeCSLNode<T>>[])
            new AtomicMarkableReference[LockFreeCSL.MAX_LEVEL + 1];
        for (int i = 0; i < next.length; i++) {
            next[i] = new AtomicMarkableReference<LockFreeCSLNode<T>>(null,false);
        }
        topLevel = LockFreeCSL.MAX_LEVEL;
    }
    // constructor for ordinary nodes
    public LockFreeCSLNode(T x, int height) {
        this.value = x;
        this.key = x.hashCode();
        next = (AtomicMarkableReference<LockFreeCSLNode<T>>[])
            new AtomicMarkableReference[height + 1];
        for (int i = 0; i < next.length; i++) {
            next[i] = new AtomicMarkableReference<LockFreeCSLNode<T>>(null,false);
        }
        topLevel = height;
    }
}
