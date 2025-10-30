package ru.practicum.inputData;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
public class InBooking {
    private Long itemId;
    private LocalDateTime start;
    private LocalDateTime end;
    private String status;
}
