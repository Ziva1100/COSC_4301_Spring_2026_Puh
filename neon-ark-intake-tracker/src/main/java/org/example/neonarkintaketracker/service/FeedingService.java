package org.example.neonarkintaketracker.service;

import org.example.neonarkintaketracker.dto.FeedingRequest;
import org.example.neonarkintaketracker.repository.FeedingRepository;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

@Service
public class FeedingService {

    private final FeedingRepository repository;

    public FeedingService(FeedingRepository repository){
        this.repository = repository;
    }
    // handle the logic of returning the list of feedings
    public List<FeedingRequest> getFeedingTimesSrv(String time){

        try {
            LocalTime localTime = LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm"));
            return repository.getFeedingTimes(localTime);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid time format. Use HH:mm");
        }
    }
}
