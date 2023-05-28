// AUTHOR: Tony Lim
// DATE CREATED: 25/05/2023
// LAST EDITED: 25/05/2023

package nz.ac.auckland.se281.datastructures;

public class Stack<T> {
  private LinkedList<T> stack;

  public Stack() {
    stack = new LinkedList<T>();
  }

  // Adds a new node to the top of the stack
  public void push(T node) {
    stack.prepend(node);
  }

  // Removes the node at the top of the stack
  public T pop() {
    T poppedNode = stack.fetch();
    stack.removeHead();
    return poppedNode;
  }

  // Returns the number of nodes in the stack
  public int size() {
    return stack.size();
  }

  public boolean isEmpty() {
    if (stack.size() == 0) {
      return true;
    } else {
      return false;
    }
  }
}
