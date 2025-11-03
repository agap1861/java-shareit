package ru.practicum.shareit.item.storage.comment;

import ru.practicum.shareit.item.domian.Comment;

import java.util.List;

public interface CommentStorage {

    List<Comment> findAllByItemById(Long itemId);

    Comment save(Comment comment);
}
