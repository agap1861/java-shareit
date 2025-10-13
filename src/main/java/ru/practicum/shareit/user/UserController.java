package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.excaption.DuplicateException;
import ru.practicum.shareit.excaption.NotFoundException;
import ru.practicum.shareit.excaption.ValidationException;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.service.UserService;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;


    @PostMapping
    public User postUser(@RequestBody UserDto userDto) throws ValidationException, DuplicateException {
        return userService.postUser(userDto);
    }

    @PatchMapping("/{userId}")
    public User patchUser(@RequestBody UserDto userDto, @PathVariable Long userId) throws ValidationException, DuplicateException {
        return userService.patchUser(userDto, userId);
    }

    @GetMapping("/{userId}")
    public User getUserById(@PathVariable Long userId) throws NotFoundException {
        return userService.getUserById(userId);
    }

    @DeleteMapping("/{userId}")
    public void deleteUserById(@PathVariable Long userId) {
        userService.deleteUserById(userId);
    }


}
