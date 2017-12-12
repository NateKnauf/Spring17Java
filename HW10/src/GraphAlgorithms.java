import java.util.Queue;
import java.util.Set;
import java.util.Map;
import java.util.List;
import java.util.LinkedList;
import java.util.HashSet;
import java.util.HashMap;
import java.util.PriorityQueue;

/**
 * Your implementations of various graph algorithms.
 *
 * @author Nate Knauf
 * @version 1.0
 */
public class GraphAlgorithms {

    /**
     * Perform breadth first search on the given graph, starting at the start
     * Vertex.  You will return a List of the vertices in the order that
     * you visited them.  Make sure to include the starting vertex at the
     * beginning of the list.
     *
     * When exploring a Vertex, make sure you explore in the order that the
     * adjacency list returns the neighbors to you.  Failure to do so may
     * cause you to lose points.
     *
     * You may import/use {@code java.util.Queue}, {@code java.util.Set},
     * {@code java.util.Map}, {@code java.util.List}, and any classes
     * that implement the aforementioned interfaces.
     *
     * @throws IllegalArgumentException if any input is null, or if
     *         {@code start} doesn't exist in the graph
     * @param start the Vertex you are starting at
     * @param graph the Graph you are traversing
     * @param <T> the data type representing the vertices in the graph.
     * @return a List of vertices in the order that you visited them
     */
    public static <T> List<Vertex<T>> breadthFirstSearch(Vertex<T> start,
            Graph<T> graph) {
        if (start == null) {
            throw new IllegalArgumentException(
                    "Cannot search from null node!");
        }
        if (graph == null) {
            throw new IllegalArgumentException(
                    "Cannot search in null graph!");
        }
        if (!graph.getAdjacencyList().keySet().contains(start)) {
            throw new IllegalArgumentException(
                    "Start node isn't contained in this graph!");
        }
        Queue<Vertex<T>> myQ = new LinkedList<Vertex<T>>();
        myQ.add(start);
        List<Vertex<T>> ans = new LinkedList<Vertex<T>>();
        Set<Vertex<T>> explored = new HashSet<Vertex<T>>();
        while (!myQ.isEmpty()) {
            Vertex<T> nextNode = myQ.poll();
            if (!explored.contains(nextNode)) {
                ans.add(nextNode);
                explored.add(nextNode);
                for (VertexDistancePair<T> neighborPair
                        : graph.getAdjacencyList().get(nextNode)) {
                    myQ.add(neighborPair.getVertex());
                }
            }
        }
        return ans;
    }

    /**
     * Perform depth first search on the given graph, starting at the start
     * Vertex.  You will return a List of the vertices in the order that
     * you visited them.  Make sure to include the starting vertex at the
     * beginning of the list.
     *
     * When exploring a Vertex, make sure you explore in the order that the
     * adjacency list returns the neighbors to you.  Failure to do so may
     * cause you to lose points.
     *
     * You MUST implement this method recursively.
     * Do not use any data structure as a stack to avoid recursion.
     * Implementing it any other way WILL cause you to lose points!
     *
     * You may import/use {@code java.util.Set}, {@code java.util.Map},
     * {@code java.util.List}, and any classes that implement the
     * aforementioned interfaces.
     *
     * @throws IllegalArgumentException if any input is null, or if
     *         {@code start} doesn't exist in the graph
     * @param start the Vertex you are starting at
     * @param graph the Graph you are traversing
     * @param <T> the data type representing the vertices in the graph.
     * @return a List of vertices in the order that you visited them
     */
    public static <T> List<Vertex<T>> depthFirstSearch(Vertex<T> start,
            Graph<T> graph) {
        if (start == null) {
            throw new IllegalArgumentException(
                    "Cannot search from null node!");
        }
        if (graph == null) {
            throw new IllegalArgumentException(
                    "Cannot search in null graph!");
        }
        if (!graph.getAdjacencyList().keySet().contains(start)) {
            throw new IllegalArgumentException(
                    "Start node isn't contained in this graph!");
        }
        List<Vertex<T>> explored = new LinkedList<Vertex<T>>();
        return dfsHelp(start, graph, explored);
    }

    /**
     * Recursive helper method for a depthFirstSearch
     *
     * @param start the Vertex you are starting at
     * @param graph the Graph you are traversing
     * @param explored the List of nodes that have already been visited
     * @param <T> the data type representing the vertices in the graph.
     * @return a List of vertices in the order that you visited them
     */
    public static <T> List<Vertex<T>> dfsHelp(Vertex<T> start,
            Graph<T> graph, List<Vertex<T>> explored) {
        if (!explored.contains(start)) {
            explored.add(start);
            for (VertexDistancePair<T> pair
                    : graph.getAdjacencyList().get(start)) {
                dfsHelp(pair.getVertex(), graph, explored);
            }
        }
        return explored;
    }

    /**
     * Find the shortest distance between the start vertex and all other
     * vertices given a weighted graph where the edges only have positive
     * weights.
     *
     * Return a map of the shortest distances such that the key of each entry is
     * a node in the graph and the value for the key is the shortest distance
     * to that node from start, or Integer.MAX_VALUE (representing infinity)
     * if no path exists. You may assume that going from a vertex to itself
     * has a distance of 0.
     *
     * There are guaranteed to be no negative edge weights in the graph.
     *
     * You may import/use {@code java.util.PriorityQueue},
     * {@code java.util.Map}, and any class that implements the aforementioned
     * interface.
     *
     * @throws IllegalArgumentException if any input is null, or if
     *         {@code start} doesn't exist in the graph
     * @param start the Vertex you are starting at
     * @param graph the Graph you are searching
     * @param <T> the data type representing the vertices in the graph.
     * @return a map of the shortest distances from start to every other node
     *         in the graph.
     */
    public static <T> Map<Vertex<T>, Integer> dijkstras(Vertex<T> start,
            Graph<T> graph) {
        if (start == null) {
            throw new IllegalArgumentException(
                    "Cannot search from null node!");
        }
        if (graph == null) {
            throw new IllegalArgumentException(
                    "Cannot search in null graph!");
        }
        if (!graph.getAdjacencyList().keySet().contains(start)) {
            throw new IllegalArgumentException(
                    "Start node isn't contained in this graph!");
        }
        Map<Vertex<T>, Integer> map = new HashMap<Vertex<T>, Integer>();
        for (Vertex<T> vert : graph.getAdjacencyList().keySet()) {
            map.put(vert, Integer.MAX_VALUE);
        }
        map.put(start, 0);

        PriorityQueue<VertexDistancePair<T>> pq =
                new PriorityQueue<VertexDistancePair<T>>();
        for (VertexDistancePair<T> pair : graph.getAdjacencyList().get(start)) {
            pq.add(pair);
        }
        pq.add(new VertexDistancePair<T>(start, 0));

        while (!pq.isEmpty()) {
            VertexDistancePair<T> min = pq.remove();

            for (VertexDistancePair<T> neighborPair
                    : graph.getAdjacencyList().get(min.getVertex())) {
                int alt = map.get(min.getVertex()) + neighborPair.getDistance();
                if (alt < map.get(neighborPair.getVertex())) {
                    map.put(neighborPair.getVertex(), alt);
                    pq.add(new VertexDistancePair<T>(neighborPair.getVertex(),
                            alt));
                }
            }
        }

        return map;
    }

    /**
     * Run Prim's algorithm on the given graph and return the minimum spanning
     * tree in the form of a set of Edges.  If the graph is disconnected, and
     * therefore there is no valid MST, return null.
     *
     * When exploring a Vertex, make sure you explore in the order that the
     * adjacency list returns the neighbors to you.  Failure to do so may
     * cause you to lose points.
     *
     * You may assume that for a given starting vertex, there will only be
     * one valid MST that can be formed. In addition, only an undirected graph
     * will be passed in.
     *
     * You may import/use {@code java.util.PriorityQueue},
     * {@code java.util.Set}, and any class that implements the aforementioned
     * interface.
     *
     * @throws IllegalArgumentException if any input is null, or if
     *         {@code start} doesn't exist in the graph
     * @param start the Vertex you are starting at
     * @param graph the Graph you are creating the MST for
     * @param <T> the data type representing the vertices in the graph.
     * @return the MST of the graph; null if no valid MST exists.
     */
    public static <T> Set<Edge<T>> prims(Vertex<T> start, Graph<T> graph) {
        if (start == null) {
            throw new IllegalArgumentException(
                    "Cannot search from null node!");
        }
        if (graph == null) {
            throw new IllegalArgumentException(
                    "Cannot search in null graph!");
        }
        if (!graph.getAdjacencyList().keySet().contains(start)) {
            throw new IllegalArgumentException(
                    "Start node isn't contained in this graph!");
        }

        if (graph.getAdjacencyList().keySet().size() < 2) {
            return null;
        }

        Set<Vertex<T>> explored = new HashSet<Vertex<T>>();
        explored.add(start);
        Set<Edge<T>> mst = new HashSet<Edge<T>>();

        PriorityQueue<Edge<T>> pq = new PriorityQueue<Edge<T>>();
        for (VertexDistancePair<T> pair : graph.getAdjacencyList().get(start)) {
            pq.add(new Edge<T>(start, pair.getVertex(),
                    pair.getDistance(), false));
        }

        while (!pq.isEmpty()
                && explored.size() < graph.getAdjacencyList().keySet().size()) {
            Edge<T> edge = pq.poll();
            if (edge != null && !mst.contains(edge)) {
                if (!explored.contains(edge.getV())) {
                    mst.add(edge);
                    explored.add(edge.getV());
                    for (VertexDistancePair<T> pair
                            : graph.getAdjacencyList().get(edge.getV())) {
                        pq.add(new Edge<T>(edge.getV(), pair.getVertex(),
                                pair.getDistance(), false));
                    }
                }
            }
        }
        if (explored.size() == graph.getAdjacencyList().keySet().size()) {
            return mst;
        }
        return null;
    }

}
