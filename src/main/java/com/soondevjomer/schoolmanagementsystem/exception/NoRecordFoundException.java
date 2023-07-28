package com.soondevjomer.schoolmanagementsystem.exception;

public class NoRecordFoundException extends RuntimeException{

    private String name;
    private String field;

    private String value;

    public NoRecordFoundException(String name, String field, String value) {
        super(name + " with " + field + " of " + value + " not found");
        this.name = name;
        this.field = field;
        this.value = value;
    }
}
