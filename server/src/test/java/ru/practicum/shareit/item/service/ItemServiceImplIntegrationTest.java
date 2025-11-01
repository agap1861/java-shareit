package ru.practicum.shareit.item.service;

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
import ru.practicum.shareit.item.domian.Comment;
import ru.practicum.shareit.item.domian.Item;
import ru.practicum.shareit.item.dto.item.ItemWithComments;
import ru.practicum.shareit.item.storage.comment.CommentStorage;
import ru.practicum.shareit.item.storage.item.ItemStorage;
import ru.practicum.shareit.user.domain.User;
import ru.practicum.shareit.user.service.UserService;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@ActiveProfiles("test")
@Transactional
class ItemServiceImplIntegrationTest {
    private final ItemService itemService;
    private final ItemStorage itemStorage;
    private final UserService userService;
    private final BookingStorage bookingStorage;
    private final CommentStorage commentStorage;


    private User user = new User();
    private Item item = new Item();

    @BeforeEach
    public void creteUserAndItem() throws ValidationException, DuplicateException, NotFoundException {
        user.setName("name");
        user.setEmail("email@");
        user = userService.postUser(user);

        item.setName("nameItem");
        item.setDescription("descr");
        item.setAvailable(true);
        item.setOwnerId(user.getId());
        item = itemService.postItem(item);
    }

    @Test
    public void itemShouldSave() throws ValidationException, NotFoundException {
        Item newItem = new Item();
        newItem.setName("newNameItem");
        newItem.setDescription("descr1");
        newItem.setAvailable(true);
        newItem.setOwnerId(user.getId());
        newItem = itemService.postItem(newItem);

        Item saved = itemStorage.save(newItem);

        assertThat(newItem.getId(), equalTo(saved.getId()));
        assertThat(newItem.getName(), equalTo(saved.getName()));
        assertThat(newItem.getDescription(), equalTo(saved.getDescription()));
        assertThat(newItem.getAvailable(), equalTo(saved.getAvailable()));
        assertThat(newItem.getOwnerId(), equalTo(saved.getOwnerId()));

    }

    @Test
    public void shouldUpdateItemCorrect() throws ValidationException, NotFoundException {
        Item newItem = new Item();
        newItem.setId(item.getId());
        newItem.setName("newNameItem");
        newItem.setDescription("descr1");
        newItem.setAvailable(true);
        newItem.setOwnerId(user.getId());
        itemService.patchItem(newItem);

        Item updateItem = itemStorage.findById(newItem.getId()).orElseThrow();

        assertThat(newItem.getId(), equalTo(updateItem.getId()));
        assertThat(newItem.getName(), equalTo(updateItem.getName()));
        assertThat(newItem.getDescription(), equalTo(updateItem.getDescription()));
        assertThat(newItem.getAvailable(), equalTo(updateItem.getAvailable()));
        assertThat(newItem.getOwnerId(), equalTo(updateItem.getOwnerId()));


    }

    @Test
    public void shouldNotUpdateWhenOwnerWrong() throws ValidationException, DuplicateException {
        User newUSer = new User();
        newUSer.setName("name1");
        newUSer.setEmail("email@a.c");
        newUSer = userService.postUser(newUSer);

        Item newItem = new Item();
        newItem.setId(item.getId());
        newItem.setName("newNameItem");
        newItem.setDescription("descr1");
        newItem.setAvailable(true);
        newItem.setOwnerId(newUSer.getId());

        assertThrows(ValidationException.class, () -> itemService.patchItem(newItem));

    }

    @Test
    public void shouldThrowWhenPatchItemDoseNotExist() {
        Item newItem = new Item();
        newItem.setId(item.getId() + 1L);
        newItem.setName("newNameItem");
        newItem.setDescription("descr1");
        newItem.setAvailable(true);
        newItem.setOwnerId(user.getId());

        assertThrows(NotFoundException.class, () -> itemService.patchItem(newItem));

    }

    @Test
    public void shouldThrowWhenPostUserDoesNotExist() {
        Item newItem = new Item();
        newItem.setId(item.getId());
        newItem.setName("newNameItem");
        newItem.setDescription("descr1");
        newItem.setAvailable(true);
        newItem.setOwnerId(user.getId() + 1L);

        assertThrows(NotFoundException.class, () -> itemService.postItem(newItem));
    }

    @Test
    public void shouldThrowWhenPatchUserDoesNotExist() {
        Item newItem = new Item();
        newItem.setId(item.getId());
        newItem.setName("newNameItem");
        newItem.setDescription("descr1");
        newItem.setAvailable(true);
        newItem.setOwnerId(user.getId() + 1L);

        assertThrows(NotFoundException.class, () -> itemService.patchItem(newItem));
    }

    @Test
    public void shouldReturnItemWithoutComments() throws NotFoundException {
        ItemWithComments savedItem = itemService.getItem(item.getId(), user.getId());

        assertThat(item.getId(), equalTo(savedItem.getId()));
        assertThat(item.getName(), equalTo(savedItem.getName()));
        assertThat(item.getDescription(), equalTo(savedItem.getDescription()));
        assertThat(item.getAvailable(), equalTo(savedItem.getAvailable()));
        assertThat(null, equalTo(savedItem.getComments()));
        assertThat(null, equalTo(savedItem.getLastBooking()));
        assertThat(null, equalTo(savedItem.getNextBooking()));

    }

    @Test
    public void shouldReturnItemWithCommentsAndLastBookingToOwner() throws ValidationException, NotFoundException, DuplicateException {
        User userComment = new User();
        userComment.setName("name11");
        userComment.setEmail("yandex@.com");
        userComment = userService.postUser(userComment);

        Booking booking = new Booking();
        booking.setItem(item);
        booking.setStart(LocalDateTime.now().minusDays(2));
        booking.setEnd(LocalDateTime.now().minusDays(1));
        booking.setBookerId(userComment.getId());
        booking.setStatus(StatusBooking.WAITING);
        bookingStorage.save(booking);


        Comment comment = new Comment();
        comment.setItem(item);
        comment.setCreated(Instant.now());
        comment.setAuthor(userComment);
        comment.setText("text");
        commentStorage.save(comment);

        ItemWithComments itemWithComments = itemService.getItem(item.getId(), user.getId());

        assertThat(item.getId(), equalTo(itemWithComments.getId()));
        assertThat(item.getName(), equalTo(itemWithComments.getName()));
        assertThat(item.getDescription(), equalTo(itemWithComments.getDescription()));
        assertThat(item.getAvailable(), equalTo(itemWithComments.getAvailable()));
        assertThat(booking.getEnd(), equalTo(itemWithComments.getLastBooking()));
        assertThat(null, equalTo(itemWithComments.getNextBooking()));
        assertThat(1, equalTo(itemWithComments.getComments().size()));
        assertThat(comment.getText(), equalTo(itemWithComments.getComments().get(0)));


    }

    @Test
    public void shouldReturnItemWithCommentAndBookingsToOwner() throws ValidationException, DuplicateException, NotFoundException {
        User userComment = new User();
        userComment.setName("name11");
        userComment.setEmail("yandex@.com");
        userComment = userService.postUser(userComment);

        Booking booking = new Booking();
        booking.setItem(item);
        booking.setStart(LocalDateTime.now().minusDays(2));
        booking.setEnd(LocalDateTime.now().minusDays(1));
        booking.setBookerId(userComment.getId());
        booking.setStatus(StatusBooking.WAITING);
        bookingStorage.save(booking);

        Booking bookingNext = new Booking();
        bookingNext.setItem(item);
        bookingNext.setStart(LocalDateTime.now().plusDays(1));
        bookingNext.setEnd(LocalDateTime.now().plusDays(2));
        bookingNext.setBookerId(userComment.getId());
        bookingNext.setStatus(StatusBooking.WAITING);
        bookingStorage.save(bookingNext);

        Comment comment = new Comment();
        comment.setItem(item);
        comment.setCreated(Instant.now());
        comment.setAuthor(userComment);
        comment.setText("text");
        commentStorage.save(comment);

        ItemWithComments itemWithComments = itemService.getItem(item.getId(), user.getId());

        assertThat(item.getId(), equalTo(itemWithComments.getId()));
        assertThat(item.getName(), equalTo(itemWithComments.getName()));
        assertThat(item.getDescription(), equalTo(itemWithComments.getDescription()));
        assertThat(item.getAvailable(), equalTo(itemWithComments.getAvailable()));
        assertThat(booking.getEnd(), equalTo(itemWithComments.getLastBooking()));
        assertThat(bookingNext.getStart(), equalTo(itemWithComments.getNextBooking()));
        assertThat(1, equalTo(itemWithComments.getComments().size()));
        assertThat(comment.getText(), equalTo(itemWithComments.getComments().get(0)));
    }

    @Test
    public void shouldReturnItemWithCommentToNotOwner() throws NotFoundException, ValidationException, DuplicateException {
        User userComment = new User();
        userComment.setName("name11");
        userComment.setEmail("yandex@.com");
        userComment = userService.postUser(userComment);

        Booking booking = new Booking();
        booking.setItem(item);
        booking.setStart(LocalDateTime.now().minusDays(2));
        booking.setEnd(LocalDateTime.now().minusDays(1));
        booking.setBookerId(userComment.getId());
        booking.setStatus(StatusBooking.WAITING);
        bookingStorage.save(booking);


        Comment comment = new Comment();
        comment.setItem(item);
        comment.setCreated(Instant.now());
        comment.setAuthor(userComment);
        comment.setText("text");
        commentStorage.save(comment);

        ItemWithComments itemWithComments = itemService.getItem(item.getId(), userComment.getId());

        assertThat(item.getId(), equalTo(itemWithComments.getId()));
        assertThat(item.getName(), equalTo(itemWithComments.getName()));
        assertThat(item.getDescription(), equalTo(itemWithComments.getDescription()));
        assertThat(item.getAvailable(), equalTo(itemWithComments.getAvailable()));
        assertThat(null, equalTo(itemWithComments.getLastBooking()));
        assertThat(null, equalTo(itemWithComments.getNextBooking()));
        assertThat(1, equalTo(itemWithComments.getComments().size()));
        assertThat(comment.getText(), equalTo(itemWithComments.getComments().get(0)));
    }

    @Test
    public void shouldGetAllItemsByOwner() throws ValidationException, NotFoundException {
        Item newIem = new Item();
        newIem.setName("nameItem");
        newIem.setDescription("descr1");
        newIem.setAvailable(true);
        newIem.setOwnerId(user.getId());
        newIem = itemService.postItem(newIem);

        List<Item> items = itemService.getItemByOwner(user.getId());

        assertThat(2, equalTo(items.size()));
        assertThat(item.getId(), equalTo(items.getFirst().getId()));
        assertThat(newIem.getId(), equalTo(items.getLast().getId()));

    }

    @Test
    public void shouldReturnTrueWhenItemExist() {
        assertThat(true, equalTo(itemService.existById(item.getId())));
    }

    @Test
    public void shouldReturnFalseWhenItemDoseNotExist() {
        assertThat(false, equalTo(itemService.existById(item.getId() + 1L)));
    }

}