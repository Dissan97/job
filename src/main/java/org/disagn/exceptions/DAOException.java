package org.disagn.exceptions;


public class DAOException extends Exception {
    public DAOException() {
        super("DAO Exception Cannot load drivers");
    }
}
