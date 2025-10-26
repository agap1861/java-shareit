package ru.practicum.shareit.request.mapper;

import org.mapstruct.Mapper;
import ru.practicum.shareit.request.domain.Request;
import ru.practicum.shareit.request.dto.RequestResponseDto;

@Mapper
public interface RequestDomainResponseDtoMapper {

    Request dtoToDomain(RequestResponseDto requestResponseDto);

    RequestResponseDto domainToDto(Request request);


}
