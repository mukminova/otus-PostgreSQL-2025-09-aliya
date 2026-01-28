// Booking.tsx
import React from "react";

interface BookingProps {
  booking: {
    bookingId: string;
    flightNumber: string;
    passengerName: string;
    seatNumber: string;
    date: string;
  };
}

const Booking: React.FC<BookingProps> = ({ booking }) => {
  return (
    <div style={{
      border: "1px solid #ccc",
      borderRadius: "8px",
      padding: "16px",
      marginBottom: "16px",
      backgroundColor: "#f9f9f9",
    }}>
      <h3>Бронирование #{booking.bookingId}</h3>
      <p><strong>Рейс:</strong> {booking.flightNumber}</p>
      <p><strong>Пассажир:</strong> {booking.passengerName}</p>
      <p><strong>Дата:</strong> {booking.date}</p>
    </div>
  );
};

export default Booking;