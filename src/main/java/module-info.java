module job {
    requires com.google.gson;
    requires com.google.common;
    requires javafx.controls;
    requires javafx.fxml;
    requires org.jetbrains.annotations;

    opens org.agnese_dissan.gui to javafx.fxml;
    opens org.agnese_dissan.models to com.google.gson;
    opens org.agnese_dissan.daos to com.google.gson;
    exports org.agnese_dissan;
    exports org.agnese_dissan.gui;
    exports org.agnese_dissan.gui.login;
    opens org.agnese_dissan.gui.login to javafx.fxml;

}