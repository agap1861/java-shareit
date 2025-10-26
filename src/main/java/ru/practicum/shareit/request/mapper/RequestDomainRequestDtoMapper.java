package ru.practicum.shareit.request.mapper;

import org.mapstruct.Mapper;
import ru.practicum.shareit.request.domain.Request;
import ru.practicum.shareit.request.dto.RequestRequestDto;

@Mapper
public interface RequestDomainRequestDtoMapper {

    Request domainToDto(RequestRequestDto requestRequestDto);

    RequestRequestDto domainToDto(Request request);
}
