package ru.practicum.shareit.user.storage;

import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.dto.UserDto;

import java.util.Optional;

public interface UserStorage {
    User postUser(UserDto userDto);

    User patchUser(UserDto userDto, Long userId);

    Optional<User> getUserById(Long userid);

    void deleteUserById(Long userId);

    boolean isExistEmail(String email);

    boolean isExistUserById(Long userid);

}
