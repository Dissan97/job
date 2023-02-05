package org.disagn.exceptions;

public class ApplyNotExistException extends Exception{
    public ApplyNotExistException() {
        super("Apply does not exists");
    }
}
