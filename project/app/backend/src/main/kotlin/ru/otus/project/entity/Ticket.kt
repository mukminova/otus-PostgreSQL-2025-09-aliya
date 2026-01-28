package ru.otus.project.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "tickets")
class Ticket(
    @Id
    val ticketNo: String,

    @ManyToOne
    @JoinColumn(name = "book_ref")
    val booking: Booking,

    val passengerId: String,
    val passengerName: String,
    val outbound: Boolean
)
