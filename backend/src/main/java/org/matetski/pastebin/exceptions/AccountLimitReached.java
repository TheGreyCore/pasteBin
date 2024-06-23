package org.matetski.pastebin.exceptions;

public class AccountLimitReached extends Exception{
    public AccountLimitReached(String errorMessage){
        super(errorMessage);
    }
}

