package ru.practicum.shareit.item.dto.item;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ItemRequestDto {
    private Long id;
    private Long ownerId;
    private String name;
    private String description;
    private Boolean available;

    public ItemRequestDto(String name, String description, Boolean available) {
        this.name = name;
        this.description = description;
        this.available = available;
    }
}
