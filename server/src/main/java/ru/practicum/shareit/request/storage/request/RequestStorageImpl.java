package ru.practicum.shareit.request.storage.request;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.request.domain.Request;
import ru.practicum.shareit.request.entity.RequestEntity;
import ru.practicum.shareit.request.mapper.request.RequestDomainEntityMapper;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RequestStorageImpl implements RequestStorage {
    private final RequestJpaStorage requestJpaStorage;
    private final RequestDomainEntityMapper requestDomainEntityMapper;

    @Override
    public Request save(Request request) {
        RequestEntity requestEntity = requestJpaStorage.save(requestDomainEntityMapper.domainToEntity(request));

        return requestDomainEntityMapper.entityToDomain(requestEntity);
    }

    @Override
    public List<Request> getAllRequestByUserId(Long requesterId) {
        List<RequestEntity> requestEntities = requestJpaStorage.getAllByRequesterId(requesterId);
        return requestEntities.stream().map(requestDomainEntityMapper::entityToDomain).toList();
    }

    @Override
    public Optional<Request> getRequestById(Long requestId) {
        return  requestJpaStorage.findById(requestId).map(requestDomainEntityMapper::entityToDomain);
    }

    @Override
    public List<Request> getAllRequests() {
        return requestJpaStorage.findAll().stream().map(requestDomainEntityMapper::entityToDomain).toList();
    }

    @Override
    public boolean existsById(Long requestId) {
        return requestJpaStorage.existsById(requestId);
    }
}
