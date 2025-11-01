package ru.practicum.shareit.item.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.practicum.shareit.booking.domain.Booking;
import ru.practicum.shareit.booking.storage.BookingStorage;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.domian.Comment;
import ru.practicum.shareit.item.domian.Item;
import ru.practicum.shareit.item.dto.item.ItemWithComments;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.storage.comment.CommentStorage;
import ru.practicum.shareit.item.storage.item.ItemStorage;
import ru.practicum.shareit.request.service.RequestService;
import ru.practicum.shareit.user.domain.User;
import ru.practicum.shareit.user.service.UserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemStorage itemStorage;
    private final UserService userService;
    private final RequestService requestService;
    private final BookingStorage bookingStorage;
    private final CommentStorage commentStorage;

    @Override
    @Transactional
    public Item postItem(Item item) throws NotFoundException {
        if (!userService.existById(item.getOwnerId())) {
            throw new NotFoundException("user not found");
        }
        Item saveItem = itemStorage.save(item);
        if (item.getRequestId() != null) {
            item.setId(saveItem.getId());
            requestService.postResponse(item);
        }
        return saveItem;
    }

    @Override
    public Item patchItem(Item item) throws NotFoundException, ValidationException {
        if (!userService.existById(item.getOwnerId())) {
            throw new NotFoundException("user not correct");
        }
        Item oldItem = itemStorage.findById(item.getId()).orElseThrow(() -> new NotFoundException("not found"));
        if (!oldItem.getOwnerId().equals(item.getOwnerId())) {
            throw new ValidationException("owner id not correct");
        }
        if (item.getName() != null) {
            oldItem.setName(item.getName());
        }
        if (item.getDescription() != null) {
            oldItem.setDescription(item.getDescription());
        }
        if (item.getAvailable() != null) {
            oldItem.setAvailable(item.getAvailable());
        }
        return itemStorage.save(oldItem);
    }

    @Override
    public ItemWithComments getItem(Long itemId, Long userId) throws NotFoundException {
        Item item = itemStorage.findById(itemId).orElseThrow(() -> new NotFoundException("not found"));
        Booking last = bookingStorage.findLastBooking(itemId);
        Booking next = bookingStorage.findNextBooking(itemId);
        List<String> comments = commentStorage.findAllByItemById(itemId).stream()
                .map(Comment::getText)
                .toList();

        if (comments.isEmpty()) {
            return new ItemWithComments(item.getId(), item.getName(), item.getDescription(), item.getAvailable());
        }
        if (next == null && item.getOwnerId().equals(userId)) {
            return new ItemWithComments(item.getId(), item.getName(), item.getDescription(),
                    item.getAvailable(), last.getEnd(), comments);
        } else if (next == null) {
            return new ItemWithComments(item.getId(), item.getName(), item.getDescription(), item.getAvailable(),
                    comments);
        }


        return ItemMapper.itemToItemWithComments(item, last, next, comments);
    }

    @Override
    public List<Item> getItemByOwner(Long ownerId) {
        return itemStorage.findAllByOwner_Id(ownerId);
    }

    @Override
    public List<Item> getItemBySearch(String text) {

        if (!StringUtils.hasText(text)) {
            return List.of();
        } else {
            return itemStorage.getItemBySearch(text);
        }
    }

    @Override
    public boolean existById(Long id) {
        return itemStorage.existById(id);
    }

    @Override
    public boolean isAvailable(Long id) {
        Item item = itemStorage.findById(id).orElseThrow();
        return item.getAvailable();
    }


    @Override
    public Optional<Item> findItemById(Long itemId) {
        return itemStorage.findById(itemId);
    }

    @Override
    public Comment postComment(Comment comment) throws ValidationException, NotFoundException {
        Booking booking = bookingStorage.findOneByBooker_IdAndItemEntity_Id(comment.getAuthor().getId(), comment.getItem().getId());
        if (booking != null && booking.getEnd().isBefore(LocalDateTime.now())) {
            User author = userService.getUserById(comment.getAuthor().getId());
            comment.setAuthor(author);
            return commentStorage.save(comment);

        } else {
            throw new ValidationException("user didn't booked this item");
        }

    }

}
