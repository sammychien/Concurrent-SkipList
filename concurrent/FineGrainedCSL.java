package concurrent;

import java.util.Random;

public class FineGrainedCSL<T> implements SkipList<T> {
    final static int MAX_LEVEL = 32;
    private Integer size;
    final FineGrainedCSLNode<T> head = new FineGrainedCSLNode<T>(Integer.MIN_VALUE);
    final FineGrainedCSLNode<T> tail = new FineGrainedCSLNode<T>(Integer.MAX_VALUE);
    public FineGrainedCSL() {
        for (int i = 0; i < head.next.length; i++) {
            head.next[i] = tail;
        }
        size = 0;
    }
    public int find(T x, FineGrainedCSLNode<T>[] preds, FineGrainedCSLNode<T>[] succs) {
        int key = x.hashCode();
        int lFound = -1;
        FineGrainedCSLNode<T> pred = head;
        for (int level = MAX_LEVEL; level >= 0; level--) {
            FineGrainedCSLNode<T> curr = pred.next[level];
            while (key > curr.key) {
                pred = curr; curr = pred.next[level];
            }
            if (lFound == -1 && key == curr.key) {
                lFound = level;
            }
            preds[level] = pred;
            succs[level] = curr;
        }
        return lFound;
    }

    public boolean contains(T a){
        FineGrainedCSLNode<T>[] preds = (FineGrainedCSLNode<T>[]) new FineGrainedCSLNode[MAX_LEVEL + 1];
        FineGrainedCSLNode<T>[] succs = (FineGrainedCSLNode<T>[]) new FineGrainedCSLNode[MAX_LEVEL + 1];
        int levelFound = find(a, preds, succs);
        boolean result = levelFound != -1 && succs[levelFound].fullyLinked && !succs[levelFound].marked;
        return result;
    }
    public boolean insert(T a){
        int topLevel = randomLevel();
        FineGrainedCSLNode<T>[] preds = (FineGrainedCSLNode<T>[]) new FineGrainedCSLNode[MAX_LEVEL + 1];
        FineGrainedCSLNode<T>[] succs = (FineGrainedCSLNode<T>[]) new FineGrainedCSLNode[MAX_LEVEL + 1];
        while (true) {
            int levelFound = find(a, preds, succs);
            if (levelFound != -1) {
                FineGrainedCSLNode<T> nodeFound = succs[levelFound];
                if (!nodeFound.marked) {
                    while (!nodeFound.fullyLinked) {}
                    return false;
                }
                continue;
            }
            int highestLocked = -1;
            try {
                // Locking procedure
                FineGrainedCSLNode<T> pred, succ;
                boolean valid = true;
                for (int level = 0; valid && (level <= topLevel); level++) {
                    pred = preds[level];
                    succ = succs[level];
                    pred.lock.lock();
                    highestLocked = level;
                    valid = !pred.marked && !succ.marked && pred.next[level]==succ;
                }
                if (!valid) continue;
                FineGrainedCSLNode<T> newNode = (FineGrainedCSLNode<T>) new FineGrainedCSLNode<T>(a, topLevel);
                for (int level = 0; level <= topLevel; level++)
                    newNode.next[level] = succs[level];
                for (int level = 0; level <= topLevel; level++)
                    preds[level].next[level] = newNode;
                newNode.fullyLinked = true;
                size++;
                return true;
            } finally {
                for (int level = 0; level <= highestLocked; level++) {
                    preds[level].unlock();
                }
            }
        }
        
    }
    
    private int randomLevel() {
        int level = 0;
        Random rand = new Random();
        while (level < 31) {
            if (rand.nextInt(2) == 1) {
                level++;
            } else {
                break;
            }
        }
        return level;
    }

    public boolean delete(T a) {

        FineGrainedCSLNode<T> victim = null;
        boolean isMarked = false;
        int topLevel = -1;
        FineGrainedCSLNode<T>[] preds = (FineGrainedCSLNode<T>[]) new FineGrainedCSLNode[MAX_LEVEL + 1];
        FineGrainedCSLNode<T>[] succs = (FineGrainedCSLNode<T>[]) new FineGrainedCSLNode[MAX_LEVEL + 1];
        while (true) {
            int lFound = find(a, preds, succs);
            if (lFound != -1)
                victim = succs[lFound];
            if (isMarked | (lFound != -1 && (victim.fullyLinked && victim.topLevel == lFound && !victim.marked))) {
                if (!isMarked) {
                    topLevel = victim.topLevel;
                    victim.lock.lock();
                    if (victim.marked) {
                        victim.lock.unlock();
                        return false;
                    }
                    victim.marked = true;
                    isMarked = true;
                }
                int highestLocked = -1;
                try {
                    FineGrainedCSLNode<T> pred;
                    boolean valid = true;
                    for (int level = 0; valid && (level <= topLevel); level++) {
                        pred = preds[level];
                        pred.lock.lock();
                        highestLocked = level;
                        valid = !pred.marked && pred.next[level] == victim;
                    }
                    if (!valid)
                        continue;
                    for (int level = topLevel; level >= 0; level--) {
                        preds[level].next[level] = victim.next[level];
                    }
                    victim.lock.unlock();
                    return true;
                } finally {
                    for (int i = 0; i <= highestLocked; i++) {
                        preds[i].unlock();
                    }
                }
            } else
                return false;
        }

    }
    public Integer size() {
        return size;
    }
}
