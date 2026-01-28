package ru.otus.project.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.math.BigDecimal
import java.time.OffsetDateTime

@Entity
@Table(name = "bookings")
class Booking(
    @Id
    val bookRef: String,
    val bookDate: OffsetDateTime,
    val totalAmount: BigDecimal
)
