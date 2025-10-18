package ru.practicum.shareit.item.mapper;

import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.CommentResponse;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;

public class CommentMapper {

    public static Comment commentDtoToComment(CommentDto dto, User author, Long itemId) {
        return new Comment(dto.getText(), new Item(itemId), author);
    }

    public static CommentResponse commentToCommentResponse(Comment comment) {
        return new CommentResponse(comment.getId(), comment.getText(), comment.getAuthor().getName(),
                comment.getCreated());
    }
}
