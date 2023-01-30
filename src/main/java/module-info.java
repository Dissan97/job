module job {
    requires com.google.gson;
    requires com.google.common;
    requires javafx.controls;
    requires javafx.fxml;
    requires org.jetbrains.annotations;
    requires java.sql;
    requires mariadb.java.client;

    opens org.agnese_dissan.gui to javafx.fxml;
    opens org.agnese_dissan.daos to com.google.gson;
    exports org.agnese_dissan;
    exports org.agnese_dissan.gui;
    exports org.agnese_dissan.gui.login;
    exports org.agnese_dissan.models.users;
    exports org.agnese_dissan.exceptions;
    exports org.agnese_dissan.gui.employer;
    exports org.agnese_dissan.gui.employee;

    opens org.agnese_dissan.gui.employee to javafx.fxml;
    opens org.agnese_dissan.gui.employer to javafx.fxml;
    opens org.agnese_dissan.gui.login to javafx.fxml;
    opens org.agnese_dissan.models.users to com.google.gson;
    opens org.agnese_dissan.models.job to com.google.gson;


}