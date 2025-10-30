package ru.practicum.shareit.request.mapper.response;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.shareit.request.domain.Response;
import ru.practicum.shareit.request.dto.ResponseDto;

@Mapper(componentModel = "spring")
public interface ResponseDomainToDtoMapper {

    @Mapping(target = "name",source = "itemName")
    @Mapping(target = "id",source = "itemId")
    ResponseDto domainToDto(Response response);
}
