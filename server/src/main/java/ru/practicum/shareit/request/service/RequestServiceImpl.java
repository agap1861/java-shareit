package ru.practicum.shareit.request.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.domian.Item;

import ru.practicum.shareit.request.domain.Request;
import ru.practicum.shareit.request.domain.Response;
import ru.practicum.shareit.request.mapper.response.ResponseDomainItemMapper;
import ru.practicum.shareit.request.storage.request.RequestStorage;
import ru.practicum.shareit.request.storage.response.ResponseStorage;
import ru.practicum.shareit.user.service.UserService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {
    private final ResponseStorage responseStorage;
    private final RequestStorage requestStorage;
    private final UserService userService;
    private final ResponseDomainItemMapper responseDomainItemMapper;


    @Override
    public Request postRequest(Request request) throws NotFoundException {
        if (!userService.existById(request.getRequesterId())) {
            throw new NotFoundException("not found");
        }
        return requestStorage.save(request);
    }

    @Override
    public void postResponse(Item item) throws NotFoundException {
        if (!requestStorage.existsById(item.getRequestId())) {
            throw new NotFoundException("not found");
        }
        Response response = responseDomainItemMapper.itemToResponse(item);
        responseStorage.save(response);
    }

    @Override
    public List<Request> getRequestsByUserId(Long userId) {

        return requestStorage.getAllRequestByUserId(userId);
    }

    @Override
    public Request getRequestById(Long requestId) throws NotFoundException {
        return requestStorage.getRequestById(requestId).orElseThrow(() -> new NotFoundException("not found"));

    }


}
