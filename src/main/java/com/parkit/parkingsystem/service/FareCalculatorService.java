package com.parkit.parkingsystem.service;

import java.util.Date;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.model.Ticket;

public class FareCalculatorService {

	public void calculateFare(Ticket ticket){
		if( (ticket.getOutTime() == null) || (ticket.getOutTime().before(ticket.getInTime())) ){
			throw new IllegalArgumentException("Out time provided is incorrect:"+ticket.getOutTime().toString());
		}

		Date inHour = ticket.getInTime();
		Date outHour = ticket.getOutTime();

		double duration = outHour.getTime() - inHour.getTime();
		duration = duration / (60*60*1000);


		if (duration < 0.5) {
			switch (ticket.getParkingSpot().getParkingType()){
			case CAR: {
				ticket.setPrice (0.0);
				break;
			}
			case BIKE: {
				ticket.setPrice(0.0);
				break;
			}
			default: throw new IllegalArgumentException("Unkown Parking Type");
			}
		}
		else {
			switch (ticket.getParkingSpot().getParkingType()){
			case CAR: {
				ticket.setPrice (duration * Fare.CAR_RATE_PER_HOUR);
				break;
			}
			case BIKE: {
				ticket.setPrice(duration * Fare.BIKE_RATE_PER_HOUR);
				break;
			}
			default: throw new IllegalArgumentException("Unkown Parking Type");
			}
		}
	}
}
