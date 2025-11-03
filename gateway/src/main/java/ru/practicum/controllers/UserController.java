package ru.practicum.controllers;


import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;


import ru.practicum.client.UserClient;
import ru.practicum.exeption.ValidationException;
import ru.practicum.inputData.InUser;
import ru.practicum.outputData.OutUser;


@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
public class UserController {
    private final UserClient userClient;

    @PostMapping
    public OutUser postUser(@RequestBody InUser inUser) throws ValidationException {
        return userClient.postUser(inUser);
    }

    @PatchMapping("/{userId}")
    public OutUser patchUser(@RequestBody InUser inUser, @PathVariable Long userId) throws ValidationException {
        return userClient.patchUser(inUser, userId);
    }

    @GetMapping("/{userId}")
    public OutUser getUserById(@PathVariable Long userId) throws ValidationException {
        return userClient.getUserById(userId);
    }

    @DeleteMapping("/{userId}")
    public void deleteUserById(@PathVariable Long userId) throws ValidationException {
        userClient.deleteUserById(userId);
    }


}
