package ru.practicum.shareit.request.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.exception.DuplicateException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;

import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.request.domain.Request;
import ru.practicum.shareit.request.storage.request.RequestStorage;
import ru.practicum.shareit.user.domain.User;
import ru.practicum.shareit.user.service.UserService;

import java.time.Instant;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@ActiveProfiles("test")
@Transactional
class RequestServiceImplTest {
    private final RequestService requestService;
    private final RequestStorage requestStorage;
    private final UserService userService;
    private final ItemService itemService;

    private Request request = new Request();
    private User user = new User();

    @BeforeEach
    public void createRequestAndUserAndItem() throws ValidationException, DuplicateException, NotFoundException {

        user.setName("name");
        user.setEmail("email");
        user = userService.postUser(user);

        request.setRequesterId(user.getId());
        request.setCreated(Instant.now());
        request.setDescription("description");
        request = requestService.postRequest(request);
    }

    @Test
    public void shouldRequestSave() {
        Request newRequest = requestStorage.getRequestById(request.getId()).get();

        assertThat(request.getId(), equalTo(newRequest.getId()));
        assertThat(request.getRequesterId(), equalTo(newRequest.getRequesterId()));
        assertThat(request.getDescription(), equalTo(newRequest.getDescription()));
        assertThat(request.getCreated(), equalTo(newRequest.getCreated()));
    }

    @Test
    public void shouldReturnRequest() throws NotFoundException {

        Request saveRequest = requestService.getRequestById(request.getId());

        assertThat(request.getId(), equalTo(saveRequest.getId()));
    }


}