package org.example.neonarkintaketracker.service;

import org.example.neonarkintaketracker.dto.FeedingRequest;
import org.example.neonarkintaketracker.repository.FeedingRepository;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public class FeedingService {

    private final FeedingRepository repository;

    public FeedingService(FeedingRepository repository){
        this.repository = repository;
    }

    public Optional<List<FeedingRequest>> getFeedingTimesSrv(String time){
        return null;
    }
}
