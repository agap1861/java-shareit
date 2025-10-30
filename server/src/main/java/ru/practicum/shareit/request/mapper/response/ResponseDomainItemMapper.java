package ru.practicum.shareit.request.mapper.response;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.shareit.item.domian.Item;
import ru.practicum.shareit.request.domain.Response;

@Mapper(componentModel = "spring")
public interface ResponseDomainItemMapper {

    @Mapping(target = "itemId",source = "id")
    @Mapping(target = "id",ignore = true)
    @Mapping(target ="itemName",source = "name")
    @Mapping(target = "requestId",source = "requestId")
    Response itemToResponse(Item item);
}
