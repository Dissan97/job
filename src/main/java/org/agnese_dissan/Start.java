package org.agnese_dissan;

import org.agnese_dissan.factories.UiFactory;
import org.agnese_dissan.interfaces.JobView;

public class Start {


    //TODO if found configuration file the start home menu
    public static void main(String [] args){

        boolean gui = true;
        JobView startView;

        if (args != null){
            if (args[0].equalsIgnoreCase("CLI")){
                gui = false;
            }
        }

        UiFactory.setGui(gui);
        startView = UiFactory.getUi(Macros.START.ordinal(), null);
        assert startView != null;
        startView.startUi();


        //TODO if not found configuration file the start login menu


    }


}
