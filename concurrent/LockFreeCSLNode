public static final class Node<T> {
  final T value; final int key;
  final AtomicMarkableReference<Node<T>>[] next;
  private int topLevel;
  // constructor for sentinel nodes
  public Node(int key) {
    value = null; key = key;
    next = (AtomicMarkableReference<Node<T>>[])
    new AtomicMarkableReference[MAX_LEVEL + 1];
    for (int i = 0; i < next.length; i++) {
      next[i] = new AtomicMarkableReference<Node<T>>(null,false);
    }
    topLevel = MAX_LEVEL;
  }
  // constructor for ordinary nodes
  public Node(T x, int height) {
    value = x;
    key = x.hashCode();
    next = (AtomicMarkableReference<Node<T>>[])
    new AtomicMarkableReference[height + 1];
    for (int i = 0; i < next.length; i++) {
      next[i] = new AtomicMarkableReference<Node<T>>(null,false);
    }
    topLevel = height;
  }
}
