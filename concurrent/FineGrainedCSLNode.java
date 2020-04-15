package concurrent;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FineGrainedCSLNode<T> {
    final Lock lock = new ReentrantLock();
    final T item;
    final int key;
    final FineGrainedCSLNode<T>[] next;
    volatile boolean marked = false;
    volatile boolean fullyLinked = false;
    public int topLevel; // height of Node (determined using Random obj)
    /* Sentinel Node Constructor */
    public FineGrainedCSLNode(int key) {
        this.item = null;
        this.key = key;
        next = new FineGrainedCSLNode[FineGrainedCSL.MAX_LEVEL+1];
        topLevel = FineGrainedCSL.MAX_LEVEL;
    }
    /* Regular Node Constructor */
    public FineGrainedCSLNode(T item, int height) {
        this.item = item;
        key = item.hashCode();
        next = new FineGrainedCSLNode[height+1];
        topLevel = height;
    }
    public void lock() {
        lock.lock();
    }
    public void unlock() {
        lock.unlock();
    }
}