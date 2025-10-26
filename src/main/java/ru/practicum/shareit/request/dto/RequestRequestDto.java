package ru.practicum.shareit.request.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * TODO Sprint add-item-requests.
 */
@Data
@NoArgsConstructor
public class RequestRequestDto {
    private Long requestorId;
    private String description;

    public RequestRequestDto(Long requestorId, String description) {
        this.requestorId = requestorId;
        this.description = description;
    }
}
