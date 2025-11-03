package ru.practicum.client;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.springframework.web.client.RestTemplate;
import ru.practicum.exeption.ValidationException;
import ru.practicum.inputData.InBooking;
import ru.practicum.outputData.OutBooking;
import ru.practicum.validate.ValidateBooking;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingClient {
    private final ValidateBooking validateBooking;
    private final RestTemplate restTemplate;
    @Value("${shareit.server.url}")
    private String serverUrl;
    private static final String path = "/bookings";

    public OutBooking postBooking(Long bookerId, InBooking inBooking) throws ValidationException {
        validateBooking.validateId(bookerId);
        validateBooking.validateBookingForPost(inBooking);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("X-Sharer-User-Id", String.valueOf(bookerId));
        HttpEntity<InBooking> request = new HttpEntity<>(inBooking, httpHeaders);
        ResponseEntity<OutBooking> response = restTemplate.postForEntity(
                serverUrl + path,
                request,
                OutBooking.class
        );
        return response.getBody();
    }

    public OutBooking patchBooking(Long bookingId, Boolean approved, Long ownerId) throws ValidationException {
        validateBooking.validateId(bookingId);
        validateBooking.validateId(ownerId);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("X-Sharer-User-Id", String.valueOf(ownerId));
        HttpEntity<Void> request = new HttpEntity<>(httpHeaders);
        ResponseEntity<OutBooking> response = restTemplate.exchange(
                serverUrl + path + "/" + bookingId + "?approved={approved}",
                HttpMethod.PATCH,
                request,
                OutBooking.class,
                approved
        );
        return response.getBody();

    }

    public OutBooking getBookingByUserIdAndBookingId(Long bookingId, Long userId) throws ValidationException {
        validateBooking.validateId(bookingId);
        validateBooking.validateId(userId);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("X-Sharer-User-Id", String.valueOf(userId));
        HttpEntity<Void> request = new HttpEntity<>(httpHeaders);
        ResponseEntity<OutBooking> response = restTemplate.exchange(
                serverUrl + path + "/" + bookingId,
                HttpMethod.GET,
                request,
                OutBooking.class
        );
        return response.getBody();

    }

    public List<OutBooking> getBookingsByUserId(Long bookerId) throws ValidationException {
        validateBooking.validateId(bookerId);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("X-Sharer-User-Id", String.valueOf(bookerId));
        HttpEntity<Void> request = new HttpEntity<>(httpHeaders);
        ResponseEntity<List<OutBooking>> response = restTemplate.exchange(
                serverUrl + path,
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<List<OutBooking>>() {
                }
        );
        return response.getBody();
    }
}
