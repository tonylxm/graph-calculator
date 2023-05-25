// AUTHOR: Tony Lim
// DATE CREATED: 19/05/2023
// LAST EDITED: 24/05/2023

package nz.ac.auckland.se281.datastructures;

import java.util.Collections;
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
  private Set<T> verticies;
  private Set<Edge<T>> edges;

  public Graph(Set<T> verticies, Set<Edge<T>> edges) {
    this.verticies = verticies;
    this.edges = edges;
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
        rootVertices.add(Collections.min(equivalenceClass)); // is this allowed?
      }
    }
    return rootVertices;
  }

  public boolean isReflexive() {
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
      for (Edge<T> edge2 : EdgesWithSameSourceVertex(edge1.getDestination())) {
        for (Edge<T> edge3 : EdgesWithSameSourceVertex(edge1.getSource())) {
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
    // '==' rather than '.equals()' as we want the same reference of same object, not just same node
    // number

    for (Edge<T> edge1 : edges) {
      for (Edge<T> edge2 : EdgesWithSameSourceVertex(edge1.getDestination())) {
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
      for (Edge<T> edge : EdgesWithSameSourceVertex(vertex)) {
        equivalenceClass.add(edge.getDestination());
      }
      return equivalenceClass;
    }
  }

  public List<T> iterativeBreadthFirstSearch() {
    // BFS - queue
    // TODO: Task 2.
    throw new UnsupportedOperationException();
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

  // helper
  public Set<Edge<T>> EdgesWithSameSourceVertex(T vertex) {
    Set<Edge<T>> EdgesWithSameSourceVertice = new HashSet<Edge<T>>();

    for (Edge<T> edge : edges) {
      if (edge.getSource().equals(vertex)) EdgesWithSameSourceVertice.add(edge);
    }
    return EdgesWithSameSourceVertice;
  }
}
