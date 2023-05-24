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
    Set<T> rootVertices = verticies;
    Set<T> equivalenceClassSet = new HashSet<>();

    // Check if there are no incoming edges to the node
    // i.e. the node is a not a destination of any edge, then it is a root vertice
    // UNLESS it is a isolated node with a self-loop
    for (Edge<T> edge : edges) {
      if (rootVertices.contains(edge.getDestination())) {
        rootVertices.remove(edge.getDestination()); // remove the non-root vertice from the set
      }
    }

    // If the vertice is part of an equivalence class, it is a root vertice. If there are
    // multiple vertices in one equivalence class, return the minimum value
    for (T vertice : verticies) {
      equivalenceClassSet = getEquivalenceClass(vertice);
      if (!equivalenceClassSet.isEmpty()) {
        rootVertices.add(Collections.min(equivalenceClassSet)); // is this allowed?
      }
    }
    return rootVertices;
  }

  public boolean isReflexive() {
    Set<T> setOfVertices = new HashSet<T>();

    for (Edge<T> edge : edges) {
      if (edge.getSource() == edge.getDestination()) {
        setOfVertices.add(edge.getDestination());
      }
    }

    // if all vertices have a reflexive relation, the whole graph is reflexive
    if (setOfVertices.containsAll(verticies)) {
      return true;
    }
    return false;
  }

  public boolean isSymmetric() {
    // TODO: Task 1.
    throw new UnsupportedOperationException();
  }

  public boolean isTransitive() {
    // TODO: Task 1.
    throw new UnsupportedOperationException();
  }

  public boolean isAntiSymmetric() {
    // TODO: Task 1.
    throw new UnsupportedOperationException();
  }

  public boolean isEquivalence() {
    // TODO: Task 1.
    throw new UnsupportedOperationException();
  }

  public Set<T> getEquivalenceClass(T vertex) {
    // TODO: Task 1.
    throw new UnsupportedOperationException();
  }

  public List<T> iterativeBreadthFirstSearch() {
    // TODO: Task 2.
    throw new UnsupportedOperationException();
  }

  public List<T> iterativeDepthFirstSearch() {
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
}
