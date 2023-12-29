package com.project.kiranaBackendService.Exception;

public class NotFoundException extends RuntimeException{

    public NotFoundException(String message) {
        super(message, null, false, false);
    }

}
