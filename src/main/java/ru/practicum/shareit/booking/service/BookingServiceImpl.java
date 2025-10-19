package ru.practicum.shareit.booking.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.StatusBooking;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingResponse;
import ru.practicum.shareit.booking.mapper.BookingMapper;
import ru.practicum.shareit.booking.storage.BookingStorage;
import ru.practicum.shareit.excaption.NotFoundException;
import ru.practicum.shareit.excaption.ValidationException;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.user.service.UserService;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingStorage bookingStorage;
    private final UserService userService;
    private final ItemService itemService;

    @Override
    public BookingResponse postBooking(BookingDto bookingDto, Long bookerId) throws NotFoundException, ValidationException {
        bookingDto.setBookerId(bookerId);
        bookingDto.setStatus(StatusBooking.WAITING);
        validateBooking(bookingDto, bookerId);
        Item item = itemService.findItem(bookingDto.getItemId());
        Booking booking = bookingStorage.save(BookingMapper.bookingDtoToBooking(bookingDto, item));
        return BookingMapper.bookingDtoToBooking(booking);
    }

    private void validateBooking(BookingDto bookingDto, Long bookerId) throws NotFoundException, ValidationException {
        if (!itemService.existById(bookingDto.getItemId())) {
            throw new NotFoundException("not found");
        }
        if (!userService.existById(bookerId)) {
            throw new NotFoundException("not found");
        }
        if (!itemService.isAvailable(bookingDto.getItemId())) {
            throw new ValidationException("item not available");
        }
        if (bookingDto.getStart() == null || bookingDto.getEnd() == null) {
            throw new ValidationException("end or start don't equals null");
        }
        if (bookingDto.getEnd().isBefore(LocalDateTime.now())) {
            throw new ValidationException("time of end already passed");
        }
        if (bookingDto.getStart().isBefore(LocalDateTime.now())) {
            throw new ValidationException("time of start already passed");
        }
        if (bookingDto.getStart().isEqual(bookingDto.getEnd())) {
            throw new ValidationException("start equal to end");
        }
    }

    @Override
    @Transactional
    public BookingResponse patchBooking(Long bookingId, Long ownerId, boolean approve) throws ValidationException, NotFoundException {

        Booking booking = bookingStorage.findById(bookingId).orElseThrow();
        Item item = itemService.findItem(booking.getItem().getId());
        if (!item.getOwner().getId().equals(ownerId)) {
            throw new ValidationException("wrong user");
        }

        if (approve) {
            booking.setStatus(StatusBooking.APPROVED);
            item.setAvailable(false);
        } else {
            booking.setStatus(StatusBooking.REJECTED);
        }
        itemService.postItem(ItemMapper.itemToItemDto(item), ownerId);
        bookingStorage.save(booking);
        return BookingMapper.bookingDtoToBooking(booking);

    }

    @Override
    public BookingResponse getBookingByBookingIdAndUserId(Long bookingId, Long userId) throws NotFoundException, ValidationException {
        Booking booking = bookingStorage.findById(bookingId).orElseThrow(() -> new NotFoundException("not found"));
        Item item = itemService.findItem(booking.getItem().getId());
        if (booking.getBooker().getId().equals(userId) || item.getOwner().getId().equals(userId)) {
            BookingResponse dto = BookingMapper.bookingDtoToBooking(booking);
            return dto;
        } else {
            throw new ValidationException("wrong user");
        }

    }

    @Override
    public List<BookingResponse> getBookingsByUserId(Long bookerId) {
        List<Booking> bookings = bookingStorage.findAllByBooker_Id(bookerId);
        return bookings.stream()
                .map(BookingMapper::bookingDtoToBooking)
                .toList();
    }
}
