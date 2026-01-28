export async function getFlights() {
  return fetch("/api/flights")
    .then(res => res.json());
}

export const getBookingsByFlight = async (flightId: string) => {
  const response = await fetch(`/api/bookings?flightId=${flightId}`);
  return await response.json();
};

export async function bookFlight(flightId: number, passengerName: string) {
  return fetch(
    `/api/book/${flightId}?passengerName=${passengerName}`,
    { method: "POST" }
  );
}
