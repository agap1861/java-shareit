package ru.practicum.shareit.booking.entity;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.shareit.booking.StatusBooking;
import ru.practicum.shareit.item.model.ItemEntity;
import ru.practicum.shareit.user.entity.UserEntity;

import java.time.LocalDateTime;


/**
 * TODO Sprint add-bookings.
 */
@Entity
@Table(name = "bookings")
@NoArgsConstructor
@Getter
@Setter
public class BookingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "start_date")
    private LocalDateTime start;
    @Column(name = "end_date")
    private LocalDateTime end;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private ItemEntity item;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booker_id")
    private UserEntity booker;
    @Enumerated(EnumType.STRING)
    private StatusBooking status;

    public BookingEntity(LocalDateTime start, LocalDateTime end, ItemEntity item, UserEntity booker, StatusBooking status) {
        this.start = start;
        this.end = end;
        this.item = item;
        this.booker = booker;
        this.status = status;
    }


}
