package at.htlhl.graphdemo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        GraphControl graphControl = new GraphControl();
        GraphView graphView = new GraphView(graphControl);

        Scene scene = new Scene(graphView, 1080, 720 );
        stage.setTitle("Graph Demo");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}