package nz.ac.auckland.se281.datastructures;

public class LinkedList<T> {
  protected Node<T> head;
  protected Node<T> tail;
  protected int count;

  public LinkedList() {}

  public void prepend(T value) {
    Node<T> newNode = new Node<T>(value);
    if (head == null) {
      head = newNode;
      tail = newNode;
    } else {
      newNode.setNext(head);
      head = newNode;
    }
    count++;
  }

  public void append(T value) {
    Node<T> newNode = new Node<T>(value);
    if (head == null) {
      head = newNode;
      tail = newNode;
    } else {
      tail.setNext(newNode);
      tail = newNode;
    }
    count++;
  }

  public T fetch() {
    return head.getValue();
  }

  public T removeHead() {
    head = head.getNext();
    count--;
    return head.getValue();
  }

  public int size() {
    return count;
  }
}
