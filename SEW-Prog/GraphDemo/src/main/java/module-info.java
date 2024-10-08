module at.htlhl.graphdemo {
    requires javafx.controls;
    requires javafx.fxml;
    requires smartgraph;


    opens at.htlhl.graphdemo to javafx.fxml;
    exports at.htlhl.graphdemo;
}