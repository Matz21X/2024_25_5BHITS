package at.htlhl.graphdemo;

import com.brunomnsilva.smartgraph.containers.ContentZoomScrollPane;
import com.brunomnsilva.smartgraph.graphview.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.layout.BorderPane;

import java.util.function.Consumer;

public class GraphView extends BorderPane {
    private SmartGraphPanel<String, String> smartGraphPanel;
    private ContentZoomScrollPane contentZoomScrollPane;
    private GraphControl graphControl;

    public GraphView(GraphControl graphControl) {
        this.graphControl = graphControl;
        SmartPlacementStrategy strategy = new SmartCircularSortedPlacementStrategy();
        smartGraphPanel = new SmartGraphPanel<>(graphControl.getGraph(), strategy);
        smartGraphPanel.setAutomaticLayout(true);
        contentZoomScrollPane = new ContentZoomScrollPane(smartGraphPanel);
        setCenter(contentZoomScrollPane);
        Button testButton = new Button("Change color");
        testButton.setOnAction(new TestEventHandler());
        ToolBar toolBar = new ToolBar(testButton);
        setTop(toolBar);

        smartGraphPanel.setVertexDoubleClickAction(new Consumer<SmartGraphVertex<String>>() {
            @Override
            public void accept(SmartGraphVertex<String> stringSmartGraphVertex) {
                stringSmartGraphVertex.setStyleClass("htlVertex");
            }
        });

        // Enable contextmenu on vertex
        smartGraphPanel.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
            @Override
            public void handle(ContextMenuEvent contextMenuEvent) {
                System.out.println("ContextMenu Requested" + contextMenuEvent);
            }
        });


        private SmartGraphVertexNode<String> findVertexAt(double x, double y){

        }

    }

    public void initAfterVisible() {
        smartGraphPanel.init();
    }



    private class TestEventHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent actionEvent) {
            SmartStylableNode vertex = smartGraphPanel.getStylableVertex("C");
        }
    }
}