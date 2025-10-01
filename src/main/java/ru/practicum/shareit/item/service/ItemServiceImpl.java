package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.practicum.shareit.excaption.NotFoundException;
import ru.practicum.shareit.excaption.ValidationException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.storage.ItemStorage;
import ru.practicum.shareit.user.storage.UserStorage;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemStorage itemStorage;
    private final UserStorage userStorage;

    @Override
    public ItemDto postItem(ItemDto itemDto, Long ownerId) throws NotFoundException, ValidationException {
        if (ownerId == null || !userStorage.isExistUserById(ownerId)) {
            throw new NotFoundException("user not found");
        }
        if (!StringUtils.hasText(itemDto.getName())
                || !StringUtils.hasText(itemDto.getDescription())
                || itemDto.getAvailable() == null) {
            throw new ValidationException("data is empty");
        }
        return itemStorage.postItem(itemDto, ownerId);
    }

    @Override
    public ItemDto patchItem(ItemDto itemDto, Long ownerId, Long itemId) throws NotFoundException, ValidationException {
        if (ownerId == null || !userStorage.isExistUserById(ownerId)) {
            throw new NotFoundException("user not correct");
        }
        Item item = itemStorage.getItem(itemId).orElseThrow(() -> new NotFoundException("not found"));
        if (!item.getOwnerId().equals(ownerId)) {
            throw new ValidationException("owner id not correct");
        }
        itemDto.setId(itemId);
        return itemStorage.patchItem(itemDto);

    }

    @Override
    public Item getItem(Long itemId) throws NotFoundException {
        return itemStorage.getItem(itemId).orElseThrow(() -> new NotFoundException("not found"));
    }

    @Override
    public List<ItemDto> getItemByOwner(Long ownerId) {
        return itemStorage.getItemByOwner(ownerId);
    }

    @Override
    public List<ItemDto> getItemBySearch(String text) {

        if (!StringUtils.hasText(text)) {
            return List.of();
        } else {
            return itemStorage.getItemBySearch(text);
        }
    }
}
