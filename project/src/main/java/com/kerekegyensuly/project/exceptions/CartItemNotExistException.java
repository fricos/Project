package com.kerekegyensuly.project.exceptions;

public class CartItemNotExistException extends IllegalArgumentException {
    public CartItemNotExistException(String msg){super(msg);}
}
