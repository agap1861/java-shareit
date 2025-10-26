package ru.practicum.shareit.booking.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.domain.Booking;
import ru.practicum.shareit.booking.StatusBooking;
import ru.practicum.shareit.booking.dto.BookingRequestDto;
import ru.practicum.shareit.booking.mapper.BookingDomainDtoMapper;
import ru.practicum.shareit.booking.storage.BookingStorage;
import ru.practicum.shareit.excaption.NotFoundException;
import ru.practicum.shareit.excaption.ValidationException;
import ru.practicum.shareit.item.domian.Item;
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
    private final BookingDomainDtoMapper bookingDomainDtoMapper;

    @Override
    public Booking postBooking(BookingRequestDto bookingRequestDto) throws NotFoundException, ValidationException {

        validateBooking(bookingRequestDto);
        Item item = itemService.findItemById(bookingRequestDto.getItemId()).get();
        return bookingStorage.save(bookingDomainDtoMapper.dtoToDomain(bookingRequestDto, item));

    }

    private void validateBooking(BookingRequestDto bookingRequestDto) throws NotFoundException, ValidationException {
        if (!itemService.existById(bookingRequestDto.getItemId())) {
            throw new NotFoundException("not found");
        }
        if (!userService.existById(bookingRequestDto.getBookerId())) {
            throw new NotFoundException("not found");
        }
        if (!itemService.isAvailable(bookingRequestDto.getItemId())) {
            throw new ValidationException("item not available");
        }
        if (bookingRequestDto.getStart() == null || bookingRequestDto.getEnd() == null) {
            throw new ValidationException("end or start don't equals null");
        }
        if (bookingRequestDto.getEnd().isBefore(LocalDateTime.now())) {
            throw new ValidationException("time of end already passed");
        }
        if (bookingRequestDto.getStart().isBefore(LocalDateTime.now())) {
            throw new ValidationException("time of start already passed");
        }
        if (bookingRequestDto.getStart().isEqual(bookingRequestDto.getEnd())) {
            throw new ValidationException("start equal to end");
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
/*        itemService.postItem(ItemMapper.itemToItemDto(itemEntity), ownerId);
        bookingStorage.save(bookingEntity);*/
        itemService.postItem(item);
        return booking;//?????? что тут унас
        // return BookingMapper.bookingDtoToBooking(bookingEntity);

    }

    @Override
    public Booking getBookingByBookingIdAndUserId(Long bookingId, Long userId) throws NotFoundException, ValidationException {
        Booking booking = bookingStorage.findById(bookingId).orElseThrow(() -> new NotFoundException("not found"));
        Item item = itemService.findItemById(booking.getItem().getId()).orElseThrow(() -> new NotFoundException("not found"));
        if (booking.getBookerId().equals(userId) || item.getOwnerId().equals(userId)) {
            // BookingResponse dto = BookingMapper.bookingDtoToBooking(bookingEntity);
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
