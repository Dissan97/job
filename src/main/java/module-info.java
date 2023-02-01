module job {
    requires com.google.gson;
    requires com.google.common;
    requires javafx.controls;
    requires javafx.fxml;
    requires org.jetbrains.annotations;
    requires java.sql;
    requires mariadb.java.client;

    opens org.AgneseDissan.gui to javafx.fxml;
    opens org.AgneseDissan.daos to com.google.gson;
    exports org.AgneseDissan;
    exports org.AgneseDissan.gui;
    exports org.AgneseDissan.gui.login;
    exports org.AgneseDissan.models.users;
    exports org.AgneseDissan.exceptions;
    exports org.AgneseDissan.gui.employer;
    exports org.AgneseDissan.models.job;
    exports org.AgneseDissan.gui.employee;

    opens org.AgneseDissan.gui.employee to javafx.fxml;
    opens org.AgneseDissan.gui.employer to javafx.fxml;
    opens org.AgneseDissan.gui.login to javafx.fxml;
    opens org.AgneseDissan.models.users to com.google.gson;
    opens org.AgneseDissan.models.job to com.google.gson;


}