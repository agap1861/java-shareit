package ru.practicum.shareit.item.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
public class CommentResponse {
    private Long id;
    private String text;
    private String authorName;
    private Instant created;

    public CommentResponse(Long id, String text, String authorName, Instant created) {
        this.id = id;
        this.text = text;
        this.authorName = authorName;
        this.created = created;
    }
}
