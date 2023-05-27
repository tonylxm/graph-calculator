// AUTHOR: Tony Lim
// DATE CREATED: 25/05/2023
// LAST EDITED: 25/05/2023

package nz.ac.auckland.se281.datastructures;

public class Queue<T> {
  private LinkedList<T> queue;

  public Queue() {
    queue = new LinkedList<T>();
  }

  public void enqueue(T node) {
    queue.append(node);
  }

  public T dequeue() {
    return queue.removeHead();
  }

  public int size() {
    return queue.size();
  }

  public boolean isEmpty() {
    if (queue.size() == 0) return true;
    else return false;
  }
}
