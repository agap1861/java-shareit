package ru.practicum.shareit.user.mapper;

import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.entity.UserEntity;

public class UserMapper {


    public static UserEntity userDtoToUser(UserDto userDto) {
        return new UserEntity(userDto.getName(), userDto.getEmail());
    }

    public static UserEntity userDtoToUser(UserDto userDto, Long id) {
        return new UserEntity(id, userDto.getName(), userDto.getEmail());
    }

}
