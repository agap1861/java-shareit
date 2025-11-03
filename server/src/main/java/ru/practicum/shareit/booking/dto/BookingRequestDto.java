package ru.practicum.shareit.booking.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.booking.StatusBooking;

import java.time.LocalDateTime;

/**
 * TODO Sprint add-bookings.
 */
@Data
@NoArgsConstructor
public class BookingRequestDto {
    private Long itemId;
    private LocalDateTime start;
    private LocalDateTime end;
    private Long bookerId;
    private StatusBooking status;


    public BookingRequestDto(Long itemId, LocalDateTime start, LocalDateTime end, Long bookerId, StatusBooking status) {
        this.itemId = itemId;
        this.start = start;
        this.end = end;
        this.bookerId = bookerId;
        this.status = status;
    }
}
