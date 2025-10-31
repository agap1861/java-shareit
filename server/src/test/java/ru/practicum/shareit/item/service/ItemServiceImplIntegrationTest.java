package ru.practicum.shareit.item.service;

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
import ru.practicum.shareit.item.domian.Comment;
import ru.practicum.shareit.item.domian.Item;
import ru.practicum.shareit.item.storage.item.ItemStorage;
import ru.practicum.shareit.user.domain.User;
import ru.practicum.shareit.user.service.UserService;

import java.time.Instant;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@ActiveProfiles("test")
@Transactional
class ItemServiceImplIntegrationTest {
    private final ItemService itemService;
    private final ItemStorage itemStorage;
    private final UserService userService;

    private User user = new User();
    private Item item = new Item();

    @BeforeEach
    public void creteUserAndItem() throws ValidationException, DuplicateException, NotFoundException {
        user.setName("name");
        user.setEmail("email@");
        user = userService.postUser(user);

        item.setName("nameItem");
        item.setDescription("descr");
        item.setAvailable(true);
        item.setOwnerId(user.getId());
        item = itemService.postItem(item);
    }

    @Test
    public void ItemShouldSave() throws ValidationException, NotFoundException {
        Item newItem = new Item();
        newItem.setName("newNameItem");
        newItem.setDescription("descr1");
        newItem.setAvailable(true);
        newItem.setOwnerId(user.getId());
        newItem  = itemService.postItem(newItem);

        Item saved = itemStorage.save(newItem);

        assertThat(newItem.getId(),equalTo(saved.getId()));
        assertThat(newItem.getName(),equalTo(saved.getName()));
        assertThat(newItem.getDescription(),equalTo(saved.getDescription()));
        assertThat(newItem.getAvailable(),equalTo(saved.getAvailable()));
        assertThat(newItem.getOwnerId(),equalTo(saved.getOwnerId()));

    }
    @Test
    public void ShouldUpdateItemCorrect() throws ValidationException, NotFoundException {
        Item newItem = new Item();
        newItem.setId(item.getId());
        newItem.setName("newNameItem");
        newItem.setDescription("descr1");
        newItem.setAvailable(true);
        newItem.setOwnerId(user.getId());
        itemService.patchItem(newItem);

        Item updateItem = itemStorage.findById(newItem.getId()).orElseThrow();

        assertThat(newItem.getId(),equalTo(updateItem.getId()));
        assertThat(newItem.getName(),equalTo(updateItem.getName()));
        assertThat(newItem.getDescription(),equalTo(updateItem.getDescription()));
        assertThat(newItem.getAvailable(),equalTo(updateItem.getAvailable()));
        assertThat(newItem.getOwnerId(),equalTo(updateItem.getOwnerId()));


    }

    @Test
    public void ShouldNotUpdateWhenOwnerWrong() throws ValidationException, DuplicateException {
        User newUSer = new User();
        newUSer.setName("name1");
        newUSer.setEmail("email@a.c");
        newUSer = userService.postUser(newUSer);

        Item newItem = new Item();
        newItem.setId(item.getId());
        newItem.setName("newNameItem");
        newItem.setDescription("descr1");
        newItem.setAvailable(true);
        newItem.setOwnerId(newUSer.getId());

        assertThrows(ValidationException.class,()-> itemService.patchItem(newItem));

    }
    @Test
    public void shouldThrowWhenItemDoseNotExist(){
        Item newItem = new Item();
        newItem.setId(item.getId()+1L);
        newItem.setName("newNameItem");
        newItem.setDescription("descr1");
        newItem.setAvailable(true);
        newItem.setOwnerId(user.getId());

        assertThrows(NotFoundException.class,()-> itemService.patchItem(newItem));

    }

}