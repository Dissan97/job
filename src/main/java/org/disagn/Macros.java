package org.disagn;

/**
 * This enum is used to refer common MACROS used in the project
 */

public enum Macros {

    SIGN_IN_SUCCESS,
    SIGN_IN_FAILED,
    SIGN_UP_SUCCESS,
    BACK_CALL,
    ERROR,
    SIGN_IN,
    EMPLOYER,
    EMPLOYEE,
    ASSISTANT,
    START;


    @Override
    public java.lang.String toString() {
        return this.name();
    }
}
