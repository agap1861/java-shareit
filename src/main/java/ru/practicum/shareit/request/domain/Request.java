package ru.practicum.shareit.request.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

/**
 * TODO Sprint add-item-requests.
 */
@Getter
@Setter
public class Request {
    private Long id;
    private Long requestorId;
    private String description;
    private Instant created;

    public Request(Long id, Long requestorId, String description) {
        this.id = id;
        this.requestorId = requestorId;
        this.description = description;
        this.created = Instant.now();
    }
}
