package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import ru.practicum.shareit.exception.DuplicateException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.user.domain.User;
import ru.practicum.shareit.user.storage.UserStorage;


@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserStorage userStorage;


    @Override
    public User postUser(User user) throws DuplicateException {

        validateInputDataForPost(user);
        return userStorage.save(user);
    }

    @Override
    public User patchUser(User user) throws DuplicateException, ValidationException {
        validateInputDataForPatch(user);
        return userStorage.save(user);
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
        return userStorage.existById(id);
    }

    private void validateInputDataForPost(User user) throws DuplicateException {
        if (userStorage.existsByEmail(user.getEmail())) {

            throw new DuplicateException("email already exist");
        }

    }

    private void validateInputDataForPatch(User user) throws DuplicateException, ValidationException {
        if (!userStorage.existById(user.getId())) {
            throw new ValidationException("id not correct");
        }
        if (StringUtils.hasText(user.getEmail()) && userStorage.existsByEmail(user.getEmail())) {
            throw new DuplicateException("email already exist");
        }
    }
}
