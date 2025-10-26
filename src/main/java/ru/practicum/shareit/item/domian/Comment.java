package ru.practicum.shareit.item.domian;

import lombok.Getter;
import lombok.Setter;
import ru.practicum.shareit.user.domain.User;

import java.time.Instant;

@Getter
@Setter
public class Comment {
    private Long id;
    private String text;
    private Item item;
    private User author;
    private Instant created;

    public Comment(Long id, String text, Item item, User author) {
        this.id = id;
        this.text = text;
        this.item = item;
        this.author = author;
        this.created = Instant.now();
    }
}
