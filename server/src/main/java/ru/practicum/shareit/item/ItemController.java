package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.domian.Comment;
import ru.practicum.shareit.item.domian.Item;

import ru.practicum.shareit.item.dto.comment.CommentRequestDto;
import ru.practicum.shareit.item.dto.comment.CommentResponseDto;
import ru.practicum.shareit.item.dto.item.ItemRequestDto;
import ru.practicum.shareit.item.dto.item.ItemResponseDto;
import ru.practicum.shareit.item.dto.item.ItemWithComments;
import ru.practicum.shareit.item.mapper.comment.CommentDomainRequestDtoMapper;
import ru.practicum.shareit.item.mapper.comment.CommentDomainResponseMapper;
import ru.practicum.shareit.item.mapper.item.ItemDomainRequestMapper;
import ru.practicum.shareit.item.mapper.item.ItemDomainResponseDtoMapper;
import ru.practicum.shareit.item.service.ItemService;

import java.util.List;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {
    private final ItemService service;
    private final ItemDomainResponseDtoMapper itemResponseDto;
    private final ItemDomainRequestMapper itemDomainRequestMapper;
    private final CommentDomainResponseMapper commentDomainResponseMapper;
    private final CommentDomainRequestDtoMapper commentDomainRequestDtoMapper;

    @PostMapping
    public ItemResponseDto postItem(@RequestHeader("X-Sharer-User-Id") Long ownerId, @RequestBody ItemRequestDto itemRequestDto) throws ValidationException, NotFoundException {
        itemRequestDto.setOwnerId(ownerId);
        Item item = service.postItem(itemDomainRequestMapper.domainToDto(itemRequestDto));
        return itemResponseDto.dtoToDomain(item);

    }

    @PatchMapping("/{itemId}")
    public ItemResponseDto patchItem(@RequestHeader("X-Sharer-User-Id") Long ownerId,
                                     @PathVariable Long itemId, @RequestBody ItemRequestDto itemRequestDto) throws ValidationException, NotFoundException {
        itemRequestDto.setId(itemId);
        itemRequestDto.setOwnerId(ownerId);
        Item item = service.patchItem(itemDomainRequestMapper.domainToDto(itemRequestDto));
        return itemResponseDto.dtoToDomain(item);
    }

    @GetMapping("/{itemId}")
    public ItemWithComments getItemById(@PathVariable Long itemId, @RequestHeader("X-Sharer-User-Id") Long userId) throws NotFoundException {
        return service.getItem(itemId, userId);
    }

    @GetMapping
    public List<ItemResponseDto> getItemByOwner(@RequestHeader("X-Sharer-User-Id") Long ownerId) {
        List<Item> items = service.getItemByOwner(ownerId);
        return items.stream().map(itemResponseDto::dtoToDomain).toList();
    }

    @GetMapping("/search")
    public List<ItemResponseDto> getItemsBySearch(@RequestParam("text") String text) {
        List<Item> items = service.getItemBySearch(text);
        return items.stream().map(itemResponseDto::dtoToDomain).toList();
    }

    @PostMapping("/{itemId}/comment")
    public CommentResponseDto postComment(@PathVariable Long itemId, @RequestHeader("X-Sharer-User-Id") Long userId, @RequestBody CommentRequestDto dto) throws ValidationException, NotFoundException {
        dto.setItemId(itemId);
        dto.setUserId(userId);
        Comment comment = service.postComment(commentDomainRequestDtoMapper.dtoToDomain(dto));
        return commentDomainResponseMapper.domainToDto(comment);
    }


}
