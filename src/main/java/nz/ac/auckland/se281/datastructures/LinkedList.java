// AUTHOR: Tony Lim
// DATE CREATED: 26/05/2023
// LAST EDITED: 28/05/2023

package nz.ac.auckland.se281.datastructures;

/**
 * LinkedList dynamic data structure, being used to implement both stack and queue. Is comprised of
 * node that point to the next node for easy traversal, insertion and deletion.
 *
 * @param <T> The type of each vertex, that have a total ordering.
 */
public class LinkedList<T> {
  private Node<T> head;
  private Node<T> tail;
  private int count = 0;

  /**
   * Create new node with value and add to the front of the LinkedList.
   *
   * @param value value to assign to new node
   */
  public void prepend(T value) {
    Node<T> newNode = new Node<T>(value);

    // If it is the first node to be added, both head and tail pointers point to this node.
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

    // If it is the first node to be added, both head and tail pointers point to this node.
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
