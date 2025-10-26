package ru.practicum.shareit.user.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

/**
 * TODO Sprint add-controllers.
 */
@Getter
@Setter
@Entity
@AllArgsConstructor
@Table(name = "users")
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "email")
    private String email;



    public UserEntity(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public UserEntity(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity userEntity = (UserEntity) o;
        return Objects.equals(id, userEntity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

}
