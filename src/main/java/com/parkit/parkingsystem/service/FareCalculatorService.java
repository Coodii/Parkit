package com.parkit.parkingsystem.service;

import java.util.Date;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.model.Ticket;

@SuppressWarnings("checkstyle:RegexpSingleline")
public class FareCalculatorService {

	/**
	 * Create ticketDAO.
	 */
	private TicketDAO ticketDAO;

	/**
	 * This method initialize ticketDAO.
	 */
	public FareCalculatorService() {
		ticketDAO = new TicketDAO();
	}

	/**
	 * This method calculate the fare of the vehicle.
	 * @param ticket
	 */
	public void calculateFare(final Ticket ticket) {
		if ((ticket.getOutTime() == null) || (ticket.getOutTime().before(ticket.getInTime()))) {
			throw new IllegalArgumentException("Out time provided is incorrect:" + ticket.getOutTime().toString());
		}
		Date inHour = ticket.getInTime();
		Date outHour = ticket.getOutTime();
		double duration = outHour.getTime() - inHour.getTime();
		duration = duration / (60 * 60 * 1000);
		double ratio;
		//free for less than 30 min
		if (duration < 0.5) {
			ratio = 0;
		//discount of 5% for recurrent user
		} else if (ticketDAO.checkExistingVehicle(ticket)) {
			ratio = 0.95;
		} else {
			ratio = 1;
		}
		switch (ticket.getParkingSpot().getParkingType()) {
			case CAR:
				ticket.setPrice(duration * Fare.CAR_RATE_PER_HOUR * ratio);
				break;

			case BIKE:
				ticket.setPrice(duration * Fare.BIKE_RATE_PER_HOUR * ratio);
				break;

			default:
				throw new IllegalArgumentException("Unkown Parking Type");

		}
	}
}
