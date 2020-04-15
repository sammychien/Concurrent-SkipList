package concurrent;


public class FineGrainedCSL<T> {
    final static int MAX_LEVEL = 32;
    final volatile Integer size;
    final volatile Integer height;
    final FineGrainedCSLNode<T> head = new FineGrainedCSLNode<T>(Integer.MIN_VALUE);
    final FineGrainedCSLNode<T> tail = new FineGrainedCSLNode<T>(Integer.MAX_VALUE);
    public LazySkipList() {
        for (int i = 0; i < head.next.length; i++) {
            head.next[i] = tail;
        }
        size = 0;
        height = 0;
    }
    public boolean isPresent(T a){
        
    }
    public boolean contains(T a){
        
    }
    public boolean insert(T a){
    
    }
    public boolean delete(T a){
    }
    public Integer size() {
        return size;
    }

    public Integer height() {
        return maxHeight+1; 
    }
    
}
