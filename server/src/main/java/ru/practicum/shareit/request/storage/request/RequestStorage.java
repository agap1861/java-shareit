package ru.practicum.shareit.request.storage.request;

import ru.practicum.shareit.request.domain.Request;

import java.util.List;
import java.util.Optional;

public interface RequestStorage {

    Request save(Request request);

    List<Request> getAllRequestByUserId(Long requesterId);

    Optional<Request> getRequestById(Long requestId);

    List<Request> getAllRequests();

    boolean existsById(Long requestId);


}
