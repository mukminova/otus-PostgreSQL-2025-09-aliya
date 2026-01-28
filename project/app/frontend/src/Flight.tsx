import { useState, useEffect } from "react";
import Booking from "./Booking";
import { getBookingsByFlight } from "./api";

interface FlightProps {
  flight: {
    flightId: string;
    flightNumber: string;
    departure: string;
    arrival: string;
    date: string;
    routeNo: string;
    scheduledDeparture: string;
    scheduledArrival: string;
    status: string;
  };
  onBook: (flightId: string, passengerName: string) => Promise<void>;
  getBookings: typeof getBookingsByFlight;
}

const Flight: React.FC<FlightProps> = ({ flight, onBook, getBookings }) => {
  const [showBookings, setShowBookings] = useState(false);
  const [bookings, setBookings] = useState<any[]>([]);
  const [loading, setLoading] = useState(false);
  const [showForm, setShowForm] = useState(false);
  const [passengerName, setPassengerName] = useState("");

  const handleToggleBookings = async () => {
    if (!showBookings) {
      setLoading(true);
      const data = await getBookings(flight.flightId);
      setBookings(data);
      setLoading(false);
    }
    setShowBookings(!showBookings);
  };

  const handleBookClick = () => {
    setShowForm(true);
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!passengerName.trim()) return;

    await onBook(flight.flightId, passengerName);
    setPassengerName("");
    setShowForm(false);
  };

  return (
    <div style={{
      border: "1px solid #ccc",
      borderRadius: "8px",
      padding: "16px",
      marginBottom: "16px",
    }}>
      <h3>Рейс {flight.routeNo}</h3>
      <p><strong>От:</strong> {flight.scheduledDeparture}</p>
      <p><strong>До:</strong> {flight.scheduledArrival}</p>
      <p><strong>Статус рейса:</strong> {flight.status}</p>

      <button onClick={handleBookClick} style={{ marginRight: "10px" }}>
        Забронировать
      </button>

      <button onClick={handleToggleBookings}>
        {showBookings ? "Скрыть бронирования" : "Показать бронирования"}
      </button>

      {/* Форма для ввода имени */}
      {showForm && (
        <form onSubmit={handleSubmit} style={{ marginTop: "10px" }}>
          <label>
            Имя пассажира:
            <input
              type="text"
              value={passengerName}
              onChange={(e) => setPassengerName(e.target.value)}
              required
              style={{ marginLeft: "8px", padding: "4px" }}
            />
          </label>
          <button type="submit" style={{ marginLeft: "8px" }}>
            Отправить
          </button>
        </form>
      )}

      {/* Список бронирований */}
      {showBookings && (
        <div style={{ marginTop: "20px" }}>
          <h4>Бронирования:</h4>
          {loading ? (
            <p>Загрузка...</p>
          ) : bookings.length > 0 ? (
            bookings.map((booking) => (
              <Booking key={booking.bookingId} booking={booking} />
            ))
          ) : (
            <p>Нет бронирований.</p>
          )}
        </div>
      )}
    </div>
  );
};

export default Flight;