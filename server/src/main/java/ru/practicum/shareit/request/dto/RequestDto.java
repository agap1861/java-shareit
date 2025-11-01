package ru.practicum.shareit.request.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class RequestDto {
    private Long id;
    private String description;
    private Instant created;
}
