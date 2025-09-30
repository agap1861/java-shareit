package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.practicum.shareit.excaption.DuplicateException;
import ru.practicum.shareit.excaption.NotFoundException;
import ru.practicum.shareit.excaption.ValidationException;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.storage.UserStorage;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserStorage userStorage;

    @Override
    public User postUser(UserDto userDto) {
        validateInputDataForPost(userDto);
        return userStorage.postUser(userDto);
    }

    @Override
    public User patchUser(UserDto userDto, Long userId) {
        validateInputDataForPatch(userDto, userId);
        return userStorage.patchUser(userDto, userId);

    }

    @Override
    public User getUserById(Long userId) {
        return userStorage.getUserById(userId).orElseThrow(() -> new NotFoundException("not found user"));
    }

    @Override
    public void deleteUserById(Long userId) {
        userStorage.deleteUserById(userId);

    }

    private void validateInputDataForPost(UserDto userDto) {
        if (!StringUtils.hasText(userDto.getName()) || !StringUtils.hasText(userDto.getEmail())) {
            throw new ValidationException("data is empty");
        }
        if (userStorage.isExistEmail(userDto.getEmail())) {
            throw new DuplicateException("email already exist");
        }
        if (!userDto.getEmail().contains("@")) {
            throw new ValidationException("email not correct");
        }

    }

    private void validateInputDataForPatch(UserDto userDto, Long userId) {
        if (!userStorage.isExistUserById(userId)) {
            throw new ValidationException("id not correct");
        }

        if (StringUtils.hasText(userDto.getEmail()) && !userDto.getEmail().contains("@")) {
            throw new ValidationException("email not correct");
        }
        if (StringUtils.hasText(userDto.getEmail()) && userStorage.isExistEmail(userDto.getEmail())) {
            throw new DuplicateException("email already exist");
        }
    }
}
