package ru.practicum.shareit.request.storage.response;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.request.entity.ResponseEntity;

public interface ResponseJpaStorage extends JpaRepository<ResponseEntity,Long> {
}
