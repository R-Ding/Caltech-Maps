package edu.caltech.cs2.datastructures;

import edu.caltech.cs2.interfaces.*;

public class Graph<V, E> extends IGraph<V, E> {
    private ChainingHashDictionary<V, ChainingHashDictionary<V, E>> graph;

    public Graph() {
        this.graph = new ChainingHashDictionary<V, ChainingHashDictionary<V, E>>(MoveToFrontDictionary::new);
    }

    /**
     * Add a vertex to the graph.
     * @param vertex The vertex to add
     * @return true if vertex was not present already.
     */
    @Override
    public boolean addVertex(V vertex) {
        boolean result = !this.graph.containsKey(vertex);
        this.graph.put(vertex, new ChainingHashDictionary<V, E>(MoveToFrontDictionary::new));
        return result;
    }

    /**
     * Adds edge e to the graph.
     *
     * @param e The edge to add.
     * @throws IllegalArgumentException
     *             If e is not a valid edge (eg. refers to vertices not in the graph).
     * @return true If e was not already present; false if it was (in which case the graph is still updated).
     */
    @Override
    public boolean addEdge(V src, V dest, E e) {
        if (!this.graph.containsKey(src) || !this.graph.containsKey(dest)) {
            throw new IllegalArgumentException("not a valid edge");
        }
        boolean output = this.graph.get(src).containsKey(dest);
        this.graph.get(src).put(dest, e);
        return !output;
    }

    @Override
    public boolean addUndirectedEdge(V n1, V n2, E e) {
        boolean dir1 = addEdge(n1, n2, e);
        boolean dir2 = addEdge(n2, n1, e);
        return (dir1 && dir2);
    }

    @Override
    public boolean removeEdge(V src, V dest) {
        if (!this.graph.containsKey(src) || !this.graph.containsKey(dest)) {
            throw new IllegalArgumentException("not a valid edge");
        }
        boolean output = this.graph.get(src).containsKey(dest);
        this.graph.get(src).remove(dest);
        return output;
    }

    /**
     * Returns the set of vertices in the graph.
     * @return The set of all vertices in the graph.
     */
    @Override
    public ISet<V> vertices() {
        return this.graph.keySet();
    }

    /**
     * Tests if vertices i and j are adjacent, returning the edge between
     * them if so.
     *
     * @throws IllegalArgumentException if i or j are not vertices in the graph.
     * @return The edge from i to j if it exists in the graph;
     * 		   null otherwise.
     */
    @Override
    public E adjacent(V i, V j) {
        if (!this.graph.containsKey(i) || !this.graph.containsKey(j)) {
            throw new IllegalArgumentException("not a valid edge");
        }
        if (this.graph.get(i).containsKey(j)) {
            return this.graph.get(i).get(j);
        }
        return null;
    }

    /**
     * Return the neighbours of a given vertex when this graph is treated as
     * DIRECTED; that is, vertices to which vertex has an outgoing edge.
     *
     * @param vertex The vertex the neighbours of which to return.
     * @throws IllegalArgumentException if vertex is not in the graph.
     * @return The set of neighbors of vertex.
     */
    @Override
    public ISet<V> neighbors(V vertex) {
        return this.graph.get(vertex).keySet();
    }
}