package ru.practicum.outputData;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
public class RequestWithResponse {
    private Long id;
    private String description;
    private Instant created;
    private List<Response> items;
}
