package ru.practicum.shareit.request.dto;

import lombok.Data;

@Data
public class RequestCreateDto {
    private Long requesterId;
    private String description;
}
