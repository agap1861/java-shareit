package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.domain.Booking;
import ru.practicum.shareit.booking.dto.BookingRequestDto;
import ru.practicum.shareit.booking.dto.BookingResponse;
import ru.practicum.shareit.booking.mapper.BookingDomainDtoMapper;
import ru.practicum.shareit.booking.service.BookingService;

import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;

import java.util.List;

/**
 * TODO Sprint add-bookings.
 */
@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;
    private final BookingDomainDtoMapper bookingDomainDtoMapper;

    @PostMapping
    public BookingResponse postBooking(@RequestHeader("X-Sharer-User-Id") Long bookerId, @RequestBody BookingRequestDto bookingRequestDto) throws ValidationException, NotFoundException {
        bookingRequestDto.setBookerId(bookerId);
        bookingRequestDto.setStatus(StatusBooking.WAITING);
        Booking booking = bookingService.postBooking(bookingRequestDto);
        return bookingDomainDtoMapper.domainToDto(booking);
    }

    @PatchMapping("/{bookingId}")
    public BookingResponse patchBooking(@PathVariable Long bookingId, @RequestParam(name = "approved") Boolean approved, @RequestHeader("X-Sharer-User-Id") Long ownerId) throws ValidationException, NotFoundException {

        Booking booking = bookingService.patchBooking(bookingId, ownerId, approved);
        return bookingDomainDtoMapper.domainToDto(booking);
    }

    @GetMapping("/{bookingId}")
    public BookingResponse getBookingByUserIdAndBookingId(@PathVariable Long bookingId, @RequestHeader("X-Sharer-User-Id") Long userId) throws ValidationException, NotFoundException {
        Booking booking = bookingService.getBookingByBookingIdAndUserId(bookingId, userId);
        return bookingDomainDtoMapper.domainToDto(booking);
    }

    @GetMapping
    public List<BookingResponse> getBookingsByUserId(@RequestHeader("X-Sharer-User-Id") Long bookerId) {
        List<Booking> bookings = bookingService.getBookingsByUserId(bookerId);
        return bookings.stream().map(bookingDomainDtoMapper::domainToDto).toList();
    }


}
