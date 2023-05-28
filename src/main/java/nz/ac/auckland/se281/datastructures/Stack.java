// AUTHOR: Tony Lim
// DATE CREATED: 25/05/2023
// LAST EDITED: 28/05/2023

package nz.ac.auckland.se281.datastructures;

/**
 * Stack linear data structure, where operations are performed in a First In Last Out (FILO) order.
 *
 * @param <T> The type of each vertex, that have a total ordering.
 */
public class Stack<T> {
  private LinkedList<T> stack;

  public Stack() {
    stack = new LinkedList<T>();
  }

  /**
   * Adds a new node to the top of the stack.
   *
   * @param node node to add to stack
   */
  public void push(T node) {
    stack.prepend(node);
  }

  /**
   * Removes the node at the top of the stack.
   *
   * @return T node at top of stack
   */
  public T pop() {
    T poppedNode = stack.fetch();
    stack.removeHead();
    return poppedNode;
  }

  /**
   * Calculates the current size of the stack.
   *
   * @return int number of elements in stack
   */
  public int size() {
    return stack.size();
  }

  /**
   * Check if stack is empty or not.
   *
   * @return boolean true if the stack is empty, false otherwise
   */
  public boolean isEmpty() {
    if (stack.size() == 0) {
      return true;
    } else {
      return false;
    }
  }
}
