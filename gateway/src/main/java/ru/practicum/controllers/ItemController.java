package ru.practicum.controllers;


import lombok.RequiredArgsConstructor;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.practicum.exeption.ValidationException;
import ru.practicum.inputData.InComment;
import ru.practicum.inputData.InItem;
import ru.practicum.outputData.ItemWithComment;
import ru.practicum.outputData.OutComment;
import ru.practicum.outputData.OutItem;
import ru.practicum.validate.ValidateItem;


import java.util.List;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
    private final ValidateItem validateItem;
    private final RestTemplate restTemplate;
    private static final String SERVER = "http://localhost:9090/items";

    @PostMapping
    public OutItem postItem(@RequestHeader("X-Sharer-User-Id") Long ownerId, @RequestBody InItem inItem) throws ValidationException {
        validateItem.validateDataForPost(inItem);
        validateItem.validateId(ownerId);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("X-Sharer-User-Id", String.valueOf(ownerId));
        HttpEntity<InItem> request = new HttpEntity<>(inItem, httpHeaders);
        ResponseEntity<OutItem> item = restTemplate
                .postForEntity(SERVER, request, OutItem.class);
        return item.getBody();

    }

    @PatchMapping("/{itemId}")
    public OutItem patchItem(@RequestHeader("X-Sharer-User-Id") Long ownerId,
                             @PathVariable Long itemId, @RequestBody InItem inItem) throws ValidationException {
        validateItem.validateId(itemId);
        validateItem.validateId(ownerId);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("X-Sharer-User-Id", String.valueOf(ownerId));
        HttpEntity<InItem> request = new HttpEntity<>(inItem, httpHeaders);
        ResponseEntity<OutItem> response = restTemplate.exchange(
                SERVER + "/" + itemId,
                HttpMethod.PATCH,
                request,
                OutItem.class
        );
        return response.getBody();
    }

    @GetMapping("/{itemId}")
    public ItemWithComment getItem(@PathVariable Long itemId, @RequestHeader("X-Sharer-User-Id") Long userId) throws ValidationException {
        validateItem.validateId(itemId);
        validateItem.validateId(userId);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("X-Sharer-User-Id", String.valueOf(userId));
        HttpEntity<Void> request = new HttpEntity<>(httpHeaders);
        ResponseEntity<ItemWithComment> response = restTemplate.exchange(
                SERVER + "/" + itemId,
                HttpMethod.GET,
                request,
                ItemWithComment.class
        );
        return response.getBody();
    }

    @GetMapping
    public List<OutItem> getItemByOwner(@RequestHeader("X-Sharer-User-Id") Long ownerId) throws ValidationException {
        validateItem.validateId(ownerId);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("X-Sharer-User-Id", String.valueOf(ownerId));
        HttpEntity<Void> request = new HttpEntity<>(httpHeaders);
        ResponseEntity<List<OutItem>> response = restTemplate.exchange(
                SERVER,
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<List<OutItem>>() {
                }
        );
        return response.getBody();
    }

    @GetMapping("/search")
    public List<OutItem> getItemBySearch(@RequestParam("text") String text) throws ValidationException {
        validateItem.validateText(text);
        ResponseEntity<List<OutItem>> response = restTemplate.exchange(
                SERVER + "/search?text={text}",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<OutItem>>() {
                },
                text
        );
        return response.getBody();
    }

    @PostMapping("/{itemId}/comment")
    public OutComment postComment(@PathVariable Long itemId, @RequestHeader("X-Sharer-User-Id") Long userId, @RequestBody InComment comment) throws ValidationException {
        validateItem.validateInputDataForComment(itemId, userId, comment);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("X-Sharer-User-Id", String.valueOf(userId));
        HttpEntity<InComment> request = new HttpEntity<>(comment, httpHeaders);
        ResponseEntity<OutComment> response = restTemplate.postForEntity(
                SERVER + "/" + itemId + "/comment",
                request,
                OutComment.class
        );
        return response.getBody();
    }

}
