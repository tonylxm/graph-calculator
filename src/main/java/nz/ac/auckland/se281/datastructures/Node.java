package nz.ac.auckland.se281.datastructures;

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
   * @return Node<T> next node in list
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
