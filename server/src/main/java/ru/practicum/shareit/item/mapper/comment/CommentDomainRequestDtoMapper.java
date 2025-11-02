package ru.practicum.shareit.item.mapper.comment;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.shareit.item.domian.Comment;
import ru.practicum.shareit.item.dto.comment.CommentRequestDto;

import java.time.Instant;

@Mapper(componentModel = "spring", imports = Instant.class)
public interface CommentDomainRequestDtoMapper {

    @Mapping(target = "author.id", source = "userId")
    @Mapping(target = "item.id", source = "dto.itemId")
    @Mapping(target = "created", expression = "java(Instant.now())")
    Comment dtoToDomain(CommentRequestDto dto);

    CommentRequestDto domainToDto(Comment comment);
}
