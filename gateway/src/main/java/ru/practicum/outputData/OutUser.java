package ru.practicum.outputData;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OutUser {
    private Long id;
    private String name;
    private String email;
}
