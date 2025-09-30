package ru.practicum.shareit.item.mapper;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

public class ItemMapper {

    public static Item ItemDtoToItem(ItemDto itemDto, Long ownerId) {
        return new Item(itemDto.getId(), ownerId, itemDto.getName(), itemDto.getDescription(), itemDto.getAvailable());
    }

    public static ItemDto ItemToItemDto(Item item) {
        return new ItemDto(item.getId(), item.getName(), item.getDescription(), item.getAvailable());
    }
}
