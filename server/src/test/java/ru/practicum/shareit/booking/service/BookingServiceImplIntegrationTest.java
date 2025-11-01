package ru.practicum.shareit.booking.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
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

import java.time.LocalDateTime;


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
        owner.setEmail("email@.ru");
        owner = userService.postUser(owner);

        user.setName("nameUser");
        user.setEmail("email@.ru");
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
        booking = bookingService.postBooking()

    }

}