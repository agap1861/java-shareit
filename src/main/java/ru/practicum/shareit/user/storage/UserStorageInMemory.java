package ru.practicum.shareit.user.storage;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.mapper.UserMapper;

import java.util.HashMap;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserStorageInMemory implements UserStorage {
    HashMap<Long, User> users = new HashMap<>();


    @Override
    public User postUser(UserDto userDto) {
        Long id = users.keySet().stream()
                .max(Long::compareTo)
                .orElse(0L);
        id++;
        User user = UserMapper.userDtoToUser(userDto, id);

        users.put(id, user);

        return user;
    }

    @Override
    public User patchUser(UserDto userDto, Long userId) {
        User user = getUserById(userId).get();
        if (userDto.getName() != null) {
            user.setName(userDto.getName());
        }
        if (userDto.getEmail() != null) {
            user.setEmail(userDto.getEmail());
        }
        users.replace(userId, user);
        return user;
    }

    @Override
    public Optional<User> getUserById(Long userid) {
        User user = users.get(userid);
        return Optional.of(user);

    }

    @Override
    public void deleteUserById(Long userId) {
        users.remove(userId);
    }

    @Override
    public boolean isExistEmail(String email) {

        boolean flag = users.values().stream()
                .map(User::getEmail)
                .anyMatch(userEmail -> userEmail.equals(email));
        return flag;
    }

    @Override
    public boolean isExistUserById(Long userid) {
        return users.keySet().stream().anyMatch(id -> id.equals(userid));
    }
}
