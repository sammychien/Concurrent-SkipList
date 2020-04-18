package concurrent;
import java.util.concurrent.atomic.AtomicMarkableReference;

public class LockFreeCSL<T> {
    static final int MAX_LEVEL = 32;
    final LockFreeCSLNode<T> head = new LockFreeCSLNode<T>(Integer.MIN_VALUE);
    final LockFreeCSLNode<T> tail = new LockFreeCSLNode<T>(Integer.MAX_VALUE);

    public LockFreeCSL() {
        for (int i = 0; i < head.next.length; i++) {
            head.next[i] = new AtomicMarkableReference<LockFreeCSLNode<T>>(tail, false);
        }
    }
}