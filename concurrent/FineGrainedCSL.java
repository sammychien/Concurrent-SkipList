package concurrent;


public class FineGrainedCSL<T> {
    final static int MAX_LEVEL = 32;
    volatile Integer size;
    volatile Integer height;
    final FineGrainedCSLNode<T> head = new FineGrainedCSLNode<T>(Integer.MIN_VALUE);
    final FineGrainedCSLNode<T> tail = new FineGrainedCSLNode<T>(Integer.MAX_VALUE);
    public FineGrainedCSL() {
        for (int i = 0; i < head.next.length; i++) {
            head.next[i] = tail;
        }
        size = 0;
        height = 0;
    }
    public boolean isPresent(T a){
        return false;
    }
    public boolean contains(T a){
        return false;
    }
    public boolean insert(T a){
    	return false;
    }
    public boolean delete(T a){
    	return false;
    }
    public Integer size() {
        return size;
    }

    public Integer height() {
        return height+1; 
    }
    
}
