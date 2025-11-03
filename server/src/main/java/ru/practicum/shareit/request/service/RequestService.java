package ru.practicum.shareit.request.service;

import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.domian.Item;
import ru.practicum.shareit.request.domain.Request;

import java.util.List;

public interface RequestService {

    Request postRequest(Request request) throws NotFoundException;

    void postResponse(Item item) throws NotFoundException;

    List<Request> getRequestsByUserId(Long userId);

    Request getRequestById(Long requestId) throws NotFoundException;


}
