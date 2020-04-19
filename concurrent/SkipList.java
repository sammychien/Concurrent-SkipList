package concurrent;

public interface SkipList<T> {
    public boolean delete(T a);
    public boolean insert(T a);
    public boolean contains(T a);
    public Integer size();
}