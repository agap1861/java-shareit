package ru.practicum.shareit.user.storage;

import ru.practicum.shareit.item.domian.Item;
import ru.practicum.shareit.user.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserStorage {

    User save(User user);

    Optional<User> findById(Long userId);

    void deleteById(Long userId);

    boolean existById(Long userId);

    boolean existsByEmail(String email);
}
