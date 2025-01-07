package at.htlhl.graphdemo;

import com.brunomnsilva.smartgraph.containers.ContentZoomScrollPane;
import com.brunomnsilva.smartgraph.graph.Edge;
import com.brunomnsilva.smartgraph.graph.Vertex;
import com.brunomnsilva.smartgraph.graphview.*;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import java.util.*;


public class GraphView extends BorderPane {

    private final SmartGraphPanel<VertexData, EdgeData> smartGraphPanel;
    private final GraphControl graphControl;
    private final ContextMenu contextMenu;
    private Vertex<VertexData> startVertex = null;
    private Vertex<VertexData> endVertex = null;

    public GraphView(GraphControl graphControl) {
        this.graphControl = graphControl;

        SmartPlacementStrategy strategy = new SmartCircularSortedPlacementStrategy();
        smartGraphPanel = new SmartGraphPanel<>(graphControl.getGraph(), strategy);
        smartGraphPanel.setAutomaticLayout(true);

        setCenter(new ContentZoomScrollPane(smartGraphPanel));

        ToolBar toolBar = getToolBar(graphControl);
        setTop(toolBar);


        contextMenu = buildContextMenu();

        smartGraphPanel.setOnContextMenuRequested(contextMenuEvent -> {
            SmartGraphVertexNode<VertexData> foundVertex = findVertexAt(contextMenuEvent.getX(), contextMenuEvent.getY());

            if (foundVertex != null) {
                contextMenu.show(foundVertex, contextMenuEvent.getScreenX(), contextMenuEvent.getScreenY());
            }
        });
    }


    public void initAfterVisible() {
        smartGraphPanel.init();
    }


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


            resetEdges();

            highlightPath(path, graphControl.getGraph().edges());


            buildPathDescription(path);
        });


        Button restartButton = new Button("Neustart");
        restartButton.setOnAction(e -> {
            resetVertices();
            resetEdges();
            startVertex = null;
            endVertex = null;
        });


        return new ToolBar(dijkstraButton, restartButton);
    }


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


    private ContextMenu buildContextMenu() {
        ContextMenu contextMenu = new ContextMenu();

        MenuItem setAsStart = new MenuItem("Startknoten");
        setAsStart.setOnAction(e -> {
            setVertexStyle("start");
            startVertex = ((SmartGraphVertexNode<VertexData>) contextMenu.getOwnerNode()).getUnderlyingVertex();
            resetEdges();
        });

        MenuItem setAsEnd = new MenuItem("Endknoten");
        setAsEnd.setOnAction(e -> {
            setVertexStyle("end");
            endVertex = ((SmartGraphVertexNode<VertexData>) contextMenu.getOwnerNode()).getUnderlyingVertex();
            resetEdges();
        });

        contextMenu.getItems().addAll(setAsStart, setAsEnd);

        return contextMenu;
    }


    private void setVertexStyle(String style) {
        Vertex<?> targetVertex = ((SmartGraphVertexNode<?>) contextMenu.getOwnerNode()).getUnderlyingVertex();
        updateStyle(graphControl.getGraph().vertices(), "vertex", style, targetVertex);
    }


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
                Vertex<V> v = null;


                if (edge.vertices()[0].equals(u)) {
                    v = edge.vertices()[1];
                } else if (edge.vertices()[1].equals(u)) {
                    v = edge.vertices()[0];
                }

                if (v != null) {
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


    private LinkedList<Vertex<VertexData>> reconstructPath(
            Map<Vertex<VertexData>, Vertex<VertexData>> prev,
            Vertex<VertexData> end) {

        LinkedList<Vertex<VertexData>> path = new LinkedList<>();
        for (Vertex<VertexData> at = end; at != null; at = prev.get(at)) {
            path.addFirst(at);
        }
        return path;
    }


    private boolean isValidDijkstra() {
        boolean isInvalid = startVertex == null || endVertex == null;
        if (isInvalid) {
            showAlert("Warnung", "Start und Endknoten müssen ausgewählt sein", Alert.AlertType.ERROR);
        }
        return !isInvalid;
    }

    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }


    private void resetVertices() {
        updateStyle(graphControl.getGraph().vertices(), "vertex", null, null);
    }

    private void resetEdges() {
        updateStyle(graphControl.getGraph().edges(), "edge", null, null);
    }


    private void highlightPath(List<Vertex<VertexData>> path, Collection<Edge<EdgeData, VertexData>> edges) {
        for (int i = 0; i < path.size() - 1; i++) {
            Vertex<VertexData> from = path.get(i);
            Vertex<VertexData> to = path.get(i + 1);

            edges.stream()
                    .filter(edge -> (edge.vertices()[0].equals(from) && edge.vertices()[1].equals(to)) ||
                            (edge.vertices()[1].equals(from) && edge.vertices()[0].equals(to)))
                    .map(smartGraphPanel::getStylableEdge)
                    .filter(Objects::nonNull)
                    .forEach(edgeNode -> edgeNode.setStyleClass("myEdge"));
        }
    }


    private boolean isPathEmpty(LinkedList<Vertex<VertexData>> path) {
        boolean isEmpty = path.isEmpty();
        if (isEmpty) {
            showAlert("Ergebnis", "Kein Pfad gefunden von Node " + startVertex.element().getName() + " zu " + endVertex.element().getName(), Alert.AlertType.INFORMATION);
        }
        return isEmpty;
    }


    private Map<Vertex<VertexData>, Vertex<VertexData>> computeShortestPaths() {
        return dijkstraGeneric(
                graphControl.getGraph().vertices(),
                graphControl.getGraph().edges(),
                startVertex
        );
    }


    private void buildPathDescription(LinkedList<Vertex<VertexData>> path) {
        StringBuilder pathDescription = new StringBuilder();
        int totalDistance = 0;

        for (int i = 0; i < path.size() - 1; i++) {
            Vertex<VertexData> from = path.get(i);
            Vertex<VertexData> to = path.get(i + 1);


            for (Edge<EdgeData, VertexData> edge : graphControl.getGraph().edges()) {
                if (edge.vertices()[0].equals(from) && edge.vertices()[1].equals(to)) {
                    totalDistance += edge.element().getDistance();
                    break;
                }
                if (edge.vertices()[1].equals(from) && edge.vertices()[0].equals(to)) {
                    totalDistance += edge.element().getDistance();
                    break;
                }
            }
            pathDescription.append(from.element().getName()).append(" -> ");
        }


        pathDescription.append(path.getLast().element().getName());


        showAlert("Ergebnis:", "Kürzester Pfad:\n" + pathDescription + "\nGesamtstrecke: " + totalDistance, Alert.AlertType.INFORMATION);
    }


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
                if (element.equals(startVertex)) {
                    stylableNode.setStyleClass("start");
                } else if (element.equals(endVertex)) {
                    stylableNode.setStyleClass("end");
                } else if (element.equals(specificElement)) {
                    stylableNode.setStyleClass(specificStyle);
                } else {
                    stylableNode.setStyleClass(defaultStyle);
                }
            }
        }
    }


}
