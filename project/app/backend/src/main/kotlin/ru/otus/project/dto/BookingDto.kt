package ru.otus.project.dto

import java.time.LocalDateTime

data class BookingDto(
    val bookingId: String,
    val flightNumber: String,
    val passengerName: String,
    val date: LocalDateTime
)
