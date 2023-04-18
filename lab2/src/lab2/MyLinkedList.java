//import MyLinkedList.Node;

package lab2;

public class MyLinkedList<E> implements MyList<E> {
    protected Node<E> head, tail;
    protected int size = 0; // Number of elements in the list

    /** Create an empty list */
    public MyLinkedList() {
    }

    /** Create a list from an array of objects */
    public MyLinkedList(E[] objects) {
        for(E item: objects) {
                addLast(item);  
        }
    }

    /** Return the head element in the list */
    public E getFirst() {
        return head.element;
    }

    /** Return the last element in the list */
    public E getLast() {
            return tail.element;
    }

    /** Add an element to the beginning of the list */
    public void addFirst(E e) {
        Node<E> tmp = new Node<E>(e);
        if(size == 0){
            head = tail = tmp;
            size ++;
            return;
        }
        tmp.next = head;
        head = tmp;
        size ++;
    }

    /** Add an element to the end of the list */
    public void addLast(E e) {
            Node<E> tmp = new Node<E>(e);
        if(size == 0){
            head = tail = tmp;
            size ++;
            return;
        }
            tail.next = tmp;
            tail = tmp;
            size ++;
    }

    @Override /** Add a new element at the specified index 
     * in this list. The index of the head element is 0 */
    public void add(int index, E e) {
            Node<E> tmp = head;
            if(index == 0){
                addFirst(e);   
            } else if(index >= size) {
            addLast(e);
            } else {
            for(;(--index) != 0; tmp = tmp.next);
            Node<E> new_element = new Node<E>(e);
            new_element.next = tmp.next;
            tmp.next = new_element;
            if(new_element.next == null) tail = new_element;
            size ++;
            }
    }

    /** Remove the head node and
     *  return the object that is contained in the removed node. */
    public E removeFirst() {
        E ret = head.element;
        if(size == 1){
            head = tail = null;
        } else {
                head = head.next;
        }
        size --;
            return ret;
    }

    /** Remove the last node and
     * return the object that is contained in the removed node. */
    public E removeLast() {
        return remove(size - 1);
    }

    @Override /** Remove the element at the specified position in this 
     *  list. Return the element that was removed from the list. */
    public E remove(int index) {   
        if(index == 0){
            return removeFirst();
        } else {
                Node<E> tmp = head;
            for(;(--index) != 0  && tmp.next != null; tmp = tmp.next);
            E ret = tmp.next.element;
            tmp.next = tmp.next.next;
            size --;
            return ret;
        }
    }

    @Override /** Override toString() to return elements in the list */
    public String toString() {
        StringBuilder result = new StringBuilder("[");

        Node<E> current = head;
        for (int i = 0; i < size; i++) {
            result.append(current.element);
            current = current.next;
            if (current != null) {
                result.append(", "); // Separate two elements with a comma
            }
            else {
                result.append("]"); // Insert the closing ] in the string
            }
        }

        return result.toString();
    }

    @Override /** Clear the list */
    public void clear() {
        size = 0;
        head = tail = null;
    }

    @Override /** Return true if this list contains the element e */
    public boolean contains(Object e) {
        for(Node<E> cur = head; cur != null; cur = cur.next)
            if(cur.element.equals(e)) return true;
            return false;
    }

    @Override /** Return the element at the specified index */
    public E get(int index) {
        Node<E> tmp = head;
        for(;(index--) != 0  && tmp.next != null; tmp = tmp.next);
            return tmp.element;
    }

    @Override /** Return the index of the first matching element in 
     *  this list. Return -1 if no match. */
    public int indexOf(Object e) {
        int index = 0;
        for(Node<E> cur = head; cur != null; cur = cur.next, index++)
            if(cur.element.equals( e)) return index;
            return -1;
    }

    @Override /** Return the index of the last matching element in 
     *  this list. Return -1 if no match. */
    public int lastIndexOf(E e) {
        int index = 0, ret = -1;
        for(Node<E> cur = head; cur != null; cur = cur.next, index++)
            if(cur.element.equals( e)) ret = index;
            return ret;
    }

    @Override /** Replace the element at the specified position 
     *  in this list with the specified element. */
    public E set(int index, E e) {
        Node<E> new_element = new Node<E>(e);
        Node<E> tmp = head;
        E ret;
        if(index == 0){
            new_element.next = head.next;
            ret = head.element;
            head = new_element;
            return ret;
        } else {
            for(;(--index) != 0  && tmp.next != null; tmp = tmp.next);
            ret = tmp.next.element;
            new_element.next = tmp.next.next;
            tmp.next = new_element;
            if(new_element.next == null) tail = new_element;
            return ret;
        }
    }

    @Override /** Override iterator() defined in Iterable */
    public java.util.Iterator<E> iterator() {
        return new LinkedListIterator();
    }

    private class LinkedListIterator 
    implements java.util.Iterator<E> {
        private Node<E> current = head; // Current node 
        private int index=-1; // initial index before head

        @Override
        public boolean hasNext() {
            return (current != null);
        }

        @Override
        public E next() {
            E e = current.element;
            index++;    
            current = current.next;
            return e;
        }

        @Override
        // remove the last element returned by the iterator
        public void remove() {
            MyLinkedList.this.remove(index);    
        }
    }

    protected static class Node<E> {
        protected E element;
        protected Node<E> next;
        Node(E k){
            element = k;
            next = null; 
        }
    }

    @Override /** Return the number of elements in this list */
    public int size() {
        return size;
    }
}