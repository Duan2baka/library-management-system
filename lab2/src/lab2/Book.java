package lab2;

public class Book {
    private String title;
    private String ISBN;
    private boolean available = true;
    private MyQueue<String> reservedQueue = new MyQueue<String>();
    
    public Book(){}
    public Book(String _title, String _ISBN) {
        title = _title;
        ISBN = _ISBN;
    }
    public void setISBN(String _ISBN){
        ISBN = _ISBN;
    }
    public void setTitle(String _title){
        title = _title;
    }
    public void setAvailable(boolean flag){
        available = flag;
    }
    public MyQueue<String> getReservedQueue(){
        return reservedQueue;
    }
    public String getISBN(){
        return ISBN;
    }
    public String getTitle(){
        return title;
    }
    public boolean isAvailable(){
        return available;
    }
}
