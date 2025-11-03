package ru.practicum.shareit.item.dto.item;


import lombok.AllArgsConstructor;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * TODO Sprint add-controllers.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemResponseDto {
    private Long id;
    private String name;
    private String description;
    private Boolean available;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ItemResponseDto itemResponseDto = (ItemResponseDto) o;
        return Objects.equals(id, itemResponseDto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
