package ru.practicum.shareit.item.storage.item;

import ru.practicum.shareit.item.domian.Item;

import java.util.List;
import java.util.Optional;

public interface ItemStorage {
    Item save(Item item);

    Optional<Item> findById(Long itemId);

    void deleteById(Long itemId);

    boolean existById(Long itemId);

    List<Item> findAllByOwner_Id(Long ownerId);

    List<Item> getItemBySearch(String text);
}
