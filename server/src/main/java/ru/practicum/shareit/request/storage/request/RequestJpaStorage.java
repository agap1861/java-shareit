package ru.practicum.shareit.request.storage.request;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.request.entity.RequestEntity;

import java.util.List;

public interface RequestJpaStorage extends JpaRepository<RequestEntity, Long> {
    List<RequestEntity> getAllByRequesterId(Long requesterId);
}
