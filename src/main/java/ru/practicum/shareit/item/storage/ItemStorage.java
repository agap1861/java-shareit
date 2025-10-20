package ru.practicum.shareit.item.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

@Repository
public interface ItemStorage extends JpaRepository<Item, Long> {

    List<Item> findAllByOwner_Id(Long ownerId);

    @Query("SELECT it " +
            "FROM Item  it " +
            "WHERE (LOWER(it.name) LIKE LOWER(CONCAT('%', ?1, '%')) " +
            "OR LOWER(it.description) LIKE LOWER(CONCAT('%', ?1, '%'))) " +
            "AND it.available = true")
    List<Item> getItemBySearch(String text);

    boolean existsByIdAndOwner_Id(Long itemId, Long ownerId);

}
