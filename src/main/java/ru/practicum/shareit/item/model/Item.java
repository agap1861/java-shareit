package ru.practicum.shareit.item.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Objects;

/**
 * TODO Sprint add-controllers.
 */
@Data
@AllArgsConstructor
public class Item {
    private Long id;
    private Long ownerId;
    private String name;
    private String description;
    private Boolean available;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(id, item.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
