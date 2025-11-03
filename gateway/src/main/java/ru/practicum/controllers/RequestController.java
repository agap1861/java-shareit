package ru.practicum.controllers;


import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import ru.practicum.client.RequestClient;
import ru.practicum.exeption.ValidationException;
import ru.practicum.inputData.InRequest;
import ru.practicum.outputData.OutRequest;
import ru.practicum.outputData.RequestWithResponse;


import java.util.List;

@RestController
@RequestMapping("/requests")
@RequiredArgsConstructor
public class RequestController {
    private final RequestClient requestClient;

    @PostMapping
    public OutRequest postRequest(@RequestBody InRequest inRequest, @RequestHeader("X-Sharer-User-Id") Long userId) throws ValidationException {
        return requestClient.postRequest(inRequest, userId);
    }

    @GetMapping
    public List<RequestWithResponse> getAllRequests(@RequestHeader("X-Sharer-User-Id") Long userId) throws ValidationException {
        return requestClient.getAllRequests(userId);
    }

    @GetMapping("/{requestId}")
    public RequestWithResponse getRequestById(@PathVariable Long requestId) throws ValidationException {
        return requestClient.getRequestById(requestId);
    }


}
