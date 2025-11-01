package ru.practicum.validate;


import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.practicum.exeption.ValidationException;
import ru.practicum.inputData.InUser;

@Service
public class ValidateUser {

    public void validateInputDataForPost(InUser user) throws ValidationException {
        if (!StringUtils.hasText(user.getName()) || !StringUtils.hasText(user.getEmail())) {
            throw new ValidationException("data is empty");
        }
        if (!user.getEmail().contains("@")) {
            throw new ValidationException("email not correct");
        }

    }

    public void validateInputDataForPatch(InUser user, Long userId) throws ValidationException {
        if (StringUtils.hasText(user.getEmail()) && !user.getEmail().contains("@")) {
            throw new ValidationException("email not correct");
        }
        if (userId == null) {
            throw new ValidationException("id not correct");
        }

    }

    public void validateId(Long userId) throws ValidationException {
        if (userId == null) {
            throw new ValidationException("id not correct");
        }
    }


}
