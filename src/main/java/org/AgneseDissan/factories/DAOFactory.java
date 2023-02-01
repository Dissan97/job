package org.AgneseDissan.factories;


import org.AgneseDissan.daos.FileSystem;
import org.AgneseDissan.daos.MariaDbJDBC;
import org.AgneseDissan.interfaces.DAO;

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

    public static boolean isFs(){
        return local;
    }

    public static void setStorageMethod(boolean isLocal) {
        local = isLocal;
    }
}
