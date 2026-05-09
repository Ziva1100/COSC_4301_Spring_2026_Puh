package org.example.neonarkintaketracker.dto;

import java.time.LocalDate;

public record ObservationRequest(
        Long id,
        String creatureName,
        LocalDate date,
        String ategory,
        String observation
) {
}
