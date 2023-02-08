package org.disagn;

import org.disagn.factories.DAOFactory;
import org.disagn.factories.UiFactory;
import org.disagn.interfaces.JobView;
import org.disagn.models.users.User;

public class Start{

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

        if (configurationJson.hasConfig() && !gui){
            starter = configurationJson.getUserType();
            user = configurationJson.getUser();
        }

        startView = UiFactory.getUi(starter, user);
        assert startView != null;
        startView.startUi();

    }

}
