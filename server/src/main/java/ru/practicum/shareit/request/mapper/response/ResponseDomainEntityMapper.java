package ru.practicum.shareit.request.mapper.response;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.shareit.request.domain.Response;
import ru.practicum.shareit.request.entity.ResponseEntity;

@Mapper(componentModel = "spring")
public interface ResponseDomainEntityMapper {

    @Mapping(target = "requestId", source = "request.id")
    @Mapping(target = "itemId", source = "item.id")
    @Mapping(target = "itemName", source = "item.name")
    @Mapping(target = "ownerId", source = "owner.id")
    Response entityToDomain(ResponseEntity response);

    @Mapping(target = "request.id", source = "requestId")
    @Mapping(target = "item.id", source = "itemId")
    @Mapping(target = "item.name", source = "itemName")
    @Mapping(target = "owner.id", source = "ownerId")
    ResponseEntity domainToEntity(Response response);
}
