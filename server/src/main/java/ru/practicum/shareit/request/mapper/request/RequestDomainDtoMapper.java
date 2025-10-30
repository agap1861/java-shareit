package ru.practicum.shareit.request.mapper.request;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.shareit.request.domain.Request;
import ru.practicum.shareit.request.dto.RequestCreateDto;
import ru.practicum.shareit.request.dto.RequestDto;
import ru.practicum.shareit.request.dto.RequestWithResponseDto;

import java.time.Instant;

@Mapper(componentModel = "spring" ,imports = Instant.class)
public interface RequestDomainDtoMapper {
    @Mapping(target = "created",expression = "java(Instant.now())")
    Request dtoToDomain(RequestCreateDto requestCreateDto);

    RequestDto domainToDto(Request request);


}
