package org.example.neonarkintaketracker.exception;

// this exception handles the wrong password and username error
// this will be a 403 Forbidden serer message
// Capstone -- [ View All System Users ] Menu Choice

public class ForbiddenException extends RuntimeException{
    public ForbiddenException(String message){
        super(message);
    }
}
