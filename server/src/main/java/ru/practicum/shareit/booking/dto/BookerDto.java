package ru.practicum.shareit.booking.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BookerDto {
    Long id;

    public BookerDto(Long id) {
        this.id = id;
    }
}
