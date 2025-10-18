package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingResponse;
import ru.practicum.shareit.booking.service.BookingService;

import ru.practicum.shareit.excaption.NotFoundException;
import ru.practicum.shareit.excaption.ValidationException;

import java.util.List;

/**
 * TODO Sprint add-bookings.
 */
@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @PostMapping
    public BookingResponse postBooking(@RequestHeader("X-Sharer-User-Id") Long bookerId, @RequestBody BookingDto bookingDto) throws ValidationException, NotFoundException {
        return bookingService.postBooking(bookingDto, bookerId);
    }

    @PatchMapping("/{bookingId}")
    public BookingResponse patchBooking(@PathVariable Long bookingId, @RequestParam(name = "approved") Boolean approved, @RequestHeader("X-Sharer-User-Id") Long ownerId) throws ValidationException, NotFoundException {
        return bookingService.patchBooking(bookingId, ownerId, approved);
    }

    @GetMapping("/{bookingId}")
    public BookingResponse getBookingByUserIdAndBookingId(@PathVariable Long bookingId, @RequestHeader("X-Sharer-User-Id") Long userId) throws ValidationException, NotFoundException {
        return bookingService.getBookingByBookingIdAndUserId(bookingId, userId);
    }

    @GetMapping
    public List<BookingResponse> getBookingsByUserId(@RequestHeader("X-Sharer-User-Id") Long bookerId) {
        return bookingService.getBookingsByUserId(bookerId);
    }


}
