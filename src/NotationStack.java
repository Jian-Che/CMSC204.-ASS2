import java.util.ArrayList;

 
public class NotationStack<T> implements StackInterface<T> {

    private T data[];
    private int size;
    private int index ;


    public NotationStack(int size) {
        this.size = size;
        data =   (T[]) new Object[size];
        index = 0;
    }
    
    public NotationStack( ) {
        this.size == 10;
    }
    
    public boolean isEmpty() {
        return index == null;
    }

    public boolean isFull() {
        return index == size;
    }

    public T pop() throws StackUnderflowException {
        if (isEmpty()) {
            throw new StackUnderflowException("This stack is emply!");
        }else {
        return data[--index];
        }

    }

    public T top() throws StackUnderflowException {
        if (isEmpty()) {
            throw new StackUnderflowException("This stack is emply!");
        }else {
        return data[index-1];
    }
    }


    public int size() {
        return index;
    }

    
    public boolean push(T b) throws StackOverflowException {
        if (isFull()) {
            throw new StackOverflowException("This stack is full!");
        }

        data[index] = b;
        index++;
        return true;
    }

    public String toString(String delimiter) {
         StringBuilder sB = new StringBuilder();
        for (int i = 0; i <index; i++) {
            sB.append(data[i].toString());
            if(i!=index-1)
            sB.append(delimiter);
        }
        return sB.toString();
    }
    
    public String toString() {
         StringBuilder sT = new StringBuilder();
        for (int i = 0; i <index; i++) {
            sT.append(data[i].toString());

        }
        return sT.toString();
    }
    

    public void fill(ArrayList<T> list) {
        data =   (T[]) new Object[size];
        index = 0;
        try{
            
            for(T t:list) {
            push(t);
                
            }
        }catch(StackOverflowException e) {
            System.out.println(e);
        }
    }

}