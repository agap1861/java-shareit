package ru.practicum.outputData;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class OutItem {
    private Long id;
    private String name;
    private String description;
    private Boolean available;
}
