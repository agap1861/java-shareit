package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.storage.BookingStorage;
import ru.practicum.shareit.excaption.NotFoundException;
import ru.practicum.shareit.excaption.ValidationException;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.CommentResponse;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemWithComments;
import ru.practicum.shareit.item.mapper.CommentMapper;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.storage.CommentStorage;
import ru.practicum.shareit.item.storage.ItemStorage;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.storage.UserStorage;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemStorage itemStorage;
    private final UserStorage userStorage;
    private final BookingStorage bookingStorage;
    private final CommentStorage commentStorage;

    @Override
    public Item postItem(ItemDto itemDto, Long ownerId) throws NotFoundException, ValidationException {
        if (ownerId == null || !userStorage.existsById(ownerId)) {
            throw new NotFoundException("user not found");
        }
        if (!StringUtils.hasText(itemDto.getName())
                || !StringUtils.hasText(itemDto.getDescription())
                || itemDto.getAvailable() == null) {
            throw new ValidationException("data is empty");
        }
        return itemStorage.save(ItemMapper.itemDtoToItem(itemDto, ownerId));
    }

    @Override
    public ItemDto patchItem(ItemDto itemDto, Long ownerId, Long itemId) throws NotFoundException, ValidationException {
        if (ownerId == null || !userStorage.existsById(ownerId)) {
            throw new NotFoundException("user not correct");
        }
        Item item = itemStorage.findById(itemId).orElseThrow(() -> new NotFoundException("not found"));
        if (!item.getOwner().getId().equals(ownerId)) {
            throw new ValidationException("owner id not correct");
        }
        if (itemDto.getName() != null) {
            item.setName(itemDto.getName());
        }
        if (itemDto.getDescription() != null) {
            item.setDescription(itemDto.getDescription());
        }
        if (itemDto.getAvailable() != null) {
            item.setAvailable(itemDto.getAvailable());
        }
        itemStorage.save(item);
        return ItemMapper.itemToItemDto(item);
    }

    @Override
    public ItemWithComments getItem(Long itemId, Long userId) throws NotFoundException {
        Item item = itemStorage.findById(itemId).orElseThrow(() -> new NotFoundException("not found"));
        Booking last = bookingStorage.findLastBooking(itemId);
        Booking next = bookingStorage.findNextBooking(itemId);
        List<String> comments = commentStorage.findAllByItem_Id(itemId).stream()
                .map(Comment::getText)
                .toList();

        if (comments.isEmpty()) {
            return new ItemWithComments(item.getId(), item.getName(), item.getDescription(), item.getAvailable());
        }
        if (next == null && item.getOwner().getId().equals(userId)) {
            return new ItemWithComments(item.getId(), item.getName(), item.getDescription(),
                    item.getAvailable(), last.getEndDate(), comments);
        } else if (next == null) {
            return new ItemWithComments(item.getId(), item.getName(), item.getDescription(), item.getAvailable(),
                    comments);
        }


        return ItemMapper.itemToItemWithComments(item, last, next, comments);
    }

    @Override
    public List<ItemDto> getItemByOwner(Long ownerId) {
        List<Item> items = itemStorage.findAllByOwner_Id(ownerId);
        return items.stream()
                .map(ItemMapper::itemToItemDto)
                .toList();
    }

    @Override
    public List<ItemDto> getItemBySearch(String text) {

        if (!StringUtils.hasText(text)) {
            return List.of();
        } else {
            List<Item> items = itemStorage.getItemBySearch(text);
            return items.stream()
                    .map(ItemMapper::itemToItemDto)
                    .toList();
        }
    }

    @Override
    public boolean existById(Long id) {
        return itemStorage.existsById(id);
    }

    @Override
    public boolean isAvailable(Long id) {
        Item item = itemStorage.findById(id).orElseThrow();
        return item.getAvailable();
    }


    @Override
    public Item findItem(Long itemId) throws NotFoundException {
        return itemStorage.findById(itemId).orElseThrow(() -> new NotFoundException("not found"));
    }

    @Override
    public CommentResponse postComment(CommentDto dto, Long userId, Long itemId) throws ValidationException, NotFoundException {
        Booking booking = bookingStorage.findOneByBooker_IdAndItem_Id(userId, itemId);
        if (booking != null && booking.getEndDate().isBefore(LocalDateTime.now())) {
            User author = userStorage.findById(userId).orElseThrow(() -> new NotFoundException("not found"));
            Comment comment = commentStorage.save(CommentMapper.commentDtoToComment(dto, author, itemId));
            return CommentMapper.commentToCommentResponse(comment);

        } else {
            throw new ValidationException("user didn't booked this item");
        }
    }

}
