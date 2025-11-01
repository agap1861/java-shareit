package ru.practicum.shareit.item.mapper.item;

import org.mapstruct.Mapper;
import ru.practicum.shareit.item.domian.Item;
import ru.practicum.shareit.item.dto.item.ItemResponseDto;

@Mapper(componentModel = "spring")
public interface ItemDomainResponseDtoMapper {

    ItemResponseDto dtoToDomain(Item item);

    Item domainToDto(ItemResponseDto dto);


}
