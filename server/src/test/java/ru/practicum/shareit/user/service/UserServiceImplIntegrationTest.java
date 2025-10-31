package ru.practicum.shareit.user.service;

import jakarta.persistence.EntityManager;
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
import ru.practicum.shareit.user.domain.User;
import ru.practicum.shareit.user.storage.UserStorage;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@ActiveProfiles("test")
@Transactional
class UserServiceImplIntegrationTest {
    private final UserService userService;
    private final UserStorage userStorage;
    private  User user = new User();


    @BeforeEach
    public void createUser() {

        user.setName("name");
        user.setEmail("email@.ru");

        user = userStorage.save(user);

    }


    @Test
    public void userShouldBeSaveInDb() throws NotFoundException {
        User newUser = userService.getUserById(user.getId());

        assertThat("name", equalTo(newUser.getName()));
        assertThat("email@.ru", equalTo(newUser.getEmail()));
        assertThat(user.getId(), equalTo(newUser.getId()));


    }

    @Test
    public void shouldUpdateUserSuccessfully() throws ValidationException, DuplicateException, NotFoundException {
        User updateUser = new User();
        updateUser.setName("newName");
        updateUser.setEmail("newEmail@.com");
        updateUser.setId(user.getId());


        User newUser = userService.patchUser(updateUser);

        assertThat(updateUser.getId(), equalTo(newUser.getId()));

        assertThat(updateUser.getName(), equalTo(newUser.getName()));

        assertThat(updateUser.getEmail(), equalTo(newUser.getEmail()));


    }

    @Test
    public void shouldThrowExceptionWhenEmailExist() {
        User user = new User();
        user.setName("dsfs");
        user.setEmail("email@.ru");

        assertThrows(DuplicateException.class, () -> userService.postUser(user));
    }
    @Test
    public void shouldThrowExceptionWhenUserDoseNotExist(){
        User newUser = new User();

        newUser.setId(999L);
        newUser.setName("nameNew");
        newUser.setEmail("df@vf");

        assertThrows(ValidationException.class,()-> userService.patchUser(newUser));

    }


    @Test
    public void shouldReturnUserById() throws ValidationException, DuplicateException, NotFoundException {
        User newUser = new User();
        newUser.setName("testName");
        newUser.setEmail("mail@");

        newUser = userService.postUser(newUser);

        User testuser = userService.getUserById(newUser.getId());

        assertThat(newUser.getId(),equalTo(testuser.getId()));
        assertThat(newUser.getName(),equalTo(testuser.getName()));
        assertThat(newUser.getEmail(),equalTo(testuser.getEmail()));
    }

    @Test
    public void shouldReturnTrueWhenUserExist(){
        assertThat(userService.existById(user.getId()),equalTo(true));
    }

    @Test
    public void shouldDeleteUser()  {
        userService.deleteUserById(user.getId());
        assertThat(userStorage.findById(user.getId()),equalTo(Optional.empty()));

    }


}