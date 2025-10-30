package ru.practicum.shareit.booking.storage;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.booking.domain.Booking;
import ru.practicum.shareit.booking.entity.BookingEntity;
import ru.practicum.shareit.booking.mapper.BookingDomainEntityMapper;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BookingStorageImpl implements BookingStorage {
    private final BookingJpaStorage bookingJpaStorage;
    private final BookingDomainEntityMapper bookingDomainEntityMapper;

    @Override
    public Booking save(Booking booking) {
        BookingEntity bookingEntity = bookingJpaStorage.save(bookingDomainEntityMapper.domainToEntity(booking));
        return bookingDomainEntityMapper.entityToDomain(bookingEntity);
    }

    @Override
    public Optional<Booking> findById(Long bookingId) {
        return bookingJpaStorage.findById(bookingId).map(bookingDomainEntityMapper::entityToDomain);
    }

    @Override
    public Booking findLastBooking(Long itemId) {
        BookingEntity bookingEntity = bookingJpaStorage.findLastBooking(itemId);
        return bookingDomainEntityMapper.entityToDomain(bookingEntity);
    }

    @Override
    public Booking findNextBooking(Long itemId) {
        BookingEntity bookingEntity = bookingJpaStorage.findNextBooking(itemId);
        return bookingDomainEntityMapper.entityToDomain(bookingEntity);
    }

    @Override
    public List<Booking> findAllByBooker_Id(Long bookerId) {
        return bookingJpaStorage.findAllByBooker_Id(bookerId).stream()
                .map(bookingDomainEntityMapper::entityToDomain)
                .toList();
    }

    @Override
    public Booking findOneByBooker_IdAndItemEntity_Id(Long bookerId, Long itemId) {
        BookingEntity bookingEntity = bookingJpaStorage.findOneByBooker_IdAndItem_Id(bookerId,itemId);
        return bookingDomainEntityMapper.entityToDomain(bookingEntity);
    }
}
