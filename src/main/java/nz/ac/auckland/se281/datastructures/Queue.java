// AUTHOR: Tony Lim
// DATE CREATED: 25/05/2023
// LAST EDITED: 25/05/2023

package nz.ac.auckland.se281.datastructures;

public class Queue<T> {
  private LinkedList<T> queue;

  public Queue() {
    queue = new LinkedList<T>();
  }

  /**
   * Adds an element to the back of the queue
   *
   * @param node
   */
  public void enqueue(T node) {
    queue.append(node);
  }

  /**
   * Removes the element from the front of the queue
   *
   * @return T
   */
  public T dequeue() {
    return queue.removeHead();
  }

  /**
   * Size of queue
   *
   * @return int number of elements in queue
   */
  public int size() {
    return queue.size();
  }

  /**
   * Check if queue is empty or not
   *
   * @return boolean true if the queue is empty, false otherwise
   */
  public boolean isEmpty() {
    if (queue.size() == 0) {
      return true;
    } else {
      return false;
    }
  }
}
