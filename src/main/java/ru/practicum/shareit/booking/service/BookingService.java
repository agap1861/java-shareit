package ru.practicum.shareit.booking.service;


import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingResponse;
import ru.practicum.shareit.excaption.NotFoundException;
import ru.practicum.shareit.excaption.ValidationException;

import java.util.List;

public interface BookingService {
    BookingResponse postBooking(BookingDto bookingDto, Long bookerId) throws NotFoundException, ValidationException;

    BookingResponse patchBooking(Long bookingId, Long ownerId, boolean approve) throws ValidationException, NotFoundException;

    BookingResponse getBookingByBookingIdAndUserId(Long bookingId, Long userId) throws NotFoundException, ValidationException;

    List<BookingResponse> getBookingsByUserId(Long bookerId);
}
