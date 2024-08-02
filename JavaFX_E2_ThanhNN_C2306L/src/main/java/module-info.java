module com.javafx_e2_thanhnn_c2306l {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;

    requires java.sql;
    requires mysql.connector.java;

    opens com.javafx_e2_thanhnn_c2306l to javafx.fxml;
    exports com.javafx_e2_thanhnn_c2306l;

    opens com.javafx_e2_thanhnn_c2306l.Controller to javafx.fxml;
    exports com.javafx_e2_thanhnn_c2306l.Controller;

    opens com.javafx_e2_thanhnn_c2306l.Entity to javafx.fxml;
    exports com.javafx_e2_thanhnn_c2306l.Entity;

    opens com.javafx_e2_thanhnn_c2306l.Model to javafx.fxml;
    exports com.javafx_e2_thanhnn_c2306l.Model;
}