package ru.practicum.shareit.booking.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.domain.Booking;
import ru.practicum.shareit.booking.StatusBooking;


import ru.practicum.shareit.booking.storage.BookingStorage;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.domian.Item;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.user.service.UserService;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingStorage bookingStorage;
    private final UserService userService;
    private final ItemService itemService;

    @Override
    public Booking postBooking(Booking booking) throws NotFoundException, ValidationException {

        validateBooking(booking);
        Item item = itemService.findItemById(booking.getItem().getId()).orElseThrow(() -> new NotFoundException("not found"));
        booking.setItem(item);
        if (booking.getStatus() == null) {
            booking.setStatus(StatusBooking.WAITING);
        }
        return bookingStorage.save(booking);

    }

    private void validateBooking(Booking booking) throws NotFoundException, ValidationException {
        if (!itemService.existById(booking.getItem().getId())) {
            throw new NotFoundException("not found");
        }
        if (!userService.existById(booking.getBookerId())) {
            throw new NotFoundException("not found");
        }
        if (!itemService.isAvailable(booking.getItem().getId())) {
            throw new ValidationException("item not available");
        }
    }

    @Override
    @Transactional
    public Booking patchBooking(Long bookingId, Long ownerId, boolean approve) throws ValidationException, NotFoundException {

        Booking booking = bookingStorage.findById(bookingId).orElseThrow();
        Item item = itemService.findItemById(booking.getItem().getId()).orElseThrow(() -> new NotFoundException("not found"));
        if (!item.getOwnerId().equals(ownerId)) {
            throw new ValidationException("wrong user");
        }
        if (approve) {
            booking.setStatus(StatusBooking.APPROVED);
            item.setAvailable(false);
        } else {
            booking.setStatus(StatusBooking.REJECTED);
        }
        itemService.postItem(item);
        bookingStorage.save(booking);
        return booking;


    }

    @Override
    public Booking getBookingByBookingIdAndUserId(Long bookingId, Long userId) throws NotFoundException, ValidationException {
        Booking booking = bookingStorage.findById(bookingId).orElseThrow(() -> new NotFoundException("not found"));
        Item item = itemService.findItemById(booking.getItem().getId()).orElseThrow(() -> new NotFoundException("not found"));
        if (booking.getBookerId().equals(userId) || item.getOwnerId().equals(userId)) {
            return booking;
        } else {
            throw new ValidationException("wrong user");
        }

    }

    @Override
    public List<Booking> getBookingsByUserId(Long bookerId) {
        return bookingStorage.findAllByBooker_Id(bookerId);

    }

    @Override
    public Booking findNextBooking(Long itemId) {
        return bookingStorage.findNextBooking(itemId);
    }

    @Override
    public Booking findLastBooking(Long itemId) {
        return bookingStorage.findLastBooking(itemId);
    }
}
