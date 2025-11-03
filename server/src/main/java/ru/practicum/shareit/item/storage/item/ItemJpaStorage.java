package ru.practicum.shareit.item.storage.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.model.ItemEntity;

import java.util.List;

@Repository
public interface ItemJpaStorage extends JpaRepository<ItemEntity, Long> {

    List<ItemEntity> findAllByOwner_Id(Long ownerId);

    @Query("SELECT it " +
            "FROM ItemEntity  it " +
            "WHERE (LOWER(it.name) LIKE LOWER(CONCAT('%', ?1, '%')) " +
            "OR LOWER(it.description) LIKE LOWER(CONCAT('%', ?1, '%'))) " +
            "AND it.available = true")
    List<ItemEntity> getItemBySearch(String text);

    boolean existsByIdAndOwner_Id(Long itemId, Long ownerId);

}
