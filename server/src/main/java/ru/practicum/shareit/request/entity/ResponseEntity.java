package ru.practicum.shareit.request.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import ru.practicum.shareit.item.model.ItemEntity;
import ru.practicum.shareit.user.entity.UserEntity;

@Getter
@Setter
@Table(name = "responses")
@Entity
public class ResponseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "request_id", nullable = false)
    private RequestEntity request;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private ItemEntity item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private UserEntity owner;
}
