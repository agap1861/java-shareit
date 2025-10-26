package ru.practicum.shareit.booking.service;


import ru.practicum.shareit.booking.domain.Booking;
import ru.practicum.shareit.booking.dto.BookingRequestDto;
import ru.practicum.shareit.excaption.NotFoundException;
import ru.practicum.shareit.excaption.ValidationException;

import java.util.List;

public interface BookingService {
    Booking postBooking(BookingRequestDto bookingRequestDto) throws NotFoundException, ValidationException;

    Booking patchBooking(Long bookingId, Long ownerId, boolean approve) throws ValidationException, NotFoundException;

    Booking getBookingByBookingIdAndUserId(Long bookingId, Long userId) throws NotFoundException, ValidationException;

    List<Booking> getBookingsByUserId(Long bookerId);

    Booking findNextBooking(Long itemId);

    Booking findLastBooking(Long itemId);


}
