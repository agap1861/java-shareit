package ru.practicum.shareit.request.storage.response;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.request.domain.Response;
import ru.practicum.shareit.request.entity.ResponseEntity;
import ru.practicum.shareit.request.mapper.response.ResponseDomainEntityMapper;

@Repository
@RequiredArgsConstructor
public class ResponseStorageImpl implements ResponseStorage {
    private final ResponseJpaStorage responseJpaStorage;
    private final ResponseDomainEntityMapper responseDomainEntityMapper;

    @Override
    public Response save(Response response) {
        ResponseEntity entity = responseJpaStorage.save(responseDomainEntityMapper.domainToEntity(response));
        return responseDomainEntityMapper.entityToDomain(entity);
    }
}
