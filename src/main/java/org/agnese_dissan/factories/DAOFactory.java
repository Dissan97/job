package org.agnese_dissan.factories;


import org.agnese_dissan.daos.FileSystem;
import org.agnese_dissan.interfaces.DAO;

public class DAOFactory {

    private static FileSystem fileSystem = null;
    private DAOFactory(){
    }

    public static DAO getDAO(boolean db) {

        DAO dao = null;
        if (!db) {
            if (fileSystem == null) {
                fileSystem = new FileSystem();
            }
            dao = fileSystem;
        }
        return dao;
    }
}
