package ru.practicum.shareit.item.storage;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.List;
import java.util.Optional;

public interface ItemStorage {
    ItemDto postItem(ItemDto itemDto, Long ownerId);

    ItemDto patchItem(ItemDto itemDto);

    Optional<Item> getItem(Long itemId);

    List<ItemDto> getItemByOwner(Long ownerId);

    List<ItemDto> getItemBySearch(String text);
}
