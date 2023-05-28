// AUTHOR: Tony Lim
// DATE CREATED: 26/05/2023
// LAST EDITED: 28/05/2023

package nz.ac.auckland.se281.datastructures;

/**
 * Node data structure. LinkedList is made up of nodes. Has a value and pointer to next node.
 *
 * @param <T> The type of each vertex, that have a total ordering.
 */
public class Node<T> {
  private T value;
  private Node<T> next;

  public Node(T vertex) {
    value = vertex;
    next = null;
  }

  /**
   * Set the next node in the list.
   *
   * @param node node to set next
   */
  public void setNext(Node<T> node) {
    next = node;
  }

  /**
   * Get the next node in the list.
   *
   * @return the next node in the list
   */
  public Node<T> getNext() {
    return next;
  }

  /**
   * Get the value of the current node.
   *
   * @return T value of current node
   */
  public T getValue() {
    return value;
  }
}
