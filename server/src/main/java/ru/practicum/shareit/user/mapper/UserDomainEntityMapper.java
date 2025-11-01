package ru.practicum.shareit.user.mapper;


import org.mapstruct.Mapper;
import ru.practicum.shareit.user.domain.User;
import ru.practicum.shareit.user.entity.UserEntity;

@Mapper(componentModel = "spring")
public interface UserDomainEntityMapper {
    UserEntity domainToEntity(User user);

    User entityToDomain(UserEntity user);
}
