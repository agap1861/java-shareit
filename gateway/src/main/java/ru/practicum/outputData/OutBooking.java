package ru.practicum.outputData;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class OutBooking {
    private Long id;
    private LocalDateTime start;
    private LocalDateTime end;
    private String status;
    private Booker booker;
    private ItemBooked item;
}
