package ru.practicum.shareit.item.storage.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.domian.Comment;
import ru.practicum.shareit.item.mapper.comment.CommentDomainEntityMapper;
import ru.practicum.shareit.item.model.CommentEntity;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentStorageImpl implements CommentStorage {
    private final CommentJpaStorage commentJpaStorage;
    private final CommentDomainEntityMapper commentDomainEntityMapper;

    @Override
    public List<Comment> findAllByItemById(Long itemId) {
        List<CommentEntity> comments = commentJpaStorage.findAllByItem_Id(itemId);
        return comments.stream().map(commentDomainEntityMapper::entityToDomain).toList();
    }

    @Override
    public Comment save(Comment comment) {
        CommentEntity commentEntity = commentJpaStorage.save(commentDomainEntityMapper.domainToEntity(comment));
        return commentDomainEntityMapper.entityToDomain(commentEntity);
    }
}
