package com.kerekegyensuly.project.exceptions;

public class AuthenticationFailException extends IllegalArgumentException{
    public AuthenticationFailException(String msg){super(msg);}
}
