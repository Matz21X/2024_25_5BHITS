module at.htlhl.graphdemo {
    requires javafx.controls;
    requires javafx.fxml;


    opens at.htlhl.graphdemo to javafx.fxml;
    exports at.htlhl.graphdemo;
}