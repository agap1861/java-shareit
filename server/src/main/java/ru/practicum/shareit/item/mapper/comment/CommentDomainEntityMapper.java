package ru.practicum.shareit.item.mapper.comment;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.shareit.item.domian.Comment;
import ru.practicum.shareit.item.model.CommentEntity;

@Mapper(componentModel = "spring")
public interface CommentDomainEntityMapper {

    Comment entityToDomain(CommentEntity commentEntity);

    @Mapping(target = "item",source = "item")
    @Mapping(target = "author",source = "author")
    CommentEntity domainToEntity(Comment comment);

}
