package org.disagn.exceptions;

public class NoInterfaceException extends Throwable {
    public NoInterfaceException(String gui) {
        super(gui + " THERE IS NO INTERFACES: ");
    }

}
