package ru.practicum.shareit.request.domain;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Response {
    private Long id;
    private Long requestId;
    private Long itemId;
    private String itemName;
    private Long ownerId;
}
