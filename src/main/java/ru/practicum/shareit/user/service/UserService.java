package ru.practicum.shareit.user.service;

import ru.practicum.shareit.excaption.DuplicateException;
import ru.practicum.shareit.excaption.NotFoundException;
import ru.practicum.shareit.excaption.ValidationException;
import ru.practicum.shareit.user.domain.User;

public interface UserService {
    User postUser(User user) throws DuplicateException, ValidationException;

    User patchUser(User user) throws DuplicateException, ValidationException;

    User getUserById(Long userId) throws NotFoundException;

    void deleteUserById(Long userId);

    boolean existById(Long id);
}
