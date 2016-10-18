/**
 * Created by EliasBrattli on 10/09/2016.
 * Oppgave 1
 */
@SuppressWarnings("unchecked")
public class Node<E> {
    private E element;
    private Node next;
    private Node prev;
    public Node(E element, Node next, Node prev){
        this.element = element;
        this.next = next;
        this.prev = prev;
    }
    @SuppressWarnings("unchecked")
    public E getElement(){
        return element;
    }
    @SuppressWarnings("unchecked")
    public Node getNext(){
        return next;
    }
    @SuppressWarnings("unchecked")
    public Node getPrev() {
        return prev;
    }

    public void setElement(E element) {
        this.element = element;
    }
    @SuppressWarnings("unchecked")
    public void setNext(Node next) {
        this.next = next;
    }
    @SuppressWarnings("unchecked")
    public void setPrev(Node prev) {
        this.prev = prev;
    }
    @Override
    public String toString(){
        return ""+element;
    }
}
