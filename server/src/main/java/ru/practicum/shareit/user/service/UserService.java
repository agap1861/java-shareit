package ru.practicum.shareit.user.service;

import ru.practicum.shareit.exception.DuplicateException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.user.domain.User;

public interface UserService {
    User postUser(User user) throws DuplicateException, ValidationException;

    User patchUser(User user) throws DuplicateException, ValidationException;

    User getUserById(Long userId) throws NotFoundException;

    void deleteUserById(Long userId);

    boolean existById(Long id);
}
