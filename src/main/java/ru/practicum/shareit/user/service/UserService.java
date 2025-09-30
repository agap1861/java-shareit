package ru.practicum.shareit.user.service;

import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.dto.UserDto;

public interface UserService {
    User postUser(UserDto userDto);

    User patchUser(UserDto userDto, Long userId);

    User getUserById(Long userId);

    void deleteUserById(Long userId);
}
