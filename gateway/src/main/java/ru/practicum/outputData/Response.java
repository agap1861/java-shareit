package ru.practicum.outputData;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Response {
    private Long id;
    private String name;
    private Long ownerId;
}
