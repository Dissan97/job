package org.dissan;

import org.dissan.factories.DAOFactory;
import org.dissan.factories.UiFactory;
import org.dissan.interfaces.JobView;
import org.dissan.models.users.User;

public class Start {


    //TODO if found configuration file the start home menu
    //TODO add a state machine to handle GUI
    public static void main(String [] args) {

        boolean gui = true;
        boolean local = true;
        JobView startView;

        try {
            if (args[0].equalsIgnoreCase("CLI")){
                gui = false;
            }
            if (args[1].equalsIgnoreCase("DB")){
                local = false;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            gui = true;
            UiFactory.setGui(true);
        }

        UiFactory.setGui(gui);
        DAOFactory.setStorageMethod(local);

        UserConfigJson configurationJson = new UserConfigJson();

        Macros starter = Macros.START;
        User user = null;

        if (configurationJson.hasConfig()){
            starter = configurationJson.getUserType();
            user = configurationJson.getUser();
        }

        startView = UiFactory.getUi(starter, user);
        assert startView != null;
        startView.startUi();
        //TODO if not found configuration file the start login menu


    }


}
