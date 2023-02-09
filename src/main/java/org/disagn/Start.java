package org.disagn;

import org.disagn.cli.io.Printer;
import org.disagn.exceptions.NoInterfaceException;
import org.disagn.factories.DAOFactory;
import org.disagn.factories.UiFactory;
import org.disagn.interfaces.JobView;
import org.disagn.models.users.User;

public class Start{

    public static void main(String [] args) {

        boolean gui = true;
        boolean local = true;
        JobView startView = null;

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

        try {
            startView = UiFactory.getUi(starter, user);
        } catch (NoInterfaceException e) {
            Printer.print(e.getMessage());
        }
        assert startView != null;
        startView.startUi();

    }

}
