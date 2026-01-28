package ru.otus.project.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.otus.project.dto.BookingDto
import ru.otus.project.entity.Flight
import ru.otus.project.repository.FlightRepository
import ru.otus.project.service.BookingService

@RestController
@RequestMapping("/api")
class BookingController(
    private val bookingService: BookingService,
    private val flightRepo: FlightRepository
) {

    @GetMapping("/flights")
    fun flights(): List<Flight> = flightRepo.findScheduledFlights()

    @GetMapping("/bookings")
    fun getBookingsByFlight(@RequestParam flightId: Long): List<BookingDto> = bookingService.getBookings(flightId)

    @PostMapping("/book/{flightId}")
    fun book(
        @PathVariable flightId: Long,
        @RequestParam passengerName: String
    ) {
        bookingService.bookFlight(flightId, passengerName)
    }
}
