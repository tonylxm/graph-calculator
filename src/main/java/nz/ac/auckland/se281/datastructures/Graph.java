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
  private HashMap<T, Set<Edge<T>>> adjacencyMap;

  // private HashMap<T, LinkedList<T>> adjacencyMap;

  public Graph(Set<T> verticies, Set<Edge<T>> edges) {
    this.verticies = verticies;
    this.edges = edges;
    adjacencyMap = new HashMap<>();

    // populate adjacencyMap with values
    for (T vertex : verticies) {
      Set<Edge<T>> EdgesWithSameSource = new HashSet<Edge<T>>();
      EdgesWithSameSource = EdgesWithSameSourceVertex(vertex);
      adjacencyMap.put(vertex, EdgesWithSameSource);
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
    Set<T> reflexiveVertices = new HashSet<T>();

    // If there is a reflexive relation, add that vertice to reflexiveVertices .
    for (Edge<T> edge : edges) {
      if (edge.getSource() == edge.getDestination()) {
        reflexiveVertices.add(edge.getDestination());
      }
    }

    // if reflexiveVertices contain all vertices, the whole graph is reflexive
    if (reflexiveVertices.containsAll(verticies)) {
      return true;
    }
    return false;
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

    for (Edge<T> edge1 : edges) {
      for (Edge<T> edge2 : adjacencyMap.get(edge1.getDestination())) {
        for (Edge<T> edge3 : adjacencyMap.get(edge1.getSource())) {
          // if a -> b and b -> c then a -> c must exist for this relation to be transitive
          if (edge3.getDestination() == edge2.getDestination()) {
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

    for (Edge<T> edge1 : edges) {
      for (Edge<T> edge2 : adjacencyMap.get(edge1.getDestination())) {
        // if a -> b and b -> c then c is a for this relation to be antisymmetric
        if (edge2.getDestination() == edge1.getSource()) {
          // Self-loops allowed (self-loop if edge1 and edge2 are the same edge)
          if (!(edge1 == edge2)) {
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
      // Reachbility to other vertices from input vertex
      // FIX - BFS?
      for (Edge<T> edge : adjacencyMap.get(vertex)) {
        equivalenceClass.add(edge.getDestination());
      }
      return equivalenceClass;
    }
  }

  public List<T> iterativeBreadthFirstSearch() {
    // BFS - queue FILO

    Set<T> roots = getRoots();
    List<T> visited = new ArrayList<T>();
    List<T> sortedNodeAtCurrentDepth = new ArrayList<T>();
    Queue<T> queue = new Queue<T>();
    T current;

    for (T root : roots) {
      queue.enqueue(root);
      visited.add(root);

      while (!queue.isEmpty()) {
        current = queue.dequeue();
        for (Edge<T> neighbour : adjacencyMap.get(current)) {
          if (!visited.contains(neighbour.getDestination())) {
            sortedNodeAtCurrentDepth.add(neighbour.getDestination());
          }
        }
        // Nodes at the same search depth should be visited in numerical order
        Collections.sort(sortedNodeAtCurrentDepth);
        visited.addAll(sortedNodeAtCurrentDepth);

        // queue the sorted nodes at the same search depth
        for (T node : sortedNodeAtCurrentDepth) {
          queue.enqueue(node);
        }
        sortedNodeAtCurrentDepth.clear();
      }
    }
    return visited;
  }

  public List<T> iterativeDepthFirstSearch() {
    // DFS - stack
    // TODO: Task 2.
    throw new UnsupportedOperationException();
  }

  public List<T> recursiveBreadthFirstSearch() {
    // TODO: Task 3.
    throw new UnsupportedOperationException();
  }

  public List<T> recursiveDepthFirstSearch() {
    // TODO: Task 3.
    throw new UnsupportedOperationException();
  }

  // helper
  public Set<T> SetOfAllDestinationVertices() {
    Set<T> allDestinationVertices = new HashSet<T>();

    for (Edge<T> edge : edges) {
      allDestinationVertices.add(edge.getDestination());
    }
    return allDestinationVertices;
  }

  public Set<Edge<T>> EdgesWithSameSourceVertex(T vertex) {
    Set<Edge<T>> EdgesWithSameSourceVertex = new HashSet<Edge<T>>();

    for (Edge<T> edge : edges) {
      if (edge.getSource().equals(vertex)) EdgesWithSameSourceVertex.add(edge);
    }
    return EdgesWithSameSourceVertex;
  }
}
