package ru.practicum.shareit.item.storage.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.item.model.CommentEntity;

import java.util.List;

public interface CommentJpaStorage extends JpaRepository<CommentEntity, Long> {

    List<CommentEntity> findAllByItem_Id(Long itemId);
}
