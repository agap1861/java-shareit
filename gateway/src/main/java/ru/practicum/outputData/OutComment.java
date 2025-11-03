package ru.practicum.outputData;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
public class OutComment {
    private Long id;
    private String text;
    private String authorName;
    private Instant created;
}
