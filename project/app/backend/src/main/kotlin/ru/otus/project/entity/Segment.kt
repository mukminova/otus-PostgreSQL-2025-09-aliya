package ru.otus.project.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.IdClass
import jakarta.persistence.Table
import java.io.Serializable
import java.math.BigDecimal

@Entity
@Table(name = "segments")
@IdClass(SegmentId::class)
class Segment(
    @Id
    val ticketNo: String,

    @Id
    val flightId: Long,

    val fareConditions: String,
    val price: BigDecimal
)

data class SegmentId(
    val ticketNo: String = "",
    val flightId: Long = 0
) : Serializable
