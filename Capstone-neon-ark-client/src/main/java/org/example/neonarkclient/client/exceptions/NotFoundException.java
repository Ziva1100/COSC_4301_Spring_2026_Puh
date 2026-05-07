package org.example.neonarkclient.client.exceptions;

// custom exception to handle 404 Not Found error

public class NotFoundException extends RuntimeException {
    public NotFoundException(Long id) {
        super("Creature with ID " + id+" not found");
    }
}
