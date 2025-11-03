package ru.practicum.client;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.client.RestTemplate;
import ru.practicum.exeption.ValidationException;
import ru.practicum.inputData.InRequest;
import ru.practicum.outputData.OutRequest;
import ru.practicum.outputData.RequestWithResponse;
import ru.practicum.validate.ValidateRequest;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RequestClient {
    private final ValidateRequest validateRequest;
    @Value("${shareit.server.url}")
    private String serverUrl;
    private static final String path = "/requests";
    private final RestTemplate restTemplate;

    public OutRequest postRequest(InRequest inRequest, Long userId) throws ValidationException {
        validateRequest.validateId(userId);
        validateRequest.validateForPost(inRequest);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("X-Sharer-User-Id", String.valueOf(userId));

        HttpEntity<InRequest> request = new HttpEntity<>(inRequest, httpHeaders);
        ResponseEntity<OutRequest> response = restTemplate.postForEntity(
                serverUrl + path,
                request,
                OutRequest.class
        );

        return response.getBody();
    }

    @GetMapping
    public List<RequestWithResponse> getAllRequests(Long userId) throws ValidationException {
        validateRequest.validateId(userId);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("X-Sharer-User-Id", String.valueOf(userId));
        HttpEntity<Void> request = new HttpEntity<>(httpHeaders);
        ResponseEntity<List<RequestWithResponse>> response = restTemplate.exchange(
                serverUrl + path,
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<List<RequestWithResponse>>() {
                }
        );
        return response.getBody();
    }

    public RequestWithResponse getRequestById(Long requestId) throws ValidationException {
        validateRequest.validateId(requestId);
        ResponseEntity<RequestWithResponse> response = restTemplate.exchange(
                serverUrl + path + "/" + requestId,
                HttpMethod.GET,
                null,
                RequestWithResponse.class
        );
        return response.getBody();
    }
}
