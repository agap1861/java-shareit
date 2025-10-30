package ru.practicum.shareit.request.service;

import ru.practicum.shareit.excaption.NotFoundException;
import ru.practicum.shareit.item.domian.Item;
import ru.practicum.shareit.request.domain.Request;
import ru.practicum.shareit.request.domain.Response;

import java.util.List;

public interface RequestService {

    Request postRequest(Request request);

    void postResponse(Item item) throws NotFoundException;

    List<Request> getRequestsByUserId(Long userId);

    Request getRequestById(Long requestId) throws NotFoundException;




}
