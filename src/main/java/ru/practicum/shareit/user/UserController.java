package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.excaption.DuplicateException;
import ru.practicum.shareit.excaption.NotFoundException;
import ru.practicum.shareit.excaption.ValidationException;
import ru.practicum.shareit.user.domain.User;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.mapper.UserDomainDtoMapper;
import ru.practicum.shareit.user.service.UserService;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserDomainDtoMapper userDomainDtoMapper;


    @PostMapping
    public UserDto postUser(@RequestBody UserDto userDto) throws ValidationException, DuplicateException {
        User user = userService.postUser(userDomainDtoMapper.dtoToDomain(userDto));
        return userDomainDtoMapper.domainToDto(user);
    }

    @PatchMapping("/{userId}")
    public UserDto patchUser(@RequestBody UserDto userDto, @PathVariable Long userId) throws ValidationException, DuplicateException {
        userDto.setId(userId);
        User user = userService.patchUser(userDomainDtoMapper.dtoToDomain(userDto));
        return userDomainDtoMapper.domainToDto(user);
    }

    @GetMapping("/{userId}")
    public UserDto getUserById(@PathVariable Long userId) throws NotFoundException {
        User user = userService.getUserById(userId);
        return userDomainDtoMapper.domainToDto(user);
    }

    @DeleteMapping("/{userId}")
    public void deleteUserById(@PathVariable Long userId) {
        userService.deleteUserById(userId);
    }


}
