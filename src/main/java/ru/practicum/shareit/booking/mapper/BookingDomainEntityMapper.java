package ru.practicum.shareit.booking.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.shareit.booking.domain.Booking;
import ru.practicum.shareit.booking.entity.BookingEntity;
import ru.practicum.shareit.item.mapper.item.ItemDomainEntityMapper;

@Mapper(componentModel = "spring",uses = ItemDomainEntityMapper.class)
public interface BookingDomainEntityMapper {
    @Mapping(target = "item", source = "booking.item")
    @Mapping(target = "booker.id",source = "bookerId")
    BookingEntity domainToEntity(Booking booking);

    @Mapping(target = "item",source = "bookingEntity.item")
    @Mapping(target = "bookerId",source = "bookingEntity.booker.id")
    Booking entityToDomain(BookingEntity bookingEntity);
}
