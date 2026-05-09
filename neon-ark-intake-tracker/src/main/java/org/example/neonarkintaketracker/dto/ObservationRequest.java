package org.example.neonarkintaketracker.dto;

import java.time.LocalDate;

public record ObservationRequest(
        Long id,
        String name,
        LocalDate date,
        String category,
        String observation
) {
}
