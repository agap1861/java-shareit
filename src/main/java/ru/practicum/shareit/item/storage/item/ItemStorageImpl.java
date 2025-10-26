package ru.practicum.shareit.item.storage.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.domian.Item;
import ru.practicum.shareit.item.mapper.item.ItemDomainEntityMapper;
import ru.practicum.shareit.item.model.ItemEntity;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ItemStorageImpl implements ItemStorage {
    private final ItemJpaStorage itemJpaStorage;
    private final ItemDomainEntityMapper itemDomainEntityMapper;

    @Override
    public Item save(Item item) {
        ItemEntity itemEntity = itemJpaStorage.save(itemDomainEntityMapper.domainToEntity(item));
        return itemDomainEntityMapper.entityToDomain(itemEntity);
    }

    @Override
    public Optional<Item> findById(Long itemId) {
        return itemJpaStorage.findById(itemId).map(itemDomainEntityMapper::entityToDomain);
    }

    @Override
    public void deleteById(Long itemId) {
        itemJpaStorage.deleteById(itemId);
    }

    @Override
    public boolean existById(Long itemId) {
        return itemJpaStorage.existsById(itemId);
    }

    @Override
    public List<Item> findAllByOwner_Id(Long ownerId) {
        return itemJpaStorage.findAllByOwner_Id(ownerId)
                .stream()
                .map(itemDomainEntityMapper::entityToDomain)
                .toList();
    }

    @Override
    public List<Item> getItemBySearch(String text) {
        return itemJpaStorage.getItemBySearch(text).stream()
                .map(itemDomainEntityMapper::entityToDomain)
                .toList();
    }
}
