package ru.practicum.shareit.item.model;

import jakarta.persistence.*;
import lombok.*;
import ru.practicum.shareit.user.entity.UserEntity;

import java.util.Objects;

/**
 * TODO Sprint add-controllers.
 */
@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "items")
@NoArgsConstructor
public class ItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private UserEntity owner;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "available", nullable = false)
    private Boolean available;

    public ItemEntity(UserEntity owner, String name, String description, Boolean available) {
        this.owner = owner;
        this.name = name;
        this.description = description;
        this.available = available;
    }

    public ItemEntity(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ItemEntity itemEntity = (ItemEntity) o;
        return Objects.equals(id, itemEntity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

}
