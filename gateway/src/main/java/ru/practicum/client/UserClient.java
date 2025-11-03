package ru.practicum.client;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.springframework.web.client.RestTemplate;
import ru.practicum.exeption.ValidationException;
import ru.practicum.inputData.InUser;
import ru.practicum.outputData.OutUser;
import ru.practicum.validate.ValidateUser;

@Service
@RequiredArgsConstructor
public class UserClient {
    private final ValidateUser validateUser;
    private final RestTemplate restTemplate;

    @Value("${shareit.server.url}")
    private String serverUrl;
    private static final String path = "/users";

    public OutUser postUser(InUser inUser) throws ValidationException {
        validateUser.validateInputDataForPost(inUser);
        HttpEntity<InUser> request = new HttpEntity<>(inUser);
        ResponseEntity<OutUser> user = restTemplate
                .postForEntity(serverUrl + path, request, OutUser.class);
        return user.getBody();
    }

    public OutUser patchUser(InUser inUser, Long userId) throws ValidationException {
        validateUser.validateInputDataForPatch(inUser, userId);
        HttpEntity<InUser> request = new HttpEntity<>(inUser);
        ResponseEntity<OutUser> response = restTemplate.exchange(
                serverUrl + path + "/" + userId,
                HttpMethod.PATCH,
                request,
                OutUser.class
        );
        return response.getBody();

    }

    public OutUser getUserById(Long userId) throws ValidationException {
        validateUser.validateId(userId);
        ResponseEntity<OutUser> response = restTemplate.exchange(
                serverUrl + path + "/" + userId,
                HttpMethod.GET,
                null,
                OutUser.class
        );
        return response.getBody();
    }

    public void deleteUserById(Long userId) throws ValidationException {
        validateUser.validateId(userId);
        restTemplate.delete(serverUrl + path + "/" + userId);
    }
}
