import java.util.*;
 

public class NotationQueue<T> implements QueueInterface<T> {

    protected T queue[];
    protected int first；
    protected int last；
    protected int initCapacity；
    protected int size;


    public NotationQueue(int n) {
        initCapacity = n;
        Size = 0;
        queue = (T[]) new Object[initCapacity];
        first = -1;
        last = -1;
    }

    public NotationQueue() {
        this.initCapacity = 10;
        this.queue =  (T[]) new Object[initCapacity];
    }

    
    public boolean isEmpty() {
        return first == null;
    }

    
    public boolean isFull() {
        return first == 0 && last == initCapacity - 1;
    }

    
    public T dequeue() throws QueueUnderflowException {
        if (isEmpty()) {
            throw new QueueUnderflowException("Underflow Exception");
        } else {
            Size--;
            
            T l = queue[first];
            
            
            if (first == last) {
                first = null;
                last = null;
            } else {
                first++;
            }
            return l;
        }
    }

    
    public int size() {
        return Size;
    }

    
    public boolean enqueue(T e) throws QueueOverflowException {

        if (last == -1) {
            first = null;
            last = null;
            queue[last] = e;

        } else if (last + 1 >= initCapacity) {
            throw new QueueOverflowException("Overflow Exception");
        } else{
            queue[++last] = e;
        }
        Size++;
        return true;
    }

   
    public String toString(String delimiter) {
        StringBuilder sB = new StringBuilder();
        for (int i = first; i <= last; i++) {
            sB.append(q[i].toString());
            if(i<last)
            sB.append(delimiter);
        }
        return sB.toString();
    }
    
    
    public String toString( ) {
        StringBuilder sT = new StringBuilder();
        for (int i = first; i <= last; i++) {
            sT.append(queue[i].toString());
             
        }
        return sT.toString();
    }
    

    public void fill(ArrayList<T> list) {
        size = 0;
        Object[] queue = (T[]) new Object[initCapacity];
        first = -1;
        last = -1;
        try {
            for (T t : list) {

                enqueue(t);

            }
        } catch (QueueOverflowException e) {
            System.out.println(e.toString());
        }
    }

}
