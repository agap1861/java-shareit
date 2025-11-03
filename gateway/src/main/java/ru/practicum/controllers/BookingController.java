package ru.practicum.controllers;


import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import ru.practicum.client.BookingClient;
import ru.practicum.exeption.ValidationException;
import ru.practicum.inputData.InBooking;

import ru.practicum.outputData.OutBooking;


import java.util.List;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingClient bookingClient;

    @PostMapping
    public OutBooking postBooking(@RequestHeader("X-Sharer-User-Id") Long bookerId, @RequestBody InBooking inBooking) throws ValidationException {
        return bookingClient.postBooking(bookerId, inBooking);
    }

    @PatchMapping("/{bookingId}")
    public OutBooking patchBooking(@PathVariable Long bookingId, @RequestParam(name = "approved") Boolean approved, @RequestHeader("X-Sharer-User-Id") Long ownerId) throws ValidationException {
        return bookingClient.patchBooking(bookingId, approved, ownerId);
    }

    @GetMapping("/{bookingId}")
    public OutBooking getBookingByUserIdAndBookingId(@PathVariable Long bookingId, @RequestHeader("X-Sharer-User-Id") Long userId) throws ValidationException {
        return bookingClient.getBookingByUserIdAndBookingId(bookingId, userId);

    }

    @GetMapping
    public List<OutBooking> getBookingsByUserId(@RequestHeader("X-Sharer-User-Id") Long bookerId) throws ValidationException {
        return bookingClient.getBookingsByUserId(bookerId);
    }


}
