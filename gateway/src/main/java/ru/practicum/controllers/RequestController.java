package ru.practicum.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.practicum.exeption.ValidationException;
import ru.practicum.inputData.InRequest;
import ru.practicum.outputData.OutRequest;
import ru.practicum.outputData.RequestWithResponse;
import ru.practicum.validate.ValidateRequest;

import java.util.List;

@RestController
@RequestMapping("/requests")
@RequiredArgsConstructor
public class RequestController {
    private final ValidateRequest validateRequest;
    private final static String SERVER = "http://localhost:9090/requests";
    private final RestTemplate restTemplate;

    @PostMapping
    public OutRequest postRequest(@RequestBody InRequest inRequest, @RequestHeader("X-Sharer-User-Id") Long userId) throws ValidationException {
        validateRequest.validateId(userId);
        validateRequest.validateForPost(inRequest);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("X-Sharer-User-Id", String.valueOf(userId));

        HttpEntity<InRequest> request = new HttpEntity<>(inRequest, httpHeaders);
        ResponseEntity<OutRequest> response = restTemplate.postForEntity(
                SERVER,
                request,
                OutRequest.class
        );

        return response.getBody();
    }

    @GetMapping
    public List<RequestWithResponse> getAllRequests(@RequestHeader("X-Sharer-User-Id") Long userId) throws ValidationException {
        validateRequest.validateId(userId);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("X-Sharer-User-Id", String.valueOf(userId));
        HttpEntity<Void> request = new HttpEntity<>(httpHeaders);
        ResponseEntity<List<RequestWithResponse>> response = restTemplate.exchange(
                SERVER,
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<List<RequestWithResponse>>() {
                }
        );
        return response.getBody();
    }

    @GetMapping("/{requestId}")
    public RequestWithResponse getRequestById(@PathVariable Long requestId) throws ValidationException {
        validateRequest.validateId(requestId);
        ResponseEntity<RequestWithResponse> response = restTemplate.exchange(
                SERVER + "/" + requestId,
                HttpMethod.GET,
                null,
                RequestWithResponse.class
        );
        return response.getBody();
    }


}
