package org.example.neonarkintaketracker.exception;

// this exception handles the wrong password and username error
// this will be a 401 Unauthorized serer message
// Capstone -- [ View All System Users ] Menu Choice

public class UnauthorizedException extends RuntimeException{
    public UnauthorizedException(String message){
        super(message);
    }
}
