package ru.practicum.shareit.item.service;

import ru.practicum.shareit.excaption.NotFoundException;
import ru.practicum.shareit.excaption.ValidationException;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.CommentResponse;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemWithComments;

import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemService {

    Item postItem(ItemDto itemDto, Long ownerId) throws NotFoundException, ValidationException;

    ItemDto patchItem(ItemDto itemDto, Long ownerId, Long itemId) throws NotFoundException, ValidationException;

    ItemWithComments getItem(Long itemId, Long userId) throws NotFoundException;

    List<ItemDto> getItemByOwner(Long ownerId);

    List<ItemDto> getItemBySearch(String text);

    boolean existById(Long id);

    boolean isAvailable(Long id);


    Item findItem(Long itemId) throws NotFoundException;

    CommentResponse postComment(CommentDto dto, Long userId, Long ItemId) throws ValidationException, NotFoundException;


}
