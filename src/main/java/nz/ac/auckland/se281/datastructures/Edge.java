// AUTHOR: Tony Lim
// DATE CREATED: 19/05/2023
// LAST EDITED: 28/05/2023

package nz.ac.auckland.se281.datastructures;

/**
 * An edge in a graph that connects two verticies.
 *
 * <p>You must NOT change the signature of the constructor of this class.
 *
 * @param <T> The type of each vertex.
 */
public class Edge<T> {
  private T source;
  private T destination;

  public Edge(T source, T destination) {
    this.source = source;
    this.destination = destination;
  }

  /**
   * Gets the vertex the edge leaves from.
   *
   * @return T value of source vertex
   */
  public T getSource() {
    return source;
  }

  /**
   * Gets the vertex the edge arrives at.
   *
   * @return T value of destination vertex
   */
  public T getDestination() {
    return destination;
  }
}
