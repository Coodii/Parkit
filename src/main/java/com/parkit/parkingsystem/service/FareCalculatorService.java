package com.parkit.parkingsystem.service;

import java.util.Date;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.model.Ticket;

public class FareCalculatorService {		

	private TicketDAO ticketDAO;
	
	public FareCalculatorService () {
		ticketDAO = new TicketDAO();
	}
	
	public void calculateFare(Ticket ticket){
		if( (ticket.getOutTime() == null) || (ticket.getOutTime().before(ticket.getInTime())) ){
			throw new IllegalArgumentException("Out time provided is incorrect:"+ticket.getOutTime().toString());
		}

		Date inHour = ticket.getInTime();
		Date outHour = ticket.getOutTime();
		
		double duration = outHour.getTime() - inHour.getTime();
		duration = duration / (60*60*1000);
		double ratio;
		
		if ( duration < 0.5) {
			ratio = 0;
		} else if (ticketDAO.checkExistingVehicle(ticket)){
			ratio = 0.95;
		} else {
			ratio = 1;
		}
		
		switch (ticket.getParkingSpot().getParkingType()){
		case CAR: {
			ticket.setPrice (duration * Fare.CAR_RATE_PER_HOUR * ratio);
			break;
		}
		case BIKE: {
			ticket.setPrice(duration * Fare.BIKE_RATE_PER_HOUR * ratio);
			break;
		}
		default: throw new IllegalArgumentException("Unkown Parking Type");
		}
	}
}
