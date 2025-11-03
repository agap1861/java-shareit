package ru.practicum.shareit.item.service;

import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.domian.Comment;
import ru.practicum.shareit.item.domian.Item;
import ru.practicum.shareit.item.dto.item.ItemWithComments;

import java.util.List;
import java.util.Optional;

public interface ItemService {

    Item postItem(Item item) throws NotFoundException, ValidationException;

    Item patchItem(Item item) throws NotFoundException, ValidationException;

    ItemWithComments getItem(Long itemId, Long userId) throws NotFoundException;

    List<Item> getItemByOwner(Long ownerId);

    List<Item> getItemBySearch(String text);

    boolean existById(Long id);

    boolean isAvailable(Long id);


    Optional<Item> findItemById(Long itemId);

    Comment postComment(Comment comment) throws ValidationException, NotFoundException;


}
