package ru.practicum.shareit.booking.storage;

import ru.practicum.shareit.booking.domain.Booking;


import java.util.List;
import java.util.Optional;

public interface BookingStorage {

    Booking save(Booking booking);

    Optional<Booking> findById(Long bookingId);

    Booking findLastBooking(Long itemId);

    Booking findNextBooking(Long itemId);

    List<Booking> findAllByBooker_Id(Long bookerId);

    Booking findOneByBooker_IdAndItemEntity_Id(Long bookerId, Long itemId);
}
