package com.parkit.parkingsystem;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.service.ParkingService;
import com.parkit.parkingsystem.util.InputReaderUtil;
import org.junit.internal.runners.statements.Fail;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.opentest4j.AssertionFailedError;

import java.util.Date;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ParkingServiceTest {

	private static ParkingService parkingService;

	@Mock
	private static InputReaderUtil inputReaderUtil;
	@Mock
	private static ParkingSpotDAO parkingSpotDAO;
	@Mock
	private static TicketDAO ticketDAO;

	@BeforeEach
	private void setUpPerTest() {
		try {
			//when(inputReaderUtil.readVehicleRegistrationNumber()).thenReturn("ABCDEF");
			//ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR,false);
			//Ticket ticket = new Ticket();
			//ticket.setInTime(new Date(System.currentTimeMillis() - (60*60*1000)));
			//ticket.setParkingSpot(parkingSpot);
			//ticket.setVehicleRegNumber("ABCDEF");
			//when(ticketDAO.getTicket(anyString())).thenReturn(ticket);
			//when(ticketDAO.updateTicket(any(Ticket.class))).thenReturn(true);
			//when(parkingSpotDAO.updateParking(any(ParkingSpot.class))).thenReturn(true);

			parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to set up test mock objects");
		}
	}

	@Test
	public void processIncomingVehicleTest() throws Exception {
		when(parkingSpotDAO.getNextAvailableSlot(any())).thenReturn(3);
		when(inputReaderUtil.readSelection()).thenReturn(2);
		when(inputReaderUtil.readVehicleRegistrationNumber()).thenReturn("ABCDEF");
		parkingService.processIncomingVehicle();
		verify(parkingSpotDAO, Mockito.times(1)).updateParking(any(ParkingSpot.class));
	}

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

	@Test
	public void getNextParkingNumberIfAvailableTest() {
		when(parkingSpotDAO.getNextAvailableSlot(any())).thenReturn(1);
		when(inputReaderUtil.readSelection()).thenReturn(1);
		ParkingSpot parkingSpot = parkingService.getNextParkingNumberIfAvailable();
		Assertions.assertEquals(1, parkingSpot.getId());
		Assertions.assertEquals(ParkingType.CAR, parkingSpot.getParkingType());
		Assertions.assertTrue(parkingSpot.isAvailable());
	}

	@Test
	public void getNoNextParkingNumberAvailableTest() {
			when(parkingSpotDAO.getNextAvailableSlot(any())).thenReturn(0);
			when(inputReaderUtil.readSelection()).thenReturn(1);
			ParkingSpot parkingSpot = parkingService.getNextParkingNumberIfAvailable();
			Assertions.assertNull(parkingSpot);
	}

	@Test
	public void notCorrectInputTest(){
		when(inputReaderUtil.readSelection()).thenReturn(3);
		ParkingSpot parkingSpot = parkingService.getNextParkingNumberIfAvailable();
		Assertions.assertNull(parkingSpot);

	}
}







