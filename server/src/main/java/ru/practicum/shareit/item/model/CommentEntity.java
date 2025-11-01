package ru.practicum.shareit.item.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.shareit.user.entity.UserEntity;

import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "comments")
@Getter
@Setter
@NoArgsConstructor
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "text")
    private String text;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private ItemEntity item;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private UserEntity author;
    @Column(name = "created")
    private Instant created;

    public CommentEntity(String text, ItemEntity item, UserEntity author) {
        this.text = text;
        this.item = item;
        this.author = author;
        this.created = Instant.now();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CommentEntity commentEntity = (CommentEntity) o;
        return Objects.equals(id, commentEntity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

}
