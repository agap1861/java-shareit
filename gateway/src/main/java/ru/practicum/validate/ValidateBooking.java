package ru.practicum.validate;

import org.springframework.stereotype.Service;
import ru.practicum.exeption.ValidationException;
import ru.practicum.inputData.InBooking;

import java.time.LocalDateTime;
@Service
public class ValidateBooking {
    public void validateId(Long id) throws ValidationException {
        if (id == null) {
            throw new ValidationException("id not correct");
        }
    }

    public void validateBookingForPost(InBooking inBooking) throws ValidationException {
        if (inBooking.getItemId() == null)
            throw new ValidationException("id not correct");

        if (inBooking.getStart() == null || inBooking.getEnd() == null) {
            throw new ValidationException("end or start don't equals null");
        }
        if (inBooking.getEnd().isBefore(LocalDateTime.now())) {
            throw new ValidationException("time of end already passed");
        }
        if (inBooking.getStart().isBefore(LocalDateTime.now())) {
            throw new ValidationException("time of start already passed");
        }
        if (inBooking.getStart().isEqual(inBooking.getEnd())) {
            throw new ValidationException("start equal to end");
        }
    }
}
