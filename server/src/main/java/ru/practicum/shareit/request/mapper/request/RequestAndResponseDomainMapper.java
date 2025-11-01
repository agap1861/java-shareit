package ru.practicum.shareit.request.mapper.request;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.shareit.request.domain.Request;
import ru.practicum.shareit.request.dto.RequestWithResponseDto;
import ru.practicum.shareit.request.mapper.response.ResponseDomainToDtoMapper;

@Mapper(componentModel = "spring", uses = ResponseDomainToDtoMapper.class)
public interface RequestAndResponseDomainMapper {

    @Mapping(target = "items", source = "responses")
    RequestWithResponseDto domainToDto(Request request);


}
