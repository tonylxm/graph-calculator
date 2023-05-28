// AUTHOR: Tony Lim
// DATE CREATED: 19/05/2023
// LAST EDITED: 24/05/2023

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
      Set<T> destinationsWithSameSource = new HashSet<T>();
      destinationsWithSameSource = DestinationsWithSameSourceVertex(vertex);
      adjacencyMap.put(vertex, destinationsWithSameSource);
    }
  }

  public Set<T> getRoots() {
    // Set of rootVertices is a subset of vertices of a graph
    Set<T> rootVertices = new HashSet<T>();
    Set<T> equivalenceClass = new HashSet<T>();

    // Compare SetOfAllDestinationVertices with verticies -> intersection are root nodes
    // UNLESS it is a isolated node with a self-loop?
    for (T vertex : verticies) {
      // If the node is a not a destination of any edge, then it is a root vertice
      if (!SetOfAllDestinationVertices().contains(vertex)) {
        rootVertices.add(vertex);
      }
    }

    // If the vertex is part of an equivalence class, it is a root vertice. If there are
    // multiple vertices in one equivalence class, return the minimum value
    for (T vertex : verticies) {
      equivalenceClass = getEquivalenceClass(vertex);
      if (!equivalenceClass.isEmpty()) {
        rootVertices.add(Collections.min(equivalenceClass));
      }
    }
    return rootVertices;
  }

  public boolean isReflexive() {
    // NOT ALLOWED HashSet()

    // If there is a reflexive relation, add that vertice to reflexiveVertices..
    for (T vertex : verticies) {
      if (!adjacencyMap.get(vertex).contains(vertex)) {
        return false;
      }
    }
    return true;
  }

  public boolean isSymmetric() {
    // check counts the amount of symmetric relations
    int check = 0;

    for (Edge<T> edge1 : edges) {
      for (Edge<T> edge2 : edges) {
        if (edge1.getSource() == edge2.getDestination()
            && edge2.getSource() == edge1.getDestination()) {
          check += 1;
          break;
        }
      }
    }

    // if all edges have a symmetric pair, then the graph is symmetric
    if (check == edges.size()) {
      return true;
    }
    return false;
  }

  public boolean isTransitive() {
    // check = 0 means transitive relation is NOT found
    // check = 1 means transitive relation is found
    int check = 0;

    for (T vertex1 : verticies) {
      for (T vertex2 : adjacencyMap.get(vertex1)) {
        for (T vertex3 : adjacencyMap.get(vertex2)) {
          // if a -> b and b -> c then a -> c must exist for this relation to be transitive
          if (vertex3 == vertex1) {
            check = 1;
            break;
          } else {
            check = 0;
          }
        }
        // if a -> c (transitive relation) is not found, then the whole graph is not transitive
        if (check != 1) {
          return false;
        }
      }
    }
    return true;
  }

  public boolean isAntiSymmetric() {
    // '==' rather than '.equals()' as we want the same reference of same object, not just same
    // node?
    // number

    for (T vertex1 : verticies) {
      for (T vertex2 : adjacencyMap.get(vertex1)) {
        for (T vertex3 : adjacencyMap.get(vertex2))
          // if a -> b and b -> c then c is a for this relation to be antisymmetric
          if (vertex3 == vertex1) {
            // Self-loops allowed (self-loop if vertex1 and vertex2 (destinations of edges leaving
            // vertex1 are the same vertex)
            if (vertex1 != vertex2) {
              return false;
            }
          }
      }
    }
    return true;
  }

  public boolean isEquivalence() {
    if (isReflexive() && isSymmetric() && isTransitive()) {
      return true;
    }
    return false;
  }

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

  public List<T> iterativeBreadthFirstSearch() {
    // BFS - queue FILO

    Set<T> roots = getRoots();
    List<T> visited = new ArrayList<T>();
    List<T> nodesAtCurrentDepth = new ArrayList<T>();
    Queue<T> queue = new Queue<T>();
    T current;

    // if getRoots().isEmpty... -> min value?

    for (T root : roots) {
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

  public List<T> iterativeDepthFirstSearch() {
    // DFS - stack
    Set<T> roots = getRoots();
    List<T> visited = new ArrayList<T>();
    List<T> nodesAtCurrentDepth = new ArrayList<T>();
    Stack<T> stack = new Stack<T>();
    T current;

    for (T root : roots) {
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

  public List<T> recursiveBreadthFirstSearch() {
    Set<T> roots = getRoots();
    List<T> visited = new ArrayList<T>();
    Queue<T> queue = new Queue<T>();

    for (T root : roots) {
      queue.enqueue(root);
      visited.add(root);
      recursiveBfs(root, queue, visited);
    }
    return visited;
  }

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
        recursiveBfs(node, queue, visited);
      }
    }
  }

  public List<T> recursiveDepthFirstSearch() {
    Set<T> roots = getRoots();
    List<T> visited = new ArrayList<T>();
    Stack<T> stack = new Stack<T>();

    for (T root : roots) {
      stack.push(root);
      recursiveDfs(root, stack, visited);
    }
    return visited;
  }

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
            recursiveDfs(node, stack, visited);
          }
        }
      }
    }
  }

  // helper
  public Set<T> SetOfAllDestinationVertices() {
    Set<T> allDestinationVertices = new HashSet<T>();

    for (Edge<T> edge : edges) {
      allDestinationVertices.add(edge.getDestination());
    }
    return allDestinationVertices;
  }

  public Set<T> DestinationsWithSameSourceVertex(T vertex) {
    Set<T> destinationsWithSameSourceVertex = new HashSet<T>();

    for (Edge<T> edge : edges) {
      if (edge.getSource().equals(vertex)) {
        destinationsWithSameSourceVertex.add(edge.getDestination());
      }
    }
    return destinationsWithSameSourceVertex;
  }
}
