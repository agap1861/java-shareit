package ru.practicum.shareit.request.mapper.request;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.shareit.request.domain.Request;
import ru.practicum.shareit.request.entity.RequestEntity;
import ru.practicum.shareit.request.mapper.response.ResponseDomainEntityMapper;

@Mapper(componentModel = "spring",uses = ResponseDomainEntityMapper.class)
public interface RequestDomainEntityMapper {

    @Mapping(target = "requesterId",source = "request.requester.id")
    Request entityToDomain(RequestEntity request);
    @Mapping(target = "requester.id",source = "requesterId")
    RequestEntity domainToEntity(Request request);

}
