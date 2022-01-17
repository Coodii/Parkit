package com.parkit.parkingsystem.dao;


import com.parkit.parkingsystem.config.DataBaseConfig;
import com.parkit.parkingsystem.constants.DBConstants;
import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

public class TicketDAO {

	/**
	 * Initialise a new logger.
	 */
	private static final Logger LOGGER = LogManager.getLogger("TicketDAO");

	/**
	 * Initialise a new dataBaseConfig.
	 */
	public DataBaseConfig dataBaseConfig = new DataBaseConfig();

	/**
	 * This method save the ticket to the database.
	 */
	public boolean saveTicket(Ticket ticket) {
		Connection con = null;
		PreparedStatement ps = null;
		boolean retValue = false;
		try {
			con = dataBaseConfig.getConnection();
			ps = con.prepareStatement(DBConstants.SAVE_TICKET);
			//ID, PARKING_NUMBER, VEHICLE_REG_NUMBER, PRICE, IN_TIME, OUT_TIME)
			//ps.setInt(1,ticket.getId());
			ps.setInt(1,ticket.getParkingSpot().getId());
			ps.setString(2, ticket.getVehicleRegNumber());
			ps.setDouble(3, ticket.getPrice());
			ps.setTimestamp(4, new Timestamp(ticket.getInTime().getTime()));
			ps.setTimestamp(5, (ticket.getOutTime() == null) ? null : (new Timestamp(ticket.getOutTime().getTime())));
			retValue = ps.execute();
		} catch (Exception ex) {
			LOGGER.error("Error fetching next available slot", ex);
		} finally {
			try { ps.close(); } catch (SQLException e) {}
			dataBaseConfig.closeConnection(con);
		}
		return retValue;
	}

	/**
	 * This method gets the ticket from the database.
	 */
	public Ticket getTicket(String vehicleRegNumber) {
		Connection con = null;
		Ticket ticket = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = dataBaseConfig.getConnection();
			ps = con.prepareStatement(DBConstants.GET_TICKET);
			//ID, PARKING_NUMBER, VEHICLE_REG_NUMBER, PRICE, IN_TIME, OUT_TIME)
			ps.setString(1,vehicleRegNumber);
			rs = ps.executeQuery();
			if (rs.next()) {
				ticket = new Ticket();
				ParkingSpot parkingSpot = new ParkingSpot(rs.getInt(1), ParkingType.valueOf(rs.getString(6)),false);
				ticket.setParkingSpot(parkingSpot);
				ticket.setId(rs.getInt(2));
				ticket.setVehicleRegNumber(vehicleRegNumber);
				ticket.setPrice(rs.getDouble(3));
				ticket.setInTime(rs.getTimestamp(4));
				ticket.setOutTime(rs.getTimestamp(5));
			}
		} catch (Exception ex) {
			LOGGER.error("Error fetching next available slot", ex);
		} finally {
			dataBaseConfig.closeResultSet(rs);
			dataBaseConfig.closePreparedStatement(ps);
			dataBaseConfig.closeConnection(con);
			return ticket;
		}
	}
	/**
	 * This method updates the ticket in the database.
	 * @param ticket a ticket
	 * @return boolean
	 */
	public boolean updateTicket(Ticket ticket) {
		Connection con = null;
		PreparedStatement ps = null;
		boolean retValue = false;
		try {
			con = dataBaseConfig.getConnection();
			ps = con.prepareStatement(DBConstants.UPDATE_TICKET);
			ps.setDouble(1, ticket.getPrice());
			ps.setTimestamp(2, new Timestamp(ticket.getOutTime().getTime()));
			ps.setInt(3, ticket.getId());
			ps.execute();
			retValue = true;
		} catch (Exception ex) {
			LOGGER.error("Error saving ticket info", ex);
		} finally {
			dataBaseConfig.closePreparedStatement(ps);
			dataBaseConfig.closeConnection(con);
		}
		return retValue;
	}
	/**
	 * This method checks if the vehicle is already in the database.
	 * @param ticket a ticket
	 * @return boolean
	 */
	public boolean checkExistingVehicle (Ticket ticket) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean retValue = false;
		try {		
			con = dataBaseConfig.getConnection();
			String vehicle = ticket.getVehicleRegNumber();
			ps = con.prepareStatement(DBConstants.GET_AN_EXISTING_VEHICLE);
			ps.setString(1, vehicle);
			rs = ps.executeQuery();
			if (rs.next()) {
				retValue = true;
			}
		} catch (Exception ex) {
			LOGGER.error("Error trying to verify existing Vehicle number", ex);
		} finally {
			dataBaseConfig.closePreparedStatement(ps);
			dataBaseConfig.closeResultSet(rs);
			dataBaseConfig.closeConnection(con);
		} return retValue;
	}
}

