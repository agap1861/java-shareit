package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.request.domain.Request;
import ru.practicum.shareit.request.dto.RequestCreateDto;
import ru.practicum.shareit.request.dto.RequestDto;
import ru.practicum.shareit.request.dto.RequestWithResponseDto;
import ru.practicum.shareit.request.mapper.request.RequestAndResponseDomainMapper;
import ru.practicum.shareit.request.mapper.request.RequestDomainDtoMapper;
import ru.practicum.shareit.request.service.RequestService;

import java.util.List;

/**
 * TODO Sprint add-item-requests.
 */
@RestController
@RequestMapping(path = "/requests")
@RequiredArgsConstructor
public class RequestController {
    private final RequestService requestService;
    private final RequestDomainDtoMapper requestDomainDtoMapper;
    private final RequestAndResponseDomainMapper requestAndResponseDomainMapper;


    @PostMapping
    public RequestDto postRequest(@RequestBody RequestCreateDto requestDto, @RequestHeader("X-Sharer-User-Id") Long userId) {
        requestDto.setRequesterId(userId);
        Request request = requestService.postRequest(requestDomainDtoMapper.dtoToDomain(requestDto));
        return requestDomainDtoMapper.domainToDto(request);

    }

    @GetMapping
    public List<RequestWithResponseDto> getAllRequests(@RequestHeader("X-Sharer-User-Id") Long userId) {
        List<Request> requests = requestService.getRequestsByUserId(userId);
        return requests.stream().map(requestAndResponseDomainMapper::domainToDto).toList();
    }

    @GetMapping("/{requestId}")
    public RequestWithResponseDto getRequestById(@PathVariable Long requestId) throws NotFoundException {
        Request request = requestService.getRequestById(requestId);
        return requestAndResponseDomainMapper.domainToDto(request);

    }


}
