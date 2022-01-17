package com.parkit.parkingsystem;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.service.ParkingService;
import com.parkit.parkingsystem.util.InputReaderUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ParkingServiceTest {

	/**
	 * Declare a new parking service.
	 */
	private static ParkingService parkingService;

	/**
	 * Mocking InputReaderUtil.
	 */
	@Mock
	private static InputReaderUtil inputReaderUtil;
	/**
	 * Mocking ParkingSpotDAO.
	 */
	@Mock
	private static ParkingSpotDAO parkingSpotDAO;
	/**
	 * Mocking TicketDAO.
	 */
	@Mock
	private static TicketDAO ticketDAO;

	/**
	 * Initialise a new parking service before each test.
	 */
	@BeforeEach
	private void setUpPerTest() {
		try {
			parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to set up test mock objects");
		}
	}

	/**
	 * This test verifies that the processIncoming methods
	 * is working correctly.
	 * @throws Exception
	 */
	@Test
	public void processIncomingVehicleTest() throws Exception {
		when(parkingSpotDAO.getNextAvailableSlot(any())).thenReturn(3);
		when(inputReaderUtil.readSelection()).thenReturn(2);
		when(inputReaderUtil.readVehicleRegistrationNumber()).thenReturn("ABCDEF");
		parkingService.processIncomingVehicle();
		verify(parkingSpotDAO, Mockito.times(1)).updateParking(any(ParkingSpot.class));
	}

	/**
	 * This test verifies that the processExitingVehicle
	 * is working correctly.
	 * @throws Exception
	 */
	@Test
	public void processExitingVehicleTest() throws Exception {
		when(inputReaderUtil.readVehicleRegistrationNumber()).thenReturn("ABCDEF");
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);
		Ticket ticket = new Ticket();
		ticket.setInTime(new Date(System.currentTimeMillis() - (60 * 60 * 1000)));
		ticket.setParkingSpot(parkingSpot);
		ticket.setVehicleRegNumber("ABCDEF");
		when(ticketDAO.getTicket(anyString())).thenReturn(ticket);
		when(ticketDAO.updateTicket(any(Ticket.class))).thenReturn(true);
		when(parkingSpotDAO.updateParking(any(ParkingSpot.class))).thenReturn(true);
		parkingService.processExitingVehicle();
		verify(parkingSpotDAO, Mockito.times(1)).updateParking(any(ParkingSpot.class));
	}

	/**
	 * This test verifies that the method getNextParkingNumberIfAvailable
	 * is working correctly.
	 */
	@Test
	public void getNextParkingNumberIfAvailableTest() {
		when(parkingSpotDAO.getNextAvailableSlot(any())).thenReturn(1);
		when(inputReaderUtil.readSelection()).thenReturn(1);
		ParkingSpot parkingSpot = parkingService.getNextParkingNumberIfAvailable();
		Assertions.assertEquals(1, parkingSpot.getId());
		Assertions.assertEquals(ParkingType.CAR, parkingSpot.getParkingType());
		Assertions.assertTrue(parkingSpot.isAvailable());
	}

	/**
	 * This test verifies that the method getNextParkingNumberIfAvailable
	 * returns no parking spot if no slots are available.
	 */
	@Test
	public void getNoNextParkingNumberAvailableTest() {
			when(parkingSpotDAO.getNextAvailableSlot(any())).thenReturn(0);
			when(inputReaderUtil.readSelection()).thenReturn(1);
			ParkingSpot parkingSpot = parkingService.getNextParkingNumberIfAvailable();
			Assertions.assertNull(parkingSpot);
	}

	/**
	 * This test verifies that no parking spot are
	 * returned if the input is incorrect.
	 */
	@Test
	public void notCorrectInputTest() {
		when(inputReaderUtil.readSelection()).thenReturn(3);
		ParkingSpot parkingSpot = parkingService.getNextParkingNumberIfAvailable();
		Assertions.assertNull(parkingSpot);

	}
}







