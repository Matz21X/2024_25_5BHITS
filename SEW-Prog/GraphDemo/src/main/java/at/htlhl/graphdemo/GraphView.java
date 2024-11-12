package at.htlhl.graphdemo;

import com.brunomnsilva.smartgraph.containers.ContentZoomScrollPane;
import com.brunomnsilva.smartgraph.graph.Vertex;
import com.brunomnsilva.smartgraph.graphview.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToolBar;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

import java.util.function.Consumer;

public class GraphView extends BorderPane {
    private SmartGraphPanel<String, String> smartGraphPanel;
    private ContentZoomScrollPane contentZoomScrollPane;
    private static final int DOUBLE_CLICK_DELAY = 200;
    private GraphControl graphControl;

    public GraphView(GraphControl graphControl) {
        this.graphControl = graphControl;
        SmartPlacementStrategy strategy = new SmartCircularSortedPlacementStrategy();
        smartGraphPanel = new SmartGraphPanel<>(graphControl.getGraph(), strategy);
        smartGraphPanel.setAutomaticLayout(true);
        contentZoomScrollPane = new ContentZoomScrollPane(smartGraphPanel);
        setCenter(contentZoomScrollPane);
        Button testButton = new Button("Test");
        //testButton.setOnAction(new TestEventHandler());
        ToolBar toolBar = new ToolBar(testButton);
        setTop(toolBar);
        smartGraphPanel.setVertexDoubleClickAction(new Consumer<SmartGraphVertex<String>>() {
            @Override
            public void accept(SmartGraphVertex<String> stringSmartGraphVertex) {
                stringSmartGraphVertex.setStyleClass("htlVertex");
            }
        });        //enable ContextMenu on Vertex        smartGraphPanel.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {            @Override            public void handle(ContextMenuEvent contextMenuEvent) {                System.out.println("ContextMenu requested" + contextMenuEvent);                SmartGraphVertexNode<String> foundVertex=findVertex(contextMenuEvent.getX(), contextMenuEvent.getY());                if (foundVertex != null) {                    System.out.println("Vertex clicked" + foundVertex.getUnderlyingVertex());                }            }        });        //eigene Lösung(besser)        //smartGraphPanel.setVertexDoubleClickAction(new DoubleClickAction());        //smartGraphPanel.setOnMouseClicked(new MouseEventHandler());    }    private SmartGraphVertexNode<String> findVertex (double x, double y) {           for(Vertex<String> v :graphControl.getGraph().vertices()){               SmartStylableNode smartStylableNode=smartGraphPanel.getStylableVertex(v);               if(smartStylableNode instanceof SmartStylableNode){                   SmartGraphVertexNode<String> smartGraphVertexNode=(SmartGraphVertexNode<String>) smartStylableNode;                   if(smartGraphVertexNode.getBoundsInParent().contains(x,y)){                       return smartGraphVertexNode;                   }               }           }        return null;    }    /**     * IMPORTANT!     * Should be called after scene is displayed so we can     * initialize the graph visualisation     */    public void initAfterVisible() {        smartGraphPanel.init();    }    //eigene Lösung(besser)/*    private class MouseEventHandler implements EventHandler<MouseEvent> {        @Override        public void handle(MouseEvent mouseEvent) {            if (mouseEvent.getButton() == MouseButton.PRIMARY) {                if (mouseEvent.getClickCount() == 2) {                    SmartGraphVertex<String> vertex = getVertexAtMousePosition(mouseEvent.getX(), mouseEvent.getY());                    if (vertex != null) {                        System.out.println("Double-clicked on vertex: " + vertex.getUnderlyingVertex().element());                        vertex.setStyleClass("htlVertex");                    }                } else if (mouseEvent.getClickCount() == 1) {                    SmartGraphVertex<String> vertex = getVertexAtMousePosition(mouseEvent.getX(), mouseEvent.getY());                    if (vertex != null) {                        System.out.println("Single clicked on vertex: " + vertex.getUnderlyingVertex().element());                        vertex.setStyleClass("myVertex"); // Change style or perform action                    }                }            } else if (mouseEvent.getButton() == MouseButton.SECONDARY) {                SmartGraphVertex<String> vertex = getVertexAtMousePosition(mouseEvent.getX(), mouseEvent.getY());                if (vertex != null) {                    showContextMenu(mouseEvent, vertex);                }            }        }    }    private SmartGraphVertex<String> getVertexAtMousePosition(double x, double y) {        for (Vertex ver : graphControl.getVertices()) {            SmartGraphVertex<String> vertex = (SmartGraphVertex<String>) smartGraphPanel.getStylableVertex(ver);            if (vertex != null && vertex.getPositionCenterX() - vertex.getRadius() < x &&                    vertex.getPositionCenterX() + vertex.getRadius() > x &&                    vertex.getPositionCenterY() - vertex.getRadius() < y &&                    vertex.getPositionCenterY() + vertex.getRadius() > y) {                return vertex;            }        }        return null;    }    private void showContextMenu(MouseEvent mouseEvent, SmartGraphVertex<String> vertex) {        ContextMenu contextMenu = new ContextMenu();        MenuItem option1 = new MenuItem("Select");        option1.setOnAction(e -> {            vertex.setStyleClass("htlVertex");        });        MenuItem option2 = new MenuItem("Show Details");        option2.setOnAction(e -> System.out.println("Das ist Knoten " + vertex.getUnderlyingVertex().element()));        contextMenu.getItems().addAll(option1, option2);        contextMenu.show(smartGraphPanel, mouseEvent.getScreenX(), mouseEvent.getScreenY());    }    private class TestEventHandler implements EventHandler<ActionEvent> {        boolean changed = false;        @Override        public void handle(ActionEvent actionEvent) {            changed = !changed;            if (changed) {                smartGraphPanel.getStylableVertex("C").setStyleClass("htlVertex");                return;            }            smartGraphPanel.getStylableVertex("C").setStyleClass("myVertex");        }    }    private class DoubleClickAction implements Consumer<SmartGraphVertex<String>> {        @Override        public void accept(SmartGraphVertex<String> vertex) {            if (vertex != null) {                System.out.println("Double-clicked on vertex: " + vertex.getUnderlyingVertex().element());                vertex.setStyleClass("htlVertex");            }        }    } */}