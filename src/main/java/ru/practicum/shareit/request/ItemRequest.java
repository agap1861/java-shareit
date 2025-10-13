package ru.practicum.shareit.request;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * TODO Sprint add-item-requests.
 */
@Data
public class ItemRequest {
    private Long id;
    private Long requestorId;
    private String description;
    private LocalDateTime created;

    public ItemRequest(Long id, Long requestorId, String description) {
        this.id = id;
        this.requestorId = requestorId;
        this.description = description;
        this.created = LocalDateTime.now();
    }
}
