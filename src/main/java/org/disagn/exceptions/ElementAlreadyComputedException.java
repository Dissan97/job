package org.disagn.exceptions;

public class ElementAlreadyComputedException extends Exception{
    public ElementAlreadyComputedException() {
        super("Element already computed");
    }
}
