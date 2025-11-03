package ru.practicum.shareit.user.mapper;

import org.mapstruct.Mapper;
import ru.practicum.shareit.user.domain.User;
import ru.practicum.shareit.user.dto.UserDto;

@Mapper(componentModel = "spring")
public interface UserDomainDtoMapper {
    User dtoToDomain(UserDto userDto);

    UserDto domainToDto(User user);

}
