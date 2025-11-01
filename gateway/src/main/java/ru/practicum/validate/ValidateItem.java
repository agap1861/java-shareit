package ru.practicum.validate;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.practicum.exeption.ValidationException;
import ru.practicum.inputData.InComment;
import ru.practicum.inputData.InItem;

@Service
public class ValidateItem {

    public void validateDataForPost(InItem item) throws ValidationException {

        if (!StringUtils.hasText(item.getName())
                || !StringUtils.hasText(item.getDescription())
                || item.getAvailable() == null) {
            throw new ValidationException("data is empty");
        }
    }

    public void validateId(Long id) throws ValidationException {
        if (id == null) {
            throw new ValidationException("id not correct");
        }
    }

    public void validateText(String text) throws ValidationException {
        if (!StringUtils.hasText(text)) {
            throw new ValidationException("text is empty");
        }
    }

    public void validateInputDataForComment(Long itemId, Long userId, InComment comment) throws ValidationException {
        if (itemId == null || userId == null) {
            throw new ValidationException("id not correct");
        }

    }
}
