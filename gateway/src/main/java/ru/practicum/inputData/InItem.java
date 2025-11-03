package ru.practicum.inputData;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class InItem {
    private String name;
    private String description;
    private Boolean available;
    private Long requestId;
}
