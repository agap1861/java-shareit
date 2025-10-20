package ru.practicum.shareit.booking.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.booking.Booking;


import java.util.List;

@Repository
public interface BookingStorage extends JpaRepository<Booking, Long> {
    List<Booking> findAllByBooker_Id(Long bookerId);

    Booking findOneByBooker_IdAndItem_Id(Long bookerId, Long itemId);

    @Query("SELECT b FROM Booking b " +
            "WHERE b.item.id = ?1 " +
            "AND b.startDate < CURRENT_TIMESTAMP " +
            "ORDER BY b.startDate DESC ")
    Booking findLastBooking(Long itemId);

    @Query("SELECT b FROM Booking b " +
            "WHERE b.item.id = ?1 " +
            "AND b.startDate > CURRENT_TIMESTAMP " +
            "ORDER BY b.startDate ASC ")
    Booking findNextBooking(Long itemId);

}
