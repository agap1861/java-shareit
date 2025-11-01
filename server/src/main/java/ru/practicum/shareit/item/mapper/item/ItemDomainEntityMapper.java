package ru.practicum.shareit.item.mapper.item;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.shareit.item.domian.Item;
import ru.practicum.shareit.item.model.ItemEntity;

@Mapper(componentModel = "spring")
public interface ItemDomainEntityMapper {
    @Mapping(target = "owner.id", source = "item.ownerId")
    ItemEntity domainToEntity(Item item);

    @Mapping(target = "ownerId", source = "itemEntity.owner.id")
    Item entityToDomain(ItemEntity itemEntity);

}
