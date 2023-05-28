package nz.ac.auckland.se281.datastructures;

public class Node<T> {
  private T value;
  private Node<T> next;

  public Node(T vertex) {
    value = vertex;
    next = null;
  }

  /**
   * @param node node to set next in list
   */
  public void setNext(Node<T> node) {
    next = node;
  }

  /**
   * @return Node<T> next node in list
   */
  public Node<T> getNext() {
    return next;
  }

  /**
   * @return T value of current node
   */
  public T getValue() {
    return value;
  }
}
