package ru.practicum.shareit.booking.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.StatusBooking;
import ru.practicum.shareit.booking.domain.Booking;
import ru.practicum.shareit.booking.storage.BookingStorage;
import ru.practicum.shareit.exception.DuplicateException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.domian.Item;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.user.domain.User;
import ru.practicum.shareit.user.service.UserService;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.awt.print.Book;
import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;


@SpringBootTest
@ActiveProfiles("test")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Transactional
class BookingServiceImplIntegrationTest {
    private final BookingService bookingService;
    private final BookingStorage bookingStorage;
    private final ItemService itemService;
    private final UserService userService;

    private User owner = new User();
    private User user = new User();
    private Item item = new Item();
    private Booking booking = new Booking();

    @BeforeEach
    public void crateUserAndItemAndBooking() throws ValidationException, DuplicateException, NotFoundException {
        owner.setName("nameOwner");
        owner.setEmail("owner@.ru");
        owner = userService.postUser(owner);

        user.setName("nameUser");
        user.setEmail("user@.ru");
        user = userService.postUser(user);

        item.setOwnerId(owner.getId());
        item.setName("itemName");
        item.setDescription("description");
        item.setAvailable(true);
        item = itemService.postItem(item);

        booking.setItem(item);
        booking.setStatus(StatusBooking.WAITING);
        booking.setBookerId(user.getId());
        booking.setStart(LocalDateTime.now().minusDays(2));
        booking.setEnd(LocalDateTime.now().minusDays(1));
        booking = bookingService.postBooking(booking);

    }

    @Test
    public void shouldSaveBooking() throws NotFoundException {
        Booking savedBooking = bookingStorage.findById(booking.getId()).orElseThrow(() -> new NotFoundException("not found"));

        assertThat(booking.getId(), equalTo(savedBooking.getId()));
        assertThat(booking.getBookerId(), equalTo(savedBooking.getBookerId()));
        assertThat(booking.getStart(), equalTo(savedBooking.getStart()));
        assertThat(booking.getEnd(), equalTo(savedBooking.getEnd()));
        assertThat(booking.getStatus(), equalTo(savedBooking.getStatus()));
        assertThat(booking.getItem().getId(), equalTo(savedBooking.getItem().getId()));

    }

    @Test
    public void shouldThrowExceptionWhenUserDoseNotExist() throws ValidationException, NotFoundException {
        Booking newBooking = new Booking();
        newBooking.setItem(item);
        newBooking.setStatus(StatusBooking.WAITING);
        newBooking.setBookerId(user.getId() + 2L);
        newBooking.setStart(LocalDateTime.now().minusDays(2));
        newBooking.setEnd(LocalDateTime.now().minusDays(1));

        assertThrows(NotFoundException.class, () -> bookingService.postBooking(newBooking));
    }

    @Test
    public void shouldThrowExceptionWhenItemDoseNotExist() {
        Booking newBooking = new Booking();
        Item newItem = new Item();

        newItem.setId(item.getId() + 1L);
        newBooking.setItem(newItem);
        newBooking.setStatus(StatusBooking.WAITING);
        newBooking.setBookerId(user.getId());
        newBooking.setStart(LocalDateTime.now().minusDays(2));
        newBooking.setEnd(LocalDateTime.now().minusDays(1));

        assertThrows(NotFoundException.class, () -> bookingService.postBooking(newBooking));
    }

    @Test
    public void shouldThrowExceptionWhenItemNotAvailable() throws ValidationException, NotFoundException {
        Item newItem = new Item();

        newItem.setOwnerId(owner.getId());
        newItem.setName("itemName");
        newItem.setDescription("description");
        newItem.setAvailable(false);
        newItem = itemService.postItem(newItem);

        Booking newBooking = new Booking();
        newBooking.setItem(newItem);
        newBooking.setStatus(StatusBooking.WAITING);
        newBooking.setBookerId(user.getId());
        newBooking.setStart(LocalDateTime.now().minusDays(2));
        newBooking.setEnd(LocalDateTime.now().minusDays(1));

        assertThrows(ValidationException.class, () -> bookingService.postBooking(newBooking));

    }

    @Test
    public void shouldChangeStatusToApprovedWhenPatchWithTrue() throws ValidationException, NotFoundException {
        Booking patchBooking = bookingService.patchBooking(booking.getId(), owner.getId(), true);

        assertThat(StatusBooking.APPROVED, equalTo(patchBooking.getStatus()));

    }

    @Test
    public void shouldChangeStatusTooRejectedWhenPatchWithFalse() throws ValidationException, NotFoundException {
        Booking patchBooking = bookingService.patchBooking(booking.getId(), owner.getId(), false);

        assertThat(StatusBooking.REJECTED, equalTo(patchBooking.getStatus()));

    }

    @Test
    public void shouldThrowExceptionWhenWrongOwner() {
        assertThrows(ValidationException.class, () -> bookingService.patchBooking(booking.getId(), user.getId(), false));
    }

    @Test
    public void shouldReturnBookingByIdAndOwnerId() throws ValidationException, NotFoundException {
        Booking returnBooking = bookingService.getBookingByBookingIdAndUserId(booking.getId(), owner.getId());

        assertThat(returnBooking.getId(), equalTo(booking.getId()));
        assertThat(returnBooking.getBookerId(), equalTo(booking.getBookerId()));
        assertThat(returnBooking.getStart(), equalTo(booking.getStart()));
        assertThat(returnBooking.getEnd(), equalTo(booking.getEnd()));
        assertThat(returnBooking.getStatus(), equalTo(booking.getStatus()));
        assertThat(returnBooking.getItem().getId(), equalTo(booking.getItem().getId()));

    }


    @Test
    public void shouldReturnBookingsByUserId() throws ValidationException, NotFoundException {
        Booking newBooking = new Booking();
        newBooking.setItem(item);
        newBooking.setStatus(StatusBooking.WAITING);
        newBooking.setBookerId(user.getId());
        newBooking.setStart(LocalDateTime.now().minusDays(5));
        newBooking.setEnd(LocalDateTime.now().minusDays(4));
        newBooking = bookingService.postBooking(newBooking);

        List<Booking> bookings = bookingService.getBookingsByUserId(user.getId());

        assertThat(2, equalTo(bookings.size()));
        assertThat(booking.getId(), equalTo(bookings.getFirst().getId()));
        assertThat(newBooking.getId(), equalTo(bookings.getLast().getId()));
    }

}