package ru.practicum.shareit.booking.domain;

import lombok.Getter;
import lombok.Setter;
import ru.practicum.shareit.booking.StatusBooking;
import ru.practicum.shareit.item.domian.Item;

import java.time.LocalDateTime;

@Getter
@Setter
public class Booking {
    private Long id;
    private LocalDateTime start;
    private LocalDateTime end;
    private Item item;
    private Long bookerId;
    private StatusBooking status;
}
