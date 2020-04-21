package concurrent;

import java.util.concurrent.atomic.AtomicMarkableReference;

public class LockFreeCSL<T> implements SkipList<T> {
    static final int MAX_LEVEL = 32;
    final LockFreeCSLNode<T> head = new LockFreeCSLNode<T>(Integer.MIN_VALUE);
    final LockFreeCSLNode<T> tail = new LockFreeCSLNode<T>(Integer.MAX_VALUE);

    public LockFreeCSL() {
        for (int i = 0; i < head.next.length; i++) {
            head.next[i] = new AtomicMarkableReference<LockFreeCSLNode<T>>(tail, false);
        }
    }

    
    public boolean insert(T x) {
        int topLevel = randomLevel();
        int bottomLevel = 0;
        LockFreeCSLNode<T>[] preds = (LockFreeCSLNode<T>[]) new LockFreeCSLNode[MAX_LEVEL + 1];
        LockFreeCSLNode<T>[] succs = (LockFreeCSLNode<T>[]) new LockFreeCSLNode[MAX_LEVEL + 1];
        while (true) {
            boolean found = find(x, preds, succs);
            if (found) {
                return false;
            } else {
                LockFreeCSLNode<T> newNode = new LockFreeCSLNode<T>(x, topLevel);
                for (int level = bottomLevel; level <= topLevel; level++) {
                    LockFreeCSLNode<T> succ = succs[level];
                    newNode.next[level].set(succ, false);
                }
                LockFreeCSLNode<T> pred = preds[bottomLevel];
                LockFreeCSLNode<T> succ = succs[bottomLevel];
                newNode.next[bottomLevel].set(succ, false);
                if (!pred.next[bottomLevel].compareAndSet(succ, newNode,
                    false, false)) {
                    continue;
                }
                for (int level = bottomLevel + 1; level <= topLevel; level++) {
                    while (true) {
                        pred = preds[level];
                        succ = succs[level];
                        if (pred.next[level].compareAndSet(succ, newNode, false, false))
                            break;
                        find(x, preds, succs);
                    }
                }
                return true;
            }
        }
    }

    public boolean delete(T x) {
        int bottomLevel = 0;
        LockFreeCSLNode<T>[] preds = (LockFreeCSLNode<T>[]) new LockFreeCSLNode[MAX_LEVEL + 1];
        LockFreeCSLNode<T>[] succs = (LockFreeCSLNode<T>[]) new LockFreeCSLNode[MAX_LEVEL + 1];
        LockFreeCSLNode<T> succ;
        while (true) {
            boolean found = find(x, preds, succs);
            if (!found) {
                return false;
            } else {
                LockFreeCSLNode<T> nodeToRemove = succs[bottomLevel];
                for (int level = nodeToRemove.topLevel;
                     level >= bottomLevel + 1; level--) {
                    boolean[] marked = {false};
                    succ = nodeToRemove.next[level].get(marked);
                    while (!marked[0]) {
                        nodeToRemove.next[level].attemptMark(succ, true);
                        succ = nodeToRemove.next[level].get(marked);
                    }
                }
                boolean[] marked = {false};
                succ = nodeToRemove.next[bottomLevel].get(marked);
                while (true) {
                    boolean iMarkedIt =
                        nodeToRemove.next[bottomLevel].compareAndSet(succ, succ,
                            false, true);
                    succ = succs[bottomLevel].next[bottomLevel].get(marked);
                    if (iMarkedIt) {
                        find(x, preds, succs);
                        return true;
                    } else if (marked[0]) return false;
                }
            }
        }
    }

    public int randomLevel() {
        int level = (int) (Math.log(1. - Math.random()) / Math.log(1. - 0.5));
        return Math.min(level, MAX_LEVEL);

    }

    boolean find(T x, LockFreeCSLNode<T>[] preds, LockFreeCSLNode<T>[] succs) {
        int bottomLevel = 0;
        int key = x.hashCode();
        boolean[] marked = {false};
        boolean snip;
        LockFreeCSLNode<T> pred = null, curr = null, succ = null;
        retry:
        while (true) {
            pred = head;
            for (int level = MAX_LEVEL; level >= bottomLevel; level--) {
                curr = pred.next[level].getReference();
                while (true) {
                    succ = curr.next[level].get(marked);
                    while (marked[0]) {
                        snip = pred.next[level].compareAndSet(curr, succ,
                            false, false);
                        if (!snip) continue retry;
                        curr = pred.next[level].getReference();
                        succ = curr.next[level].get(marked);
                    }
                    if (curr.key < key) {
                        pred = curr;
                        curr = succ;
                    } else {
                        break;
                    }
                }
                preds[level] = pred;
                succs[level] = curr;
            }
            return (curr.key == key);
        }
    }

    public boolean contains(T x) {
        int bottomLevel = 0;
        int v = x.hashCode();
        boolean[] marked = {false};
        LockFreeCSLNode<T> pred = head, curr = null, succ = null;
        for (int level = MAX_LEVEL; level >= bottomLevel; level--) {
            curr = pred.next[level].getReference();
            while (true) {
                succ = curr.next[level].get(marked);
                while (marked[0]) {
                    curr = pred.next[level].getReference();
                    succ = curr.next[level].get(marked);
                }
                if (curr.key < v) {
                    pred = curr;
                    curr = succ;
                } else {
                    break;
                }
            }
        }
        return (curr.key == v);
    }

    public Integer size() {
        int count = 0;
        LockFreeCSLNode<T> currNode = head;
        while (currNode.next[0].getReference() != tail) {
            count++;
            currNode = currNode.next[0].getReference();
        }
        return count;
    }

}