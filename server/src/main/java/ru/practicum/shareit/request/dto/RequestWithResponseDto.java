package ru.practicum.shareit.request.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
public class RequestWithResponseDto {
    private Long id;
    private String description;
    private Instant created;
    private List<ResponseDto> items;
}
