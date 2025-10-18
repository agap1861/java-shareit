package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import ru.practicum.shareit.excaption.DuplicateException;
import ru.practicum.shareit.excaption.NotFoundException;
import ru.practicum.shareit.excaption.ValidationException;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.storage.UserStorage;


@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserStorage userStorage;


    @Override
    public User postUser(UserDto userDto) throws DuplicateException, ValidationException {

        validateInputDataForPost(userDto);
        return userStorage.save(UserMapper.userDtoToUser(userDto));
    }

    @Override
    public User patchUser(UserDto userDto, Long userId) throws DuplicateException, ValidationException {
        validateInputDataForPatch(userDto, userId);
        return userStorage.save(UserMapper.userDtoToUser(userDto, userId));
    }

    @Override
    public User getUserById(Long userId) throws NotFoundException {
        return userStorage.findById(userId).orElseThrow(() -> new NotFoundException("not found user"));
    }

    @Override
    public void deleteUserById(Long userId) {
        userStorage.deleteById(userId);
    }

    @Override
    public boolean existById(Long id) {
        return userStorage.existsById(id);
    }

    private void validateInputDataForPost(UserDto userDto) throws DuplicateException, ValidationException {
        if (!StringUtils.hasText(userDto.getName()) || !StringUtils.hasText(userDto.getEmail())) {
            throw new ValidationException("data is empty");
        }
        if (userStorage.existsByEmail(userDto.getEmail())) {

            throw new DuplicateException("email already exist");
        }
        if (!userDto.getEmail().contains("@")) {
            throw new ValidationException("email not correct");
        }

    }

    private void validateInputDataForPatch(UserDto userDto, Long userId) throws DuplicateException, ValidationException {
        if (!userStorage.existsById(userId)) {
            throw new ValidationException("id not correct");
        }

        if (StringUtils.hasText(userDto.getEmail()) && !userDto.getEmail().contains("@")) {
            throw new ValidationException("email not correct");
        }
        if (StringUtils.hasText(userDto.getEmail()) && userStorage.existsByEmail(userDto.getEmail())) {
            throw new DuplicateException("email already exist");
        }
    }
}
