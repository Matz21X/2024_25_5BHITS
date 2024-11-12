package at.htlhl.graphdemo;

import com.brunomnsilva.smartgraph.containers.ContentZoomScrollPane;
import com.brunomnsilva.smartgraph.graph.Vertex;
import com.brunomnsilva.smartgraph.graphview.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.layout.BorderPane;

import java.util.function.Consumer;

public class GraphView extends BorderPane {

    private SmartGraphPanel<String, String> smartGraphPanel;
    private ContentZoomScrollPane contentZoomScrollPane;
    private GraphControl graphControl;

    public GraphView(GraphControl graphControl) {
        this.graphControl = graphControl;

        SmartPlacementStrategy strategy =
                new SmartCircularSortedPlacementStrategy();
        smartGraphPanel = new SmartGraphPanel<>(
                graphControl.getGraph(), strategy);
        smartGraphPanel.setAutomaticLayout(true);

        contentZoomScrollPane =
                new ContentZoomScrollPane(smartGraphPanel);

        setCenter(contentZoomScrollPane);

        //Create Test-Button (1st button)
        Button testButton = new Button("Test");
        testButton.setOnAction(new TestEventHandler());

        //Create Reset-Button (2nd button)
        Button resetButton = new Button("Reset Style");
        resetButton.setOnAction(new ResetStyleEventHandler());

        // Create ToolBar and add both buttons
        ToolBar toolBar = new ToolBar(testButton, resetButton);
        setTop(toolBar);

        // Doppel Klick Funktion
        smartGraphPanel.setVertexDoubleClickAction(
                new Consumer<SmartGraphVertex<String>>() {
                    @Override
                    public void accept(SmartGraphVertex<String> stringSmartGraphVertex) {
                        stringSmartGraphVertex.setStyleClass("htlVertex");
                    }
                });

        // Enable context menu on vertex
        ContextMenu contextMenu = buildContextMenu();


        smartGraphPanel.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
            @Override
            public void handle(ContextMenuEvent contextMenuEvent) {
                System.out.println("ContextMenuEvent requested " + contextMenuEvent);

                SmartGraphVertexNode<String> foundVertex =
                        findVertexAt(contextMenuEvent.getX(),
                                contextMenuEvent.getY());

                if (foundVertex != null) {
                    contextMenu.show(
                            foundVertex,
                            contextMenuEvent.getScreenX(),
                            contextMenuEvent.getScreenY()
                    );
                }
            }
        });
    }

    private SmartGraphVertexNode<String> findVertexAt(double x, double y) {
        for (Vertex<String> v : graphControl.getGraph().vertices()) {
            SmartStylableNode smartStylableNode =
                    smartGraphPanel.getStylableVertex(v);
            if (smartStylableNode instanceof SmartGraphVertexNode) {
                SmartGraphVertexNode<String> smartGraphVertexNode =
                        (SmartGraphVertexNode<String>) smartStylableNode;
                if (smartGraphVertexNode.getBoundsInParent().contains(x, y)) {
                    return smartGraphVertexNode;
                }
            }
        }
        return null;
    }

    /**
     * IMPORTANT!
     * Should be called after scene is displayed, so we can
     * initialize the graph visualisation
     */
    public void initAfterVisible() {
        smartGraphPanel.init();
    }

    private ContextMenu buildContextMenu() {
        final ContextMenu contextMenu = new ContextMenu();

        MenuItem showDetailsItem = new MenuItem("Show Details");
        showDetailsItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                SmartGraphVertexNode<String> smartGraphVertexNode =
                        (SmartGraphVertexNode<String>) contextMenu.getOwnerNode();
                Vertex<String> vertex = smartGraphVertexNode.getUnderlyingVertex();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.initOwner(smartGraphPanel.getScene().getWindow());
                alert.setHeaderText("Vertex " + vertex.element());
                alert.setContentText("Detailed information to vertex " +
                        vertex.element());
                alert.show();
            }
        });
        contextMenu.getItems().add(showDetailsItem);

        return contextMenu;
    }

    private class TestEventHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent actionEvent) {
            smartGraphPanel.getStylableVertex("C")
                    .setStyleClass("htlVertex");  // Change style to "htlVertex"
        }
    }

    private class ResetStyleEventHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent actionEvent) {
            smartGraphPanel.getStylableVertex("C")
                    .setStyleClass("vertex");
        }
    }
}
