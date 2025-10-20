package ru.practicum.shareit.booking.mapper;

import lombok.RequiredArgsConstructor;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.dto.BookerDto;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingResponse;
import ru.practicum.shareit.booking.dto.ItemBookedDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.storage.ItemStorage;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.storage.UserStorage;

@RequiredArgsConstructor
public class BookingMapper {
    private final UserStorage userStorage;
    private final ItemStorage itemStorage;

    public static Booking bookingDtoToBooking(BookingDto dto, Item item) {

        return new Booking(dto.getStart(), dto.getEnd(), item, new User(dto.getBookerId()), dto.getStatus());
    }

    public static BookingResponse bookingDtoToBooking(Booking booking) {

        return new BookingResponse(booking.getId(), booking.getStartDate(), booking.getEndDate(), booking.getStatus(),
                new BookerDto(booking.getBooker().getId()),
                new ItemBookedDto(booking.getItem().getId(), booking.getItem().getName()));
    }

    public static BookingDto bookingToBookingDto(Booking booking) {
        return new BookingDto(booking.getItem().getId(), booking.getStartDate(), booking.getEndDate(), booking.getBooker().getId(), booking.getStatus());
    }

    private Item getItem(Long itemId) {
        return itemStorage.getReferenceById(itemId);
    }


}
