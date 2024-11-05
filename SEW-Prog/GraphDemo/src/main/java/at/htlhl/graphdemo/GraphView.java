package at.htlhl.graphdemo;

import com.brunomnsilva.smartgraph.containers.ContentZoomScrollPane;
import com.brunomnsilva.smartgraph.graphview.SmartCircularSortedPlacementStrategy;
import com.brunomnsilva.smartgraph.graphview.SmartGraphPanel;
import com.brunomnsilva.smartgraph.graphview.SmartPlacementStrategy;
import com.brunomnsilva.smartgraph.graphview.SmartStylableNode;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToolBar;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;

public class GraphView extends BorderPane {
    private SmartGraphPanel<String, String> smartGraphPanel;
    private ContentZoomScrollPane contentZoomScrollPane;

    public GraphView(GraphControl graphControl) {
        SmartPlacementStrategy strategy = new SmartCircularSortedPlacementStrategy();
        smartGraphPanel = new SmartGraphPanel<>(graphControl.getGraph(), strategy);
        smartGraphPanel.setAutomaticLayout(true);
        contentZoomScrollPane = new ContentZoomScrollPane(smartGraphPanel);
        setCenter(contentZoomScrollPane);
        Button testButton = new Button("Change color");
        testButton.setOnAction(new TestEventHandler());
        ToolBar toolBar = new ToolBar(testButton);
        setTop(toolBar);
        addDoubleClickEventHandler();
        addRightClickEventHandler();
    }

    public void initAfterVisible() {
        smartGraphPanel.init();
    }

    private void addDoubleClickEventHandler() {
        smartGraphPanel.setVertexDoubleClickAction(vertex -> {
            startColorSwitch(vertex);
        });
    }

    private void addRightClickEventHandler() {
        smartGraphPanel.getChildren().forEach(vertex -> {
            vertex.setOnMouseClicked(event -> {
                if (event.getButton() == MouseButton.SECONDARY) {
                    ContextMenu contextMenu = new ContextMenu();
                    MenuItem selectItem = new MenuItem("Select");
                    MenuItem showDetailsItem = new MenuItem("Show Details");
                    selectItem.setOnAction(e -> startColorSwitch((SmartStylableNode) vertex));
                    showDetailsItem.setOnAction(e -> System.out.println("Vertex name: " + vertex));
                    contextMenu.getItems().addAll(selectItem, showDetailsItem);
                    contextMenu.show(vertex, event.getScreenX(), event.getScreenY());
                }
            });
        });
    }

    private void startColorSwitch(SmartStylableNode vertex) {
        vertex.setStyleClass("htlVertex");
        PauseTransition pause = new PauseTransition(Duration.seconds(1));
        pause.setOnFinished(event -> {
            vertex.setStyleClass("htlVertex2");
            PauseTransition repeatPause = new PauseTransition(Duration.seconds(1));
            repeatPause.setOnFinished(e -> startColorSwitch(vertex));
            repeatPause.play();
        });
        pause.play();
    }

    private class TestEventHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent actionEvent) {
            SmartStylableNode vertex = smartGraphPanel.getStylableVertex("C");
            if (vertex != null) {
                startColorSwitch(vertex);
            }
        }
    }
}