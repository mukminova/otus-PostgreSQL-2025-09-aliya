package ru.otus.project.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.OffsetDateTime

@Entity
@Table(name = "flights")
class Flight(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val flightId: Long = 0,

    val routeNo: String,
    val status: String,

    val scheduledDeparture: OffsetDateTime,
    val scheduledArrival: OffsetDateTime
)

