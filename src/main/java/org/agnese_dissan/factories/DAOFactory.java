package org.agnese_dissan.factories;


import org.agnese_dissan.daos.FileSystem;
import org.agnese_dissan.daos.MysqlDb;
import org.agnese_dissan.interfaces.DAO;

public class DAOFactory {

    private static FileSystem fileSystem = null;
    private static MysqlDb mysqlDb = null;
    private static boolean local = false;
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

            if (mysqlDb == null){
                mysqlDb = new MysqlDb();
            }

            dao = mysqlDb;
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
