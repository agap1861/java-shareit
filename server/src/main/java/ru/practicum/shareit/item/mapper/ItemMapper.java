package ru.practicum.shareit.item.mapper;

import lombok.RequiredArgsConstructor;
import ru.practicum.shareit.booking.domain.Booking;
import ru.practicum.shareit.item.domian.Item;

import ru.practicum.shareit.item.dto.item.ItemWithComments;


import java.util.List;

@RequiredArgsConstructor
public class ItemMapper {

    public static ItemWithComments itemToItemWithComments(Item item, Booking last, Booking next, List<String> comments) {
        return new ItemWithComments(item.getId(), item.getName(), item.getDescription(), item.getAvailable(),
                last.getEnd(), next.getStart(), comments);
    }
}
