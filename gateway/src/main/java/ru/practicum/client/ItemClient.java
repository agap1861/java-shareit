package ru.practicum.client;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.practicum.exeption.ValidationException;
import ru.practicum.inputData.InComment;
import ru.practicum.inputData.InItem;
import ru.practicum.outputData.ItemWithComment;
import ru.practicum.outputData.OutComment;
import ru.practicum.outputData.OutItem;
import ru.practicum.validate.ValidateItem;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemClient {
    private final ValidateItem validateItem;
    private final RestTemplate restTemplate;
    @Value("${shareit.server.url}")
    private String serverUrl;
    private static final String path = "/items";

    public OutItem postItem(Long ownerId, InItem inItem) throws ValidationException {
        validateItem.validateDataForPost(inItem);
        validateItem.validateId(ownerId);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("X-Sharer-User-Id", String.valueOf(ownerId));
        HttpEntity<InItem> request = new HttpEntity<>(inItem, httpHeaders);
        ResponseEntity<OutItem> item = restTemplate
                .postForEntity(serverUrl + path, request, OutItem.class);
        return item.getBody();

    }

    public OutItem patchItem(Long ownerId,
                             Long itemId, InItem inItem) throws ValidationException {
        validateItem.validateId(itemId);
        validateItem.validateId(ownerId);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("X-Sharer-User-Id", String.valueOf(ownerId));
        HttpEntity<InItem> request = new HttpEntity<>(inItem, httpHeaders);
        ResponseEntity<OutItem> response = restTemplate.exchange(
                serverUrl + path + "/" + itemId,
                HttpMethod.PATCH,
                request,
                OutItem.class
        );
        return response.getBody();
    }

    public ItemWithComment getItem(Long itemId, Long userId) throws ValidationException {
        validateItem.validateId(itemId);
        validateItem.validateId(userId);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("X-Sharer-User-Id", String.valueOf(userId));
        HttpEntity<Void> request = new HttpEntity<>(httpHeaders);
        ResponseEntity<ItemWithComment> response = restTemplate.exchange(
                serverUrl + path + "/" + itemId,
                HttpMethod.GET,
                request,
                ItemWithComment.class
        );
        return response.getBody();
    }

    public List<OutItem> getItemByOwner(Long ownerId) throws ValidationException {
        validateItem.validateId(ownerId);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("X-Sharer-User-Id", String.valueOf(ownerId));
        HttpEntity<Void> request = new HttpEntity<>(httpHeaders);
        ResponseEntity<List<OutItem>> response = restTemplate.exchange(
                serverUrl + path,
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<List<OutItem>>() {
                }
        );
        return response.getBody();
    }

    public List<OutItem> getItemBySearch(String text) throws ValidationException {
        validateItem.validateText(text);
        ResponseEntity<List<OutItem>> response = restTemplate.exchange(
                serverUrl + path + "/search?text={text}",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<OutItem>>() {
                },
                text
        );
        return response.getBody();
    }

    public OutComment postComment(Long itemId, Long userId, InComment comment) throws ValidationException {
        validateItem.validateInputDataForComment(itemId, userId, comment);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("X-Sharer-User-Id", String.valueOf(userId));
        HttpEntity<InComment> request = new HttpEntity<>(comment, httpHeaders);
        ResponseEntity<OutComment> response = restTemplate.postForEntity(
                serverUrl + path + "/" + itemId + "/comment",
                request,
                OutComment.class
        );
        return response.getBody();
    }


}
