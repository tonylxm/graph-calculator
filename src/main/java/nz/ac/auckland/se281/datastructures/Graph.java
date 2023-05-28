// AUTHOR: Tony Lim
// DATE CREATED: 19/05/2023
// LAST EDITED: 28/05/2023

package nz.ac.auckland.se281.datastructures;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A graph that is composed of a set of verticies and edges.
 *
 * <p>You must NOT change the signature of the existing methods or constructor of this class.
 *
 * @param <T> The type of each vertex, that have a total ordering.
 */
public class Graph<T extends Comparable<T>> {
  private final Set<T> verticies;
  private final Set<Edge<T>> edges;
  private HashMap<T, Set<T>> adjacencyMap;

  public Graph(Set<T> verticies, Set<Edge<T>> edges) {
    this.verticies = verticies;
    this.edges = edges;
    adjacencyMap = new HashMap<>();

    // populate adjacencyMap with values
    for (T vertex : verticies) {
      adjacencyMap.put(vertex, destinationsWithSameSourceVertex(vertex));
    }
  }

  /**
   * The roots of the graph are vertices that are not the destination of any edge (apart from
   * self-loops). If the vertex is part of an equivalence class, it is a root vertice. If there are
   * multiple vertices in one equivalence class, return the minimum value.
   *
   * @return set of root vertices
   */
  public Set<T> getRoots() {
    // Set of rootVertices is a subset of vertices of a graph
    Set<T> rootVertices = new HashSet<T>();
    Set<T> setOfAllDestinationVertices = new HashSet<T>();

    // Form setOfAllDestinationVertices
    for (T vertex : verticies) {
      setOfAllDestinationVertices.addAll(adjacencyMap.get(vertex));
    }

    // Compare setOfAllDestinationVertices with verticies -> intersection are root nodes
    for (T vertex : verticies) {
      // If the node is a not a destination of any edge, then it is a root vertex
      // UNLESS it is a isolated node with a self-loop
      if (!setOfAllDestinationVertices.contains(vertex)) {
        rootVertices.add(vertex);
      }
    }

    for (T vertex : verticies) {
      if (!getEquivalenceClass(vertex).isEmpty()) {
        rootVertices.add(Collections.min(getEquivalenceClass(vertex)));
      }
    }
    return rootVertices;
  }

  /**
   * For all vertices, vertex -> vertex (self-loop) must exist for this graph to be reflexive.
   *
   * @return boolean value - true if the graph is reflexive, false if not reflexive
   */
  public boolean isReflexive() {

    for (T vertex : verticies) {
      if (!adjacencyMap.get(vertex).contains(vertex)) {
        return false;
      }
    }
    return true;
  }

  /**
   * If vertex1 -> vertex2, then vertex2 -> vertex1 must exist for this graph to be symmetric.
   *
   * @return boolean value - true if the graph is symmetric, false if not symmetric
   */
  public boolean isSymmetric() {
    for (T vertex1 : verticies) {
      for (T vertex2 : adjacencyMap.get(vertex1)) {
        if (!adjacencyMap.get(vertex2).contains(vertex1)) {
          return false;
        }
      }
    }
    return true;
  }

  /**
   * If vertex1 -> vertex2 and vertex2 -> vertex3 then vertex1 -> vertex3 must exist for this graph
   * to be transitive.
   *
   * @return boolean value - true if the graph is transitive, false if not transitive
   */
  public boolean isTransitive() {
    for (T vertex1 : verticies) {
      for (T vertex2 : adjacencyMap.get(vertex1)) {
        for (T vertex3 : adjacencyMap.get(vertex2)) {
          if (!adjacencyMap.get(vertex3).contains(vertex1)) {
            return false;
          }
        }
      }
    }
    return true;
  }

  /**
   * If vertex1 -> vertex2 and vertex2 -> vertex3 then vertex1 must equal vertex3 for this graph to
   * be antisymmetric.
   *
   * @return boolean value - true if the graph is antisymmetric, false if not antisymmetric
   */
  public boolean isAntiSymmetric() {
    for (T vertex1 : verticies) {
      for (T vertex2 : adjacencyMap.get(vertex1)) {
        for (T vertex3 : adjacencyMap.get(vertex2)) {
          if (vertex3 == vertex1) {
            // Self-loops allowed (self-loop if vertex1 and vertex2 (destinations of edges leaving
            // vertex1 are the same vertex)
            if (vertex1 != vertex2) {
              return false;
            }
          }
        }
      }
    }
    return true;
  }

  /**
   * If the graph is reflexive, symmetric and transitive, then it is an equivalence relation.
   *
   * @return boolean value - true if the graph is equivalent , false if not equivalent
   */
  public boolean isEquivalence() {
    if (isReflexive() && isSymmetric() && isTransitive()) {
      return true;
    }
    return false;
  }

  /**
   * An equivalence class of vertex1 is the set of all vertices that can be reached from vertex1.
   *
   * @param vertex vertex to get equivalence class for
   * @return Set of vertices in the equivalence class
   */
  public Set<T> getEquivalenceClass(T vertex) {
    Set<T> equivalenceClass = new HashSet<T>();

    // Set needs to have an Equivalence relation to have equivalence classes
    if (!isEquivalence()) {
      return equivalenceClass;
    } else {
      for (T destination : adjacencyMap.get(vertex)) {
        equivalenceClass.add(destination);
      }
      return equivalenceClass;
    }
  }

  /**
   * Conducts a breadth first search iteratively on the graph. Visits all vertices at current depth
   * before increasing search depth. Vertices at same depth are visited in numerical order.
   *
   * @return List of vertices in order of visited
   */
  public List<T> iterativeBreadthFirstSearch() {
    List<T> visited = new ArrayList<T>();
    List<T> nodesAtCurrentDepth = new ArrayList<T>();
    Queue<T> queue = new Queue<T>();
    T current;

    for (T root : getRoots()) {
      queue.enqueue(root);
      visited.add(root);

      while (!queue.isEmpty()) {
        current = queue.dequeue();
        for (T neighbour : adjacencyMap.get(current)) {
          if (!visited.contains(neighbour)) {
            nodesAtCurrentDepth.add(neighbour);
          }
        }
        // Nodes at the same search depth should be visited in numerical order
        Collections.sort(nodesAtCurrentDepth);
        visited.addAll(nodesAtCurrentDepth);

        // queue the sorted nodes at the same search depth
        for (T node : nodesAtCurrentDepth) {
          queue.enqueue(node);
        }
        nodesAtCurrentDepth.clear();
      }
    }
    return visited;
  }

  /**
   * Conducts a depth first search iteratively on the graph. Visits vertices giving priority to
   * visit vertices with higher search depth first. Vertices at same depth are visited in numerical
   * order.
   *
   * @return List of vertices in order of visited
   */
  public List<T> iterativeDepthFirstSearch() {
    List<T> visited = new ArrayList<T>();
    List<T> nodesAtCurrentDepth = new ArrayList<T>();
    Stack<T> stack = new Stack<T>();
    T current;

    for (T root : getRoots()) {
      stack.push(root);

      while (!stack.isEmpty()) {
        current = stack.pop();
        if (!visited.contains(current)) {
          visited.add(current);
          for (T neighbour : adjacencyMap.get(current)) {
            nodesAtCurrentDepth.add(neighbour);
          }
          // Nodes at the same search depth should be visited in numerical order, but need to
          // reverse for the nature of the stack data structure
          Collections.sort(nodesAtCurrentDepth);
          Collections.reverse(nodesAtCurrentDepth);

          // stack the sorted nodes at the same search depth
          for (T node : nodesAtCurrentDepth) {
            stack.push(node);
          }
          nodesAtCurrentDepth.clear();
        }
      }
    }
    return visited;
  }

  /**
   * Calls the recursiveBfs function, starting the root vertex (if multiple roots, start at lowest
   * numerical root and then subsequent subgraphs are searched sequentially).
   *
   * @return List of vertices in order of visited
   */
  public List<T> recursiveBreadthFirstSearch() {
    List<T> visited = new ArrayList<T>();
    Queue<T> queue = new Queue<T>();

    for (T root : getRoots()) {
      queue.enqueue(root);
      visited.add(root);
      recursiveBfs(root, queue, visited); // Call the recursive function
    }
    return visited;
  }

  /**
   * Conducts a breadth first search recursively on the graph. Visits all vertices at current depth
   * before increasing search depth. Vertices at same depth are visited in numerical order.
   */
  public void recursiveBfs(T vertex, Queue<T> queue, List<T> visited) {
    List<T> nodesAtCurrentDepth = new ArrayList<T>();
    T current;

    while (!queue.isEmpty()) {
      current = queue.dequeue();
      for (T neighbour : adjacencyMap.get(current)) {
        if (!visited.contains(neighbour)) {
          nodesAtCurrentDepth.add(neighbour);
        }
      }
      // Nodes at the same search depth should be visited in numerical order
      Collections.sort(nodesAtCurrentDepth);
      visited.addAll(nodesAtCurrentDepth);

      // queue the sorted nodes at the same search depth
      for (T node : nodesAtCurrentDepth) {
        queue.enqueue(node);
        recursiveBfs(node, queue, visited); // Call the recursive function
      }
    }
  }

  /**
   * Calls the recursiveDfs function, starting the root vertex (if multiple roots, start at lowest
   * numerical root and then subsequent subgraphs are searched sequentially).
   *
   * @return List of vertices in order of visited
   */
  public List<T> recursiveDepthFirstSearch() {
    List<T> visited = new ArrayList<T>();
    Stack<T> stack = new Stack<T>();

    for (T root : getRoots()) {
      stack.push(root);
      recursiveDfs(root, stack, visited); // Call the recursive function
    }
    return visited;
  }

  /**
   * Conducts a depth first search iteratively on the graph. Visits vertices giving priority to
   * visit vertices with higher search depth first. Vertices at same depth are visited in numerical
   * order.
   */
  public void recursiveDfs(T vertex, Stack<T> stack, List<T> visited) {
    List<T> nodesAtCurrentDepth = new ArrayList<T>();
    T current;

    while (!stack.isEmpty()) {
      current = stack.pop();
      if (!visited.contains(current)) {
        visited.add(current);
        for (T neighbour : adjacencyMap.get(current)) {
          nodesAtCurrentDepth.add(neighbour);
        }
        // Nodes at the same search depth should be visited in numerical order
        Collections.sort(nodesAtCurrentDepth);

        // stack the sorted nodes at the same search depth
        for (T node : nodesAtCurrentDepth) {
          stack.push(node);
          if (!visited.contains(node)) {
            recursiveDfs(node, stack, visited); // Call the recursive function
          }
        }
      }
    }
  }

  /**
   * Iteratively goes through all edges and finds all destination vertices with the parameterised
   * vertex as the source.
   *
   * @param vertex vertex to find neighbour vertices from
   * @return Set of destination vertices of vertex in parameter
   */
  public Set<T> destinationsWithSameSourceVertex(T vertex) {
    Set<T> destinationsWithSameSourceVertex = new HashSet<T>();

    for (Edge<T> edge : edges) {
      if (edge.getSource().equals(vertex)) {
        destinationsWithSameSourceVertex.add(edge.getDestination());
      }
    }
    return destinationsWithSameSourceVertex;
  }
}
