package nz.ac.auckland.se281.datastructures;

public class LinkedList<T> {
  protected Node<T> head;
  protected Node<T> tail;
  protected int count;

  /**
   * Create new node with value and add to the front of the LinkedList.
   *
   * @param value value to assign to new node
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
   * Create new node with value and add to the end of the LinkedList.
   *
   * @param value value to assign to new node
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
   * Fetch the value of the head node.
   *
   * @return T value of head node
   */
  public T fetch() {
    return head.getValue();
  }

  /**
   * Remove the head node from LinkedList.
   *
   * @return T value of head node
   */
  public T removeHead() {
    T removedHeadValue = head.getValue();
    head = head.getNext();
    count--;
    return removedHeadValue;
  }

  /**
   * Number of elements in LinkedList.
   *
   * @return int numbers of nodes in the LinkedList
   */
  public int size() {
    return count;
  }
}
