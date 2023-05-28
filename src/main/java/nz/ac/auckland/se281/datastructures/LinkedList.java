package nz.ac.auckland.se281.datastructures;

public class LinkedList<T> {
  protected Node<T> head;
  protected Node<T> tail;
  protected int count;

  /**
   * @param value
   */
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

  /**
   * @param value
   */
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

  /**
   * @return T
   */
  public T fetch() {
    return head.getValue();
  }

  /**
   * @return T
   */
  public T removeHead() {
    T removedHeadValue = head.getValue();
    head = head.getNext();
    count--;
    return removedHeadValue;
  }

  /**
   * @return int
   */
  public int size() {
    return count;
  }
}
