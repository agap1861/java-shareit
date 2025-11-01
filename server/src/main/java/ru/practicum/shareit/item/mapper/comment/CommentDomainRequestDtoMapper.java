package ru.practicum.shareit.item.mapper.comment;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.shareit.item.domian.Comment;
import ru.practicum.shareit.item.dto.comment.CommentRequestDto;

@Mapper(componentModel = "spring")
public interface CommentDomainRequestDtoMapper {

    @Mapping(target = "author.id", source = "userId")
    @Mapping(target = "item.id", source = "dto.itemId")
    Comment dtoToDomain(CommentRequestDto dto);

    CommentRequestDto domainToDto(Comment comment);
}
