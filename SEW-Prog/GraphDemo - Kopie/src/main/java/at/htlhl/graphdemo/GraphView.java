package at.htlhl.graphdemo;

import com.brunomnsilva.smartgraph.containers.ContentZoomScrollPane;
import com.brunomnsilva.smartgraph.graph.Edge;
import com.brunomnsilva.smartgraph.graph.Vertex;
import com.brunomnsilva.smartgraph.graphview.*;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import java.util.*;

/**
 * A visual representation of a graph, utilizing the SmartGraph library to provide an interactive
 * user interface for displaying and manipulating graph data.
 * <br>
 * This class extends BorderPane to include:
 * <br>
 * - A graphical representation of the graph.
 * <br>
 * - A toolbar with buttons for Dijkstra's algorithm and restarting.
 * <br>
 * - Context menus for setting start and end vertices.
 * <br>
 * The class supports Dijkstra's algorithm to compute the shortest path between two vertices,
 * and visually highlights the result.
 */
public class GraphView extends BorderPane {

    private final SmartGraphPanel<VertexData, EdgeData> smartGraphPanel;
    private final GraphControl graphControl;
    private final ContextMenu contextMenu;
    private Vertex<VertexData> startVertex = null;
    private Vertex<VertexData> endVertex = null;
    private final String defaultVertexStyle = "vertex";
    private final String defaultEdgeStyle = "edge";
    private final String startVertexStyle = "startVertex";
    private final String endVertexStyle = "endVertex";
    private final String highlightEdgeStyle = "highlightEdge";

    /**
     * Constructs a GraphView for the given {@link GraphControl}.
     *
     * @param graphControl the graph control containing the graph data to be visualized
     */
    public GraphView(GraphControl graphControl) {
        this.graphControl = graphControl;

        SmartPlacementStrategy strategy = new SmartCircularSortedPlacementStrategy();
        smartGraphPanel = new SmartGraphPanel<>(graphControl.getGraph(), strategy);
        smartGraphPanel.setAutomaticLayout(true);

        setCenter(new ContentZoomScrollPane(smartGraphPanel));

        ToolBar toolBar = getToolBar(graphControl);
        setTop(toolBar);

        // Enable context menu on vertex
        contextMenu = buildContextMenu();

        smartGraphPanel.setOnContextMenuRequested(contextMenuEvent -> {
            SmartGraphVertexNode<VertexData> foundVertex = findVertexAt(contextMenuEvent.getX(), contextMenuEvent.getY());

            if (foundVertex != null) {
                contextMenu.show(foundVertex, contextMenuEvent.getScreenX(), contextMenuEvent.getScreenY());
            }
        });
    }

    /**
     * Initializes the graph visualization. Should be called after the GraphView is added
     * to a visible scene graph.
     */
    public void initAfterVisible() {
        smartGraphPanel.init();
    }

    /**
     * Creates a toolbar with buttons for running Dijkstra's algorithm and restarting the graph state.
     *
     * @param graphControl the graph control used to manipulate the graph
     * @return a {@link ToolBar} containing the buttons
     */
    private ToolBar getToolBar(GraphControl graphControl) {
        Button dijkstraButton = new Button("Dijkstra");
        dijkstraButton.setOnAction(e -> {
            if (!isValidDijkstra()) {
                return;
            }

            var path = reconstructPath(computeShortestPaths(), endVertex);

            if (isPathEmpty(path)) {
                return;
            }

            // Highlight the edges in the shortest path
            resetEdges(); // Optionally reset edges before applying new highlights

            highlightPath(path, graphControl.getGraph().edges());

            // Build path description
            buildPathDescription(path);
        });

        // Create Reset-Button (2nd button)
        Button restartButton = new Button("Restart");
        restartButton.setOnAction(e -> {
            resetVertices();
            resetEdges();
            startVertex = null;
            endVertex = null;
        });

        // Create ToolBar and add both buttons
        return new ToolBar(dijkstraButton, restartButton);
    }

    /**
     * Searches for a graph vertex located at the given screen coordinates.
     *
     * @param x the x-coordinate of the screen position
     * @param y the y-coordinate of the screen position
     * @return the {@link SmartGraphVertexNode} at the specified position, or null if none found
     */
    private SmartGraphVertexNode<VertexData> findVertexAt(double x, double y) {
        for (Vertex<VertexData> v : graphControl.getGraph().vertices()) {
            SmartStylableNode smartStylableNode = smartGraphPanel.getStylableVertex(v);
            if (smartStylableNode instanceof SmartGraphVertexNode) {
                @SuppressWarnings("unchecked")
                SmartGraphVertexNode<VertexData> smartGraphVertexNode = (SmartGraphVertexNode<VertexData>) smartStylableNode;
                if (smartGraphVertexNode.getBoundsInParent().contains(x, y)) {
                    return smartGraphVertexNode;
                }
            }
        }
        return null;
    }

    /**
     * Builds a context menu with options to set a vertex as the start or end vertex for Dijkstra's algorithm.
     *
     * @return the {@link ContextMenu} instance
     */
    private ContextMenu buildContextMenu() {
        ContextMenu contextMenu = new ContextMenu();

        MenuItem setAsStart = new MenuItem("Set As Start");
        setAsStart.setOnAction(e -> {
            setVertexStyle(startVertexStyle);
            startVertex = ((SmartGraphVertexNode<VertexData>) contextMenu.getOwnerNode()).getUnderlyingVertex();
            resetEdges();
        });

        MenuItem setAsEnd = new MenuItem("Set As End");
        setAsEnd.setOnAction(e -> {
            setVertexStyle(endVertexStyle);
            endVertex = ((SmartGraphVertexNode<VertexData>) contextMenu.getOwnerNode()).getUnderlyingVertex();
            resetEdges();
        });

        contextMenu.getItems().addAll(setAsStart, setAsEnd);

        return contextMenu;
    }

    /**
     * Applies a specific style to a vertex, resetting other vertices to the default style.
     *
     * @param style the style to apply to the target vertex
     */
    private void setVertexStyle(String style) {
        Vertex<?> targetVertex = ((SmartGraphVertexNode<?>) contextMenu.getOwnerNode()).getUnderlyingVertex();
        updateStyle(graphControl.getGraph().vertices(), defaultVertexStyle, style, targetVertex);
    }

    /**
     * Generic implementation of Dijkstra's algorithm for graphs.
     *
     * @param vertices the graph vertices
     * @param edges the graph edges
     * @param start the starting vertex
     * @param <V> the type of vertex data
     * @param <E> the type of edge data
     * @return a map of vertices to their predecessors on the shortest path
     */
    private <V, E extends EdgeData> Map<Vertex<V>, Vertex<V>> dijkstraGeneric(
            Collection<Vertex<V>> vertices,
            Collection<Edge<E, V>> edges,
            Vertex<V> start) {

        Map<Vertex<V>, Integer> dist = new HashMap<>();
        Map<Vertex<V>, Vertex<V>> prev = new HashMap<>();
        PriorityQueue<Vertex<V>> queue = new PriorityQueue<>(Comparator.comparing(dist::get));

        for (Vertex<V> vertex : vertices) {
            dist.put(vertex, Integer.MAX_VALUE);
            prev.put(vertex, null);
        }

        dist.put(start, 0);
        queue.add(start);

        while (!queue.isEmpty()) {
            Vertex<V> u = queue.poll();

            for (Edge<E, V> edge : edges) {
                if (edge.vertices()[0].equals(u)) {
                    Vertex<V> v = edge.vertices()[1];
                    int alt = dist.get(u) + edge.element().getDistance();
                    if (alt < dist.get(v)) {
                        dist.put(v, alt);
                        prev.put(v, u);
                        queue.add(v);
                    }
                }
            }
        }

        return prev;
    }


    /**
     * Reconstructs the shortest path from the predecessor map generated by Dijkstra's algorithm.
     *
     * @param prev the map of vertices to their predecessors
     * @param end the end vertex of the path
     * @return a list of vertices representing the shortest path
     */
    private LinkedList<Vertex<VertexData>> reconstructPath(
            Map<Vertex<VertexData>, Vertex<VertexData>> prev,
            Vertex<VertexData> end) {

        LinkedList<Vertex<VertexData>> path = new LinkedList<>();
        for (Vertex<VertexData> at = end; at != null; at = prev.get(at)) {
            path.addFirst(at); // LinkedList ist für diese Operation optimiert
        }
        return path;
    }


    /**
     * Validates that both start and end vertices are set for running Dijkstra's algorithm.
     *
     * @return true if both vertices are set; false otherwise
     */
    private boolean isValidDijkstra() {
        boolean isInvalid = startVertex == null || endVertex == null;
        if (isInvalid) {
            showAlert("Error", "Both start and end vertices must be set!", Alert.AlertType.ERROR);
        }
        return !isInvalid;
    }

    /**
     * Displays an alert with the specified title and content.
     *
     * @param title the title of the alert
     * @param content the content of the alert
     * @param type the type of the alert (e.g., ERROR, INFORMATION)
     */
    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * Resets all vertex styles to the default style.
     */
    private void resetVertices() {
        updateStyle(graphControl.getGraph().vertices(), defaultVertexStyle, null, null);
    }

    /**
     * Resets all edge styles to the default style.
     */
    private void resetEdges() {
        updateStyle(graphControl.getGraph().edges(), defaultEdgeStyle, null, null);
    }

    /**
     * Highlights the edges in the specified path on the graph.
     *
     * @param path the list of vertices representing the path
     * @param edges the collection of edges in the graph
     */
    private void highlightPath(List<Vertex<VertexData>> path, Collection<Edge<String, String>> edges) {
        path.stream().reduce((from, to) -> {
            edges.stream()
                    .filter(edge -> edge.vertices()[0].equals(from) && edge.vertices()[1].equals(to))
                    .map(smartGraphPanel::getStylableEdge)
                    .filter(Objects::nonNull)
                    .forEach(edgeNode -> edgeNode.setStyleClass(highlightEdgeStyle));
            return to;
        });
    }

    /**
     * Checks if the given path is empty and displays an alert if so.
     *
     * @param path the list of vertices representing the path
     * @return true if the path is empty; false otherwise
     */
    private boolean isPathEmpty(LinkedList<Vertex<VertexData>> path) {
        boolean isEmpty = path.isEmpty();
        if (isEmpty) {
            showAlert("Result", "No path found from " + startVertex.element().getName() + " to " + endVertex.element().getName(), Alert.AlertType.INFORMATION);
        }
        return isEmpty;
    }

    /**
     * Computes the shortest paths from the start vertex using Dijkstra's algorithm.
     *
     * @return a map of vertices to their predecessors on the shortest path
     */
    private Map<Vertex<VertexData>, Vertex<VertexData>> computeShortestPaths() {
        return dijkstraGeneric(
                graphControl.getGraph().vertices(),
                graphControl.getGraph().edges(),
                startVertex
        );
    }

    /**
     * Constructs and displays a description of the shortest path, including its total distance.
     *
     * @param path the list of vertices representing the shortest path
     */
    private void buildPathDescription(LinkedList<Vertex<VertexData>> path) {
        StringBuilder pathDescription = new StringBuilder();
        int totalDistance = 0;

        for (int i = 0; i < path.size() - 1; i++) {
            Vertex<VertexData> from = path.get(i);
            Vertex<VertexData> to = path.get(i + 1);

            // Find the edge connecting 'from' and 'to' and add its distance
            for (Edge<EdgeData, VertexData> edge : graphControl.getGraph().edges()) {
                if (edge.vertices()[0].equals(from) && edge.vertices()[1].equals(to)) {
                    totalDistance += edge.element().getDistance();
                    break;
                }
            }
            pathDescription.append(from.element().getName()).append(" -> ");
        }

        // Add the last vertex in the path
        pathDescription.append(path.getLast().element().getName());

        // Show path and total distance
        showAlert("Result", "Shortest path:\n" + pathDescription + "\nTotal distance: " + totalDistance, Alert.AlertType.INFORMATION);
    }

    /**
     * Updates the style of the specified graph elements, optionally applying a specific style
     * to a target element.
     *
     * @param elements the collection of elements (vertices or edges) to update
     * @param defaultStyle the default style to apply to all elements
     * @param specificStyle the specific style to apply to a target element, if applicable
     * @param specificElement the specific element to style, or null to skip
     */
    private void updateStyle(Collection<?> elements, String defaultStyle, String specificStyle, Object specificElement) {
        for (Object element : elements) {
            SmartStylableNode stylableNode;
            if (element instanceof Vertex) {
                stylableNode = smartGraphPanel.getStylableVertex((Vertex<VertexData>) element);
            } else if (element instanceof Edge) {
                stylableNode = smartGraphPanel.getStylableEdge((Edge<EdgeData, VertexData>) element);
            } else {
                continue;
            }

            if (stylableNode != null) {
                stylableNode.setStyleClass(element.equals(specificElement) ? specificStyle : defaultStyle);
            }
        }
    }

}
