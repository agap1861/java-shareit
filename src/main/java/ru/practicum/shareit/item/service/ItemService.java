package ru.practicum.shareit.item.service;

import ru.practicum.shareit.excaption.NotFoundException;
import ru.practicum.shareit.excaption.ValidationException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemService {

    ItemDto postItem(ItemDto itemDto, Long ownerId) throws NotFoundException, ValidationException;

    ItemDto patchItem(ItemDto itemDto, Long ownerId, Long itemId) throws NotFoundException, ValidationException;

    Item getItem(Long itemId) throws NotFoundException;

    List<ItemDto> getItemByOwner(Long ownerId);

    List<ItemDto> getItemBySearch(String text);

}
