package org.agnese_dissan;

import org.agnese_dissan.cli.io.Input;
import org.agnese_dissan.cli.io.Output;
import org.agnese_dissan.factories.UiFactory;
import org.agnese_dissan.interfaces.JobView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Start {


    //TODO if found configuration file the start home menu
    public static void main(String [] args){

        boolean gui = true;
        JobView startView;

        try {
            if (args[0].equalsIgnoreCase("CLI")){
                gui = false;
            }
        } catch (ArrayIndexOutOfBoundsException ignored) {}

        UiFactory.setGui(gui);
        startView = UiFactory.getUi(Macros.START.ordinal(), null);
        assert startView != null;
        startView.startUi();
        //TODO if not found configuration file the start login menu


    }


}
