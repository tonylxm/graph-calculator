package nz.ac.auckland.se281.datastructures;

public class Node<T> {
  private T value;
  private Node<T> next;

  public Node(T vertex) {
    value = vertex;
    next = null;
  }

  public void setNext(Node<T> node) {
    next = node;
  }

  public Node<T> getNext() {
    return next;
  }

  public T getValue() {
    return value;
  }
}
