module job {
    requires com.google.gson;
    requires com.google.common;
    requires javafx.controls;
    requires javafx.fxml;
    requires org.jetbrains.annotations;
    requires java.sql;
    requires mariadb.java.client;

    opens org.dissan.gui to javafx.fxml;
    opens org.dissan.daos to com.google.gson;
    exports org.dissan;
    exports org.dissan.gui;
    exports org.dissan.gui.login;
    exports org.dissan.models.users;
    exports org.dissan.exceptions;
    exports org.dissan.gui.employer;
    exports org.dissan.models.job;
    exports org.dissan.gui.employee;

    opens org.dissan.gui.employee to javafx.fxml;
    opens org.dissan.gui.employer to javafx.fxml;
    opens org.dissan.gui.login to javafx.fxml;
    opens org.dissan.models.users to com.google.gson;
    opens org.dissan.models.job to com.google.gson;


}