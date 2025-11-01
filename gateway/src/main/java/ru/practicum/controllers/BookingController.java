package ru.practicum.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.practicum.exeption.ValidationException;
import ru.practicum.inputData.InBooking;

import ru.practicum.outputData.OutBooking;
import ru.practicum.validate.ValidateBooking;

import java.util.List;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final ValidateBooking validateBooking;
    private final RestTemplate restTemplate;
    private final static String SERVER = "http://localhost:9090/bookings";

    @PostMapping
    public OutBooking postBooking(@RequestHeader("X-Sharer-User-Id") Long bookerId, @RequestBody InBooking inBooking) throws ValidationException {
        validateBooking.validateId(bookerId);
        validateBooking.validateBookingForPost(inBooking);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("X-Sharer-User-Id", String.valueOf(bookerId));
        HttpEntity<InBooking> request = new HttpEntity<>(inBooking, httpHeaders);
        ResponseEntity<OutBooking> response = restTemplate.postForEntity(
                SERVER,
                request,
                OutBooking.class
        );
        return response.getBody();
    }

    @PatchMapping("/{bookingId}")
    public OutBooking patchBooking(@PathVariable Long bookingId, @RequestParam(name = "approved") Boolean approved, @RequestHeader("X-Sharer-User-Id") Long ownerId) throws ValidationException {
        validateBooking.validateId(bookingId);
        validateBooking.validateId(ownerId);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("X-Sharer-User-Id", String.valueOf(ownerId));
        HttpEntity<Void> request = new HttpEntity<>(httpHeaders);
        ResponseEntity<OutBooking> response = restTemplate.exchange(
                SERVER + "/" + bookingId + "?approved={approved}",
                HttpMethod.PATCH,
                request,
                OutBooking.class,
                approved
        );
        return response.getBody();

    }

    @GetMapping("/{bookingId}")
    public OutBooking getBookingByUserIdAndBookingId(@PathVariable Long bookingId, @RequestHeader("X-Sharer-User-Id") Long userId) throws ValidationException {
        validateBooking.validateId(bookingId);
        validateBooking.validateId(userId);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("X-Sharer-User-Id", String.valueOf(userId));
        HttpEntity<Void> request = new HttpEntity<>(httpHeaders);
        ResponseEntity<OutBooking> response = restTemplate.exchange(
                SERVER + "/" + bookingId,
                HttpMethod.GET,
                request,
                OutBooking.class
        );
        return response.getBody();

    }

    @GetMapping
    public List<OutBooking> getBookingsByUserId(@RequestHeader("X-Sharer-User-Id") Long bookerId) throws ValidationException {
        validateBooking.validateId(bookerId);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("X-Sharer-User-Id", String.valueOf(bookerId));
        HttpEntity<Void> request = new HttpEntity<>(httpHeaders);
        ResponseEntity<List<OutBooking>> response = restTemplate.exchange(
                SERVER,
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<List<OutBooking>>() {
                }
        );
        return response.getBody();
    }


}
