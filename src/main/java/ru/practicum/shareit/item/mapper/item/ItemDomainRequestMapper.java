package ru.practicum.shareit.item.mapper.item;

import org.mapstruct.Mapper;
import ru.practicum.shareit.item.domian.Item;
import ru.practicum.shareit.item.dto.item.ItemRequestDto;

@Mapper(componentModel = "spring")
public interface ItemDomainRequestMapper {
    ItemRequestDto dtoToDomain(Item item);

    Item domainToDto(ItemRequestDto dto);
}
