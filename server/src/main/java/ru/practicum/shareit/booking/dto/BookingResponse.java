package ru.practicum.shareit.booking.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.booking.StatusBooking;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class BookingResponse {
    private Long id;
    private LocalDateTime start;
    private LocalDateTime end;
    private StatusBooking status;
    private BookerDto booker;
    private ItemBookedDto item;

    public BookingResponse(Long id, LocalDateTime start, LocalDateTime end, StatusBooking status, BookerDto booker, ItemBookedDto item) {
        this.id = id;
        this.start = start;
        this.end = end;
        this.status = status;
        this.booker = booker;
        this.item = item;
    }
}
