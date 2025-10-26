package ru.practicum.shareit.item.mapper.comment;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.shareit.item.domian.Comment;
import ru.practicum.shareit.item.dto.comment.CommentResponseDto;

@Mapper(componentModel = "spring")
public interface CommentDomainResponseMapper {

    Comment dtoToDomain(CommentResponseDto dto);

    @Mapping(target = "authorName", source = "author.name")
    CommentResponseDto domainToDto(Comment comment);
}
