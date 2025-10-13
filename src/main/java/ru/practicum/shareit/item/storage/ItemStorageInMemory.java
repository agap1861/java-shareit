package ru.practicum.shareit.item.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class ItemStorageInMemory implements ItemStorage {
    private HashMap<Long, Item> items = new HashMap<>();


    @Override
    public ItemDto postItem(ItemDto itemDto, Long ownerId) {
        Long id = items.keySet().stream()
                .max(Long::compareTo)
                .orElse(0L);
        id++;
        itemDto.setId(id);
        items.put(id, ItemMapper.itemDtoToItem(itemDto, ownerId));
        return itemDto;
    }

    @Override
    public ItemDto patchItem(ItemDto itemDto) {
        Item item = items.get(itemDto.getId());
        if (itemDto.getName() != null) {
            item.setName(itemDto.getName());
        } else if (itemDto.getDescription() != null) {
            item.setDescription(itemDto.getDescription());

        } else if (itemDto.getAvailable() != null) {
            item.setAvailable(itemDto.getAvailable());
        }
        items.replace(item.getId(), item);
        return itemDto;
    }

    @Override
    public Optional<Item> getItem(Long itemId) {
        Item item = items.get(itemId);
        return Optional.of(item);
    }

    @Override
    public List<ItemDto> getItemByOwner(Long ownerId) {
        return items.values().stream()
                .filter(item -> item.getOwnerId().equals(ownerId))
                .map(ItemMapper::itemToItemDto)
                .toList();
    }

    @Override
    public List<ItemDto> getItemBySearch(String text) {
        String lowerText = text.toLowerCase();

        return items.values().stream()
                .filter(item -> ((item.getName().toLowerCase().contains(lowerText)
                        || item.getDescription().toLowerCase().contains(lowerText))
                        && item.getAvailable() == true))
                .map(ItemMapper::itemToItemDto)
                .toList();
    }
}
