package ru.practicum.shareit.request.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO Sprint add-item-requests.
 */
@Getter
@Setter
@NoArgsConstructor
public class Request {
    private Long id;
    private Long requesterId;
    private String description;
    private Instant created;
    private List<Response> responses = new ArrayList<>();

    public Request(Long id, Long requesterId, String description) {
        this.id = id;
        this.requesterId = requesterId;
        this.description = description;
        this.created = Instant.now();
    }
}
