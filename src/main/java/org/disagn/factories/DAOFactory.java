package org.disagn.factories;


import org.disagn.daos.FileSystem;
import org.disagn.daos.MariaDbJDBC;
import org.disagn.interfaces.DAO;

/**
 * Previous usage of Dao manager
 */

public class DAOFactory {

    private static FileSystem fileSystem = null;
    private static MariaDbJDBC dbms = null;
    private static boolean local = true;
    private DAOFactory(){
    }

    public static DAO getDAO() {
        DAO dao;
        if (local) {

            if (fileSystem == null) {
                fileSystem = new FileSystem();
            }

            dao = fileSystem;
        } else {

            if (dbms == null){
                dbms = new MariaDbJDBC();
            }

            dao = dbms;
        }
        return dao;
    }


    public static void setStorageMethod(boolean isLocal) {
        local = isLocal;
    }
}
