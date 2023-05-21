module com.example.d3sshoppos {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.poi.ooxml;
    requires java.desktop;
    requires itextpdf;



    opens com.example.d3sshoppos to javafx.fxml;
    exports com.example.d3sshoppos;
}