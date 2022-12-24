package org.agnese_dissan;


import org.agnese_dissan.cli.LoginView;
import org.agnese_dissan.cli.io.Output;
import org.agnese_dissan.models.DateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.zip.DataFormatException;


public class Start {


    //TODO if found configuration file the start home menu
    public static void main(String [] args){


        List<String> str = new ArrayList<>();

        str.add("dissan");
        str.add("ahmed");

        Output.printRow("MAIN", str, null);
        System.exit(0);


        /*
        *        return Hashing.sha256()
                .hashString(password, StandardCharsets.UTF_8)
                .toString();*/

        LoginView loginView = new LoginView();
        loginView.startUi();

        //TODO if not found configuration file the start login menu


    }


}
