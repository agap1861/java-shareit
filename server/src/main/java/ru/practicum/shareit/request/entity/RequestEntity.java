package ru.practicum.shareit.request.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import ru.practicum.shareit.user.entity.UserEntity;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@Table(name = "requests")
@Entity
public class RequestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requester_id")
    private UserEntity requester;

    @Column(name = "description")
    private String description;

    @Column(name = "created")
    private Instant created;

    @OneToMany(mappedBy = "request", cascade = CascadeType.MERGE)
    private List<ResponseEntity> responses;
}
