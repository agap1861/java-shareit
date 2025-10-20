package ru.practicum.shareit.item.mapper;

import lombok.RequiredArgsConstructor;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemWithComments;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;

import java.util.List;

@RequiredArgsConstructor
public class ItemMapper {

    public static Item itemDtoToItem(ItemDto itemDto, Long ownerId) {
        return new Item(new User(ownerId), itemDto.getName(), itemDto.getDescription(), itemDto.getAvailable());
    }

    public static ItemDto itemToItemDto(Item item) {
        return new ItemDto(item.getId(), item.getName(), item.getDescription(), item.getAvailable());
    }

    public static ItemWithComments itemToItemWithComments(Item item, Booking last, Booking next, List<String> comments) {
        return new ItemWithComments(item.getId(), item.getName(), item.getDescription(), item.getAvailable(),
                last.getEndDate(), next.getStartDate(), comments);
    }
}
