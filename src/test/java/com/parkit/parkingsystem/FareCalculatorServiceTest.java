package com.parkit.parkingsystem;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.service.FareCalculatorService;

@ExtendWith(MockitoExtension.class)
public class FareCalculatorServiceTest {
	private final static int HOUR_IN_MILLISECOND = 60 * 60 * 1000;
	private final static int MINUTE_IN_MILLISECOND = 60 * 1000;

	@Mock
	private static TicketDAO ticketDAO;
	@InjectMocks
	private static FareCalculatorService fareCalculatorService;
	private Ticket ticket;

	/**
	 * Before all tests a new ticketdao and
	 * a fareCalculator are initialized.
	 */
	@BeforeAll
	private static void setUp() {
		ticketDAO = new TicketDAO();
		fareCalculatorService = new FareCalculatorService();
	}

	/**
	 * Before each tests a new ticket is created.
	 */
	@BeforeEach
	private void setUpPerTest() {
		ticket = new Ticket();
	}

	/**
	 * This test verifies that the ticket fare is correct
	 * for a car staying in the parking for an hour.
	 */
	@Test
	public void calculateFareCar() {
		Date inTime = new Date();
		inTime.setTime(System.currentTimeMillis() - HOUR_IN_MILLISECOND);
		Date outTime = new Date();
		ParkingSpot parkingSpot = new ParkingSpot(
				1, ParkingType.CAR, false);
		ticket.setInTime(inTime);
		ticket.setOutTime(outTime);
		ticket.setParkingSpot(parkingSpot);
		fareCalculatorService.calculateFare(ticket);
		assertEquals(ticket.getPrice(), Fare.CAR_RATE_PER_HOUR);
	}

	/**
	 * This test verifies that the ticket fare is correct
	 * for a bike staying in the parking for an hour.
	 */
	@Test
	public void calculateFareBike() {
		Date inTime = new Date();
		// The bike stayed 1 hour.
		inTime.setTime(System.currentTimeMillis() - (HOUR_IN_MILLISECOND));
		Date outTime = new Date();
		ParkingSpot parkingSpot = new ParkingSpot(
				1, ParkingType.BIKE, false);
		ticket.setInTime(inTime);
		ticket.setOutTime(outTime);
		ticket.setParkingSpot(parkingSpot);
		fareCalculatorService.calculateFare(ticket);
		assertEquals(ticket.getPrice(), Fare.BIKE_RATE_PER_HOUR);
	}

	/**
	 * This test verifies that an error is thrown if a bike
	 * *as an in time greater than the out time.
	 */
	@Test
	public void calculateFareBikeWithFutureInTime() {
		Date inTime = new Date();
		//Adding an hour to the ticket.
		inTime.setTime(System.currentTimeMillis() + (HOUR_IN_MILLISECOND));
		Date outTime = new Date();
		ParkingSpot parkingSpot = new ParkingSpot(
				1, ParkingType.BIKE, false);
		ticket.setInTime(inTime);
		ticket.setOutTime(outTime);
		ticket.setParkingSpot(parkingSpot);
		assertThrows(IllegalArgumentException.class,
				() -> fareCalculatorService.calculateFare(ticket));
	}

	/**
	 * This test verifies that the ticket fare is correct
	 * for a car staying in the parking for less than an hour.
	 */
	@Test
	public void calculateFareBikeWithLessThanOneHourParkingTime() {
		Date inTime = new Date();
		//The bike stayed 45 minutes.
		inTime.setTime(System.currentTimeMillis() - (45 * MINUTE_IN_MILLISECOND));
		Date outTime = new Date();
		ParkingSpot parkingSpot = new ParkingSpot(
				1, ParkingType.BIKE, false);
		ticket.setInTime(inTime);
		ticket.setOutTime(outTime);
		ticket.setParkingSpot(parkingSpot);
		fareCalculatorService.calculateFare(ticket);
		assertEquals((0.75 * Fare.BIKE_RATE_PER_HOUR),
				ticket.getPrice());
	}

	/**
	 * This test verifies that the ticket fare is correct
	 * for a car staying in the parking for less than an hour.
	 */
	@Test
	public void calculateFareCarWithLessThanOneHourParkingTime() {
		Date inTime = new Date();
		//The car stayed 45 minutes.
		inTime.setTime(System.currentTimeMillis() - (45 * MINUTE_IN_MILLISECOND ));
		Date outTime = new Date();
		ParkingSpot parkingSpot = new ParkingSpot(
				1, ParkingType.CAR, false);
		ticket.setInTime(inTime);
		ticket.setOutTime(outTime);
		ticket.setParkingSpot(parkingSpot);
		fareCalculatorService.calculateFare(ticket);
		assertEquals((0.75 * Fare.CAR_RATE_PER_HOUR),
				ticket.getPrice());
	}

	/**
	 * This test verifies that the ticket fare is free
	 * for a bike staying in the parking for less than half an hour.
	 */
	@Test
	public void calculateFareCarWithLessThanHalfAnHourParkingTime() {
		Date inTime = new Date();
		//The car stayed 29 minutes.
		inTime.setTime(System.currentTimeMillis() - (29 * MINUTE_IN_MILLISECOND ));
		Date outTime = new Date();
		ParkingSpot parkingSpot = new ParkingSpot(
				1, ParkingType.CAR, false);
		ticket.setInTime(inTime);
		ticket.setOutTime(outTime);
		ticket.setParkingSpot(parkingSpot);
		fareCalculatorService.calculateFare(ticket);
		assertEquals((0), ticket.getPrice());
	}

	/**
	 * This test verifies that the ticket fare is free for a bike staying in the parking for less than half an hour.
	 */
	@Test
	public void calculateFareBikeWithLessThanHalfAnHourParkingTime() {
		Date inTime = new Date();
		//The bike stayed 29 minutes.
		inTime.setTime(System.currentTimeMillis() - (29 * MINUTE_IN_MILLISECOND ));
		Date outTime = new Date();
		ParkingSpot parkingSpot = new ParkingSpot(
				1, ParkingType.BIKE, false);
		ticket.setInTime(inTime);
		ticket.setOutTime(outTime);
		ticket.setParkingSpot(parkingSpot);
		fareCalculatorService.calculateFare(ticket);
		assertEquals((0), ticket.getPrice());
	}

	/**
	 * This test verifies that the ticket fare for
	 * a bike staying in the parking for less than half an hour.
	 */
	@Test
	public void calculateFareCarWithMoreThanADayParkingTime() {
		Date inTime = new Date();
		//The car stayed 24 hours.
		inTime.setTime(System.currentTimeMillis()
				- (24 * HOUR_IN_MILLISECOND));
		Date outTime = new Date();
		ParkingSpot parkingSpot = new ParkingSpot(
				1, ParkingType.CAR, false);
		ticket.setInTime(inTime);
		ticket.setOutTime(outTime);
		ticket.setParkingSpot(parkingSpot);
		fareCalculatorService.calculateFare(ticket);
		assertEquals((24 * Fare.CAR_RATE_PER_HOUR), ticket.getPrice());
	}

	/**
	 * This test verifies that the ticket fare of a car who
	 * already came in the parking will have a 5% discount.
	 */
	@Test
	public void calculateFareExistingCar() {
		//GIVEN
		Date inTime = new Date();
		//The car stayed 1 hour.
		inTime.setTime(System.currentTimeMillis()
				- (60 * 60 * 1000));
		Date outTime = new Date();
		ParkingSpot parkingSpot = new ParkingSpot(
				1, ParkingType.CAR, false);
		ticket.setInTime(inTime);
		ticket.setOutTime(outTime);
		ticket.setParkingSpot(parkingSpot);
		// WHEN
		when(ticketDAO.checkExistingVehicle(any())).thenReturn(true);
		fareCalculatorService.calculateFare(ticket);
		//THEN
		assertEquals((0.95 * Fare.CAR_RATE_PER_HOUR),
				ticket.getPrice());
	}

}
