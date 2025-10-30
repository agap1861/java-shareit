package ru.practicum.shareit.booking.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.booking.entity.BookingEntity;


import java.util.List;

@Repository
public interface BookingJpaStorage extends JpaRepository<BookingEntity, Long> {
    List<BookingEntity> findAllByBooker_Id(Long bookerId);

    BookingEntity findOneByBooker_IdAndItem_Id(Long bookerId, Long itemId);

    @Query("SELECT b FROM BookingEntity b " +
            "WHERE b.item.id = ?1 " +
            "AND b.start < CURRENT_TIMESTAMP " +
            "ORDER BY b.start DESC ")
    BookingEntity findLastBooking(Long itemId);

    @Query("SELECT b FROM BookingEntity b " +
            "WHERE b.item.id = ?1 " +
            "AND b.start > CURRENT_TIMESTAMP " +
            "ORDER BY b.start ASC ")
    BookingEntity findNextBooking(Long itemId);

}
