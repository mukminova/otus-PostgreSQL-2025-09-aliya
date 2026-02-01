package ru.otus.project.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import ru.otus.project.entity.Booking
import ru.otus.project.entity.Flight
import ru.otus.project.entity.Segment
import ru.otus.project.entity.SegmentId
import ru.otus.project.entity.Ticket

@Repository
interface FlightRepository : JpaRepository<Flight, Long> {
    @Query("SELECT f FROM Flight f WHERE f.status = 'Scheduled'")
    fun findScheduledFlights(): List<Flight>
}

@Repository
interface BookingRepository : JpaRepository<Booking, String>

@Repository
interface TicketRepository : JpaRepository<Ticket, String>

@Repository
interface SegmentRepository : JpaRepository<Segment, SegmentId> {
    fun findAllByFlightId(flightId: Long): List<Segment>
}
