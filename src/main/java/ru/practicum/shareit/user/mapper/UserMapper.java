package ru.practicum.shareit.user.mapper;

import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.dto.UserDto;

public class UserMapper {


    public static User userDtoToUser(UserDto userDto) {
        return new User(userDto.getName(), userDto.getEmail());
    }

    public static User userDtoToUser(UserDto userDto, Long id) {
        return new User(id, userDto.getName(), userDto.getEmail());
    }

}
