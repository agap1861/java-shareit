package ru.practicum.controllers;


import lombok.RequiredArgsConstructor;


import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import ru.practicum.client.ItemClient;
import ru.practicum.exeption.ValidationException;
import ru.practicum.inputData.InComment;
import ru.practicum.inputData.InItem;
import ru.practicum.outputData.ItemWithComment;
import ru.practicum.outputData.OutComment;
import ru.practicum.outputData.OutItem;


import java.util.List;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemClient itemClient;

    @PostMapping
    public OutItem postItem(@RequestHeader("X-Sharer-User-Id") Long ownerId, @RequestBody InItem inItem) throws ValidationException {
        return itemClient.postItem(ownerId, inItem);

    }

    @PatchMapping("/{itemId}")
    public OutItem patchItem(@RequestHeader("X-Sharer-User-Id") Long ownerId,
                             @PathVariable Long itemId, @RequestBody InItem inItem) throws ValidationException {
        return itemClient.patchItem(ownerId, itemId, inItem);
    }

    @GetMapping("/{itemId}")
    public ItemWithComment getItem(@PathVariable Long itemId, @RequestHeader("X-Sharer-User-Id") Long userId) throws ValidationException {
        return itemClient.getItem(itemId, userId);
    }

    @GetMapping
    public List<OutItem> getItemByOwner(@RequestHeader("X-Sharer-User-Id") Long ownerId) throws ValidationException {
        return itemClient.getItemByOwner(ownerId);
    }

    @GetMapping("/search")
    public List<OutItem> getItemBySearch(@RequestParam("text") String text) throws ValidationException {
        return itemClient.getItemBySearch(text);
    }

    @PostMapping("/{itemId}/comment")
    public OutComment postComment(@PathVariable Long itemId, @RequestHeader("X-Sharer-User-Id") Long userId, @RequestBody InComment comment) throws ValidationException {
        return itemClient.postComment(itemId, userId, comment);
    }

}
