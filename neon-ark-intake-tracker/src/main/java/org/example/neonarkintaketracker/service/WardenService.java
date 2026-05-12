package org.example.neonarkintaketracker.service;

import org.example.neonarkintaketracker.dto.WardenRequest;
import org.example.neonarkintaketracker.entity.Role;
import org.example.neonarkintaketracker.entity.Warden;
import org.example.neonarkintaketracker.exception.ForbiddenException;
import org.example.neonarkintaketracker.exception.UnauthorizedException;
import org.example.neonarkintaketracker.repository.RoleRepository;
import org.example.neonarkintaketracker.repository.WardenRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

// Capstone -- [ View All System Users ] Menu Choice
// This service will check if the ID of the warden works and
// if the credentials are correct, it will return a list of wardens/users
public class WardenService {

    private final WardenRepository repository;
    private final RoleRepository roleRep;

    public WardenService(WardenRepository repository, RoleRepository roleRep){
        this.repository = repository;
        this.roleRep = roleRep;
    }

    // Capstone -- [ View All System Users ] Menu Choice
    public List<WardenRequest> getAllUsers(String username, String password){

        // check if the username exists
        Warden warden = repository.findByUsername(username)
                .orElseThrow(() -> new UnauthorizedException("Incorrect Credentials."));

        if (!warden.getPassword().equals(password))
            throw new IllegalStateException("Incorrect Credentials.");

        // check if the user has the right privileges
        Role role = warden.getRole();

        if (!role.getRoleName().equalsIgnoreCase("admin"))
            throw new ForbiddenException("Access Denied!");

        // if it all fits, return the list of wardens
        return repository.getAllWardens();


    }



}
