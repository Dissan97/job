module job {
    requires com.google.gson;
    requires com.google.common;
    requires javafx.controls;
    requires javafx.fxml;
    requires org.jetbrains.annotations;
    requires java.sql;
    requires mariadb.java.client;
    requires org.slf4j;

    opens org.disagn.gui to javafx.fxml;
    opens org.disagn.daos to com.google.gson;
    exports org.disagn;
    exports org.disagn.gui;
    exports org.disagn.gui.login;
    exports org.disagn.models.users;
    exports org.disagn.exceptions;
    exports org.disagn.gui.employer;
    exports org.disagn.models.job;
    exports org.disagn.gui.employee;

    opens org.disagn.gui.employee to javafx.fxml;
    opens org.disagn.gui.employer to javafx.fxml;
    opens org.disagn.gui.login to javafx.fxml;
    opens org.disagn.models.users to com.google.gson;
    opens org.disagn.models.job to com.google.gson;


}