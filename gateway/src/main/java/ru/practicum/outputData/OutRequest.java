package ru.practicum.outputData;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@NoArgsConstructor
@Data
public class OutRequest {
    private Long id;
    private String description;
    private Instant created;
}
