package org.example.exceptions;

public class DataChangeException extends Exception {
    /*public DataChangeException(Throwable cause) {
        super(cause);
    }*/

    public DataChangeException(String message) {
        super(message);
    }
}
