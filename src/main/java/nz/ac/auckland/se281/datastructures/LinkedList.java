// AUTHOR: Tony Lim
// DATE CREATED: 26/05/2023
// LAST EDITED: 04/06/2023

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

  /**
   * Checks if value if in LinkedList.
   *
   * @param value value to check if in LinkedList
   * @return true if value is in LinkedList, false if not.
   */
  public boolean contains(T value) {
    for (int i = 0; i < count; i++) {
      if (this.get(i) == value) {
        return true;
      }
    }
    return false;
  }

  /**
   * Returns value at index in LinkedList.
   *
   * @param index index of node in LinkedList to find
   * @return value of node at given index
   */
  public T get(int index) {
    return traverseList(index).getValue();
  }

  /**
   * Helper method that iterates through this LinkedList and returns node at given index.
   *
   * @param index index of node to get
   * @return node at index given
   */
  public Node<T> traverseList(int index) {
    Node<T> nodeAtIndex;

    if (index != 0) {
      nodeAtIndex = head;
      // iterate through
      for (int i = 0; i < index; i++) {
        nodeAtIndex = nodeAtIndex.getNext();
      }
    } else {
      // If index = 0, return the only node in the LinkedList; head
      return head;
    }
    return nodeAtIndex;
  }

  /**
   * Add all nodes in given LinkedList to this LinkedList.
   *
   * @param list LinkedList to get all nodes from to add
   */
  public void addAll(LinkedList<T> list) {
    for (int i = 0; i < list.size(); i++) {
      this.append(list.get(i));
    }
  }
}
