package ru.practicum.shareit.item.dto.item;

import lombok.Data;


import java.time.LocalDateTime;
import java.util.List;

@Data
public class ItemWithComments {
    private Long id;
    private String name;
    private String description;
    private Boolean available;
    private LocalDateTime lastBooking;
    private LocalDateTime nextBooking;
    private List<String> comments;

    public ItemWithComments(Long id, String name, String description, Boolean available, LocalDateTime lastBooking, LocalDateTime nextBooking, List<String> comments) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.available = available;
        this.lastBooking = lastBooking;
        this.nextBooking = nextBooking;
        this.comments = comments;
    }

    public ItemWithComments(Long id, String name, String description, Boolean available) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.available = available;
    }

    public ItemWithComments(Long id, String name, String description, Boolean available, LocalDateTime lastBooking, List<String> comments) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.available = available;
        this.lastBooking = lastBooking;
        this.comments = comments;
    }

    public ItemWithComments(Long id, String name, String description, Boolean available, List<String> comments) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.available = available;
        this.comments = comments;
    }

}
