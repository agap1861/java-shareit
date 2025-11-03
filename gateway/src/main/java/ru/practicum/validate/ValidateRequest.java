package ru.practicum.validate;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.practicum.exeption.ValidationException;
import ru.practicum.inputData.InRequest;

@Service
public class ValidateRequest {

    public void validateForPost(InRequest inRequest) throws ValidationException {
        if (!StringUtils.hasText(inRequest.getDescription())) {
            throw new ValidationException("description is empty");
        }

    }

    public void validateId(Long id) throws ValidationException {
        if (id == null)
            throw new ValidationException("id not correct");
    }

}
