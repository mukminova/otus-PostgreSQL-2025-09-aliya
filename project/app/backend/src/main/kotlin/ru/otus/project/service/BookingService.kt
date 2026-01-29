package ru.otus.project.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.otus.project.dto.BookingDto
import ru.otus.project.entity.Booking
import ru.otus.project.entity.Segment
import ru.otus.project.entity.Ticket
import ru.otus.project.repository.BookingRepository
import ru.otus.project.repository.FlightRepository
import ru.otus.project.repository.SegmentRepository
import ru.otus.project.repository.TicketRepository
import java.math.BigDecimal
import java.time.OffsetDateTime
import java.util.UUID

@Service
class BookingService(
    private val flightRepo: FlightRepository,
    private val bookingRepo: BookingRepository,
    private val ticketRepo: TicketRepository,
    private val segmentRepo: SegmentRepository
) {

    @Transactional
    fun bookFlight(
        flightId: Long,
        passengerName: String
    ) {
        val flight = flightRepo.findById(flightId).orElseThrow()

        val booking = Booking(
            bookRef = UUID.randomUUID().toString().take(6),
            bookDate = OffsetDateTime.now(),
            totalAmount = BigDecimal("100.00")
        )
        bookingRepo.save(booking)

        val ticket = Ticket(
            ticketNo = UUID.randomUUID().toString(),
            booking = booking,
            passengerId = UUID.randomUUID().toString(),
            passengerName = passengerName,
            outbound = true
        )
        ticketRepo.save(ticket)

        val segment = Segment(
            ticketNo = ticket.ticketNo,
            flightId = flight.flightId,
            fareConditions = "Economy",
            price = BigDecimal("100.00")
        )
        segmentRepo.save(segment)
    }

    fun getBookings(flightId: Long): List<BookingDto> {
        val flightOpt = flightRepo.findById(flightId)
        if (flightOpt.isEmpty) return emptyList()

        val flight = flightOpt.get()

        val segments = segmentRepo.findAllByFlightId(flightId)
        val tickets = ticketRepo.findAllById(segments.map { it.ticketNo })
        val bookingByRef = bookingRepo.findAllById(tickets.map { it.booking.bookRef })
            .associateBy { it.bookRef }
        return tickets.mapNotNull { ticket ->
            val booking = bookingByRef[ticket.booking.bookRef] ?: return@mapNotNull null

            BookingDto(
                booking.bookRef,
                flight.routeNo,
                ticket.passengerName,
                flight.scheduledDeparture.toLocalDateTime()
            )
        }
    }
}
