package at.htlhl.graphdemo;

import com.brunomnsilva.smartgraph.containers.ContentZoomScrollPane;
import com.brunomnsilva.smartgraph.graphview.SmartCircularSortedPlacementStrategy;
import com.brunomnsilva.smartgraph.graphview.SmartGraphPanel;
import com.brunomnsilva.smartgraph.graphview.SmartPlacementStrategy;
import com.brunomnsilva.smartgraph.graphview.SmartStylableNode;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;

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