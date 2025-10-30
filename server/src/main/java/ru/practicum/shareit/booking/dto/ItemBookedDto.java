package ru.practicum.shareit.booking.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ItemBookedDto {
    private Long id;
    private String name;

    public ItemBookedDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
