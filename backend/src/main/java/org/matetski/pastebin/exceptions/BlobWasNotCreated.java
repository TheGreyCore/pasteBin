package org.matetski.pastebin.exceptions;

public class BlobWasNotCreated extends Exception{
    public BlobWasNotCreated(String errorMessage) {
        super(errorMessage);
    }

    public BlobWasNotCreated(Exception e) {
        super(e);
    }
}
