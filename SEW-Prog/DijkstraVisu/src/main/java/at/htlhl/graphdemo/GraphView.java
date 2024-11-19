package at.htlhl.graphdemo;

import com.brunomnsilva.smartgraph.containers.ContentZoomScrollPane;
import com.brunomnsilva.smartgraph.graph.Vertex;
import com.brunomnsilva.smartgraph.graphview.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.layout.BorderPane;

import java.util.Vector;
import java.util.function.Consumer;

public class GraphView extends BorderPane {
    private SmartGraphPanel<VertexData, EdgeData> smartGraphPanel;
    private ContentZoomScrollPane contentZoomScrollPane;
    GraphControl graphControl;

    public GraphView(GraphControl graphControl) {
        this.graphControl = graphControl;
        SmartPlacementStrategy strategy = new SmartCircularSortedPlacementStrategy();
        smartGraphPanel = new SmartGraphPanel<>(graphControl.getGraph(), strategy);
        smartGraphPanel.setAutomaticLayout(true);
        contentZoomScrollPane = new ContentZoomScrollPane(smartGraphPanel);
        setCenter(contentZoomScrollPane);
        Button testButton = new Button("Test");
        testButton.setOnAction(new TestEventHandler());
        ToolBar toolBar = new ToolBar(testButton);
        setTop(toolBar);
        // Enable double click on vertex
        // smartGraphPanel.setVertexDoubleClickAction(new Consumer<SmartGraphVertex<VertexData>>() {            @Override            public void accept(SmartGraphVertex<VertexData> stringSmartGraphVertex) {                stringSmartGraphVertex.setStyleClass("htlVertex");            }        }        );        ContextMenu contextMenu = buildContextMenu();        smartGraphPanel.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {            @Override            public void handle(ContextMenuEvent contextMenuEvent) {                System.out.println("ContextMenu requested" + contextMenuEvent);                SmartGraphVertexNode<VertexData> foundVertex =                        findVertexAt(contextMenuEvent.getX(), contextMenuEvent.getY());                if (foundVertex != null) {                    System.out.println("Vertex clicked: " + foundVertex.getUnderlyingVertex());                    contextMenu.show(foundVertex, contextMenuEvent.getScreenX(), contextMenuEvent.getScreenY());                }            }        });    }    private SmartGraphVertexNode<VertexData> findVertexAt(double x, double y) {        for (Vertex<VertexData> v : graphControl.getGraph().vertices()) {            SmartStylableNode smartStylableNode = smartGraphPanel.getStylableVertex(v);            if (smartStylableNode instanceof SmartGraphVertexNode) {                SmartGraphVertexNode<VertexData> smartGraphVertexNode = (SmartGraphVertexNode<VertexData>) smartStylableNode;                if (smartGraphVertexNode.getBoundsInParent().contains(x, y)) {                    return smartGraphVertexNode;                }            }        }        return null;    }    /**     * IMPORTANT!     * Should be called after scene is displayed, so we can     * initialize the graph visualization     */    public void initAfterVisible() {        smartGraphPanel.init();    }    private ContextMenu buildContextMenu() {        final ContextMenu contextMenu = new ContextMenu();        MenuItem showDetailsItem = new MenuItem("Show Details");        showDetailsItem.setOnAction(new EventHandler<ActionEvent>() {            @Override            public void handle(ActionEvent actionEvent) {                SmartGraphVertexNode<VertexData> smartGraphVertexNode =                        (SmartGraphVertexNode<VertexData>) contextMenu.getOwnerNode();                Vertex<VertexData> vertex = smartGraphVertexNode.getUnderlyingVertex();                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);                alert.initOwner(smartGraphPanel.getScene().getWindow());                alert.setHeaderText("Vertex " + vertex.element());                alert.setContentText("Detailed information to vertex " + vertex.element());                alert.show();            }        });        contextMenu.getItems().add(showDetailsItem);        return contextMenu;    }        private class TestEventHandler implements EventHandler<ActionEvent> {            @Override            public void handle(ActionEvent actionEvent) {                System.out.println(actionEvent.getSource());            }        }    }