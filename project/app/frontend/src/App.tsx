// App.tsx
import { useEffect, useState } from "react";
import Flight from "./Flight";
import Booking from "./Booking";
import { getFlights, bookFlight, getBookingsByFlight } from "./api";

function App() {
  const [flights, setFlights] = useState<any[]>([]);

  useEffect(() => {
    getFlights().then(setFlights);
  }, []);

  return (
    <div style={{ fontFamily: 'Arial, sans-serif', padding: '20px' }}>
      <h1>Рейсы</h1>
      {flights.map((flight) => (
        <Flight
          key={flight.flightId}
          flight={flight}
          onBook={bookFlight}
          getBookings={getBookingsByFlight}
        />
      ))}
    </div>
  );
}

export default App;