package ru.practicum.shareit.user.storage;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.user.domain.User;
import ru.practicum.shareit.user.entity.UserEntity;
import ru.practicum.shareit.user.mapper.UserDomainEntityMapper;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserStorageImpl implements UserStorage {
    private final UserJpaRepository userJpaStorage;
    private final UserDomainEntityMapper userDomainEntityMapper;


    @Override
    public User save(User user) {
        UserEntity entity = userJpaStorage.save(userDomainEntityMapper.domainToEntity(user));
        return userDomainEntityMapper.entityToDomain(entity);
    }

    @Override
    public Optional<User> findById(Long userId) {
        return userJpaStorage.findById(userId)
                .map(userDomainEntityMapper::entityToDomain);

    }

    @Override
    public void deleteById(Long userId) {
        userJpaStorage.deleteById(userId);
    }

    @Override
    public boolean existById(Long userId) {
        return userJpaStorage.existsById(userId);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userJpaStorage.existsByEmail(email);
    }
}

