package ru.practicum.shareit.item.domian;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Item {
    private Long id;
    private Long ownerId;
    private String name;
    private String description;
    private Boolean available;
    private Long requestId;
}
