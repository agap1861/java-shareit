package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemService {

    ItemDto postItem(ItemDto itemDto, Long ownerId);

    ItemDto patchItem(ItemDto itemDto, Long ownerId, Long itemId);

    Item getItem(Long itemId);

    List<ItemDto> getItemByOwner(Long ownerId);

    List<ItemDto> getItemBySearch(String text);

}
