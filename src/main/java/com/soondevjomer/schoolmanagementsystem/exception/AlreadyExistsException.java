package com.soondevjomer.schoolmanagementsystem.exception;

public class AlreadyExistsException extends RuntimeException{

    private String name;
    private String field;

    private String value;

    public AlreadyExistsException(String name, String field, String value) {
        super(name + " with " + field + " of " + value + " is already exists.");
        this.name = name;
        this.field = field;
        this.value = value;
    }
}
