package ru.practicum.shareit.user.service;

import ru.practicum.shareit.excaption.DuplicateException;
import ru.practicum.shareit.excaption.NotFoundException;
import ru.practicum.shareit.excaption.ValidationException;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.dto.UserDto;

public interface UserService {
    User postUser(UserDto userDto) throws DuplicateException, ValidationException;

    User patchUser(UserDto userDto, Long userId) throws DuplicateException, ValidationException;

    User getUserById(Long userId) throws NotFoundException;

    void deleteUserById(Long userId);

    boolean existById(Long id);
}
