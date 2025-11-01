package ru.practicum.shareit.booking.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.shareit.booking.domain.Booking;
import ru.practicum.shareit.booking.dto.BookingRequestDto;
import ru.practicum.shareit.booking.dto.BookingResponse;
import ru.practicum.shareit.item.domian.Item;

@Mapper(componentModel = "spring")
public interface BookingDomainDtoMapper {

    @Mapping(target = "item.id", source = "itemId")
   // @Mapping(target = "bookerId",source = "dto.bookerId")
    @Mapping(target = "bookerId",source = "dto.bookerId")
    Booking dtoToDomain(BookingRequestDto dto);

    @Mapping(target = "booker.id",source = "bookerId")
    @Mapping(target = "item.id",source = "item.id")
    @Mapping(target = "item.name",source = "item.name")
    BookingResponse domainToDto(Booking booking);

}
