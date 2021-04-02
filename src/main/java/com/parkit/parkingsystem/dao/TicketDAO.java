package com.parkit.parkingsystem.dao;


import com.parkit.parkingsystem.config.DataBaseConfig;
import com.parkit.parkingsystem.constants.DBConstants;
import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

public class TicketDAO {

	private static final Logger logger = LogManager.getLogger("TicketDAO");

	public static DataBaseConfig dataBaseConfig = new DataBaseConfig();

	public boolean saveTicket(Ticket ticket){
		Connection con = null;
		try {
			con = dataBaseConfig.getConnection();
			PreparedStatement ps = con.prepareStatement(DBConstants.SAVE_TICKET);
			//ID, PARKING_NUMBER, VEHICLE_REG_NUMBER, PRICE, IN_TIME, OUT_TIME)
			//ps.setInt(1,ticket.getId());
			ps.setInt(1,ticket.getParkingSpot().getId());
			ps.setString(2, ticket.getVehicleRegNumber());
			ps.setDouble(3, ticket.getPrice());
			ps.setTimestamp(4, new Timestamp(ticket.getInTime().getTime()));
			ps.setTimestamp(5, (ticket.getOutTime() == null)?null: (new Timestamp(ticket.getOutTime().getTime())) );
			return ps.execute();
		}catch (Exception ex){
			logger.error("Error fetching next available slot",ex);
		}finally {
			dataBaseConfig.closeConnection(con);
			return false;
		}
	}

	public Ticket getTicket(String vehicleRegNumber) {
		Connection con = null;
		Ticket ticket = null;
		try {
			con = dataBaseConfig.getConnection();
			PreparedStatement ps = con.prepareStatement(DBConstants.GET_TICKET);
			//ID, PARKING_NUMBER, VEHICLE_REG_NUMBER, PRICE, IN_TIME, OUT_TIME)
			ps.setString(1,vehicleRegNumber);
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				ticket = new Ticket();
				ParkingSpot parkingSpot = new ParkingSpot(rs.getInt(1), ParkingType.valueOf(rs.getString(6)),false);
				ticket.setParkingSpot(parkingSpot);
				ticket.setId(rs.getInt(2));
				ticket.setVehicleRegNumber(vehicleRegNumber);
				ticket.setPrice(rs.getDouble(3));
				ticket.setInTime(rs.getTimestamp(4));
				ticket.setOutTime(rs.getTimestamp(5));
			}
			dataBaseConfig.closeResultSet(rs);
			dataBaseConfig.closePreparedStatement(ps);
		}catch (Exception ex){
			logger.error("Error fetching next available slot",ex);
		}finally {
			dataBaseConfig.closeConnection(con);
			return ticket;
		}
	}

	public boolean updateTicket(Ticket ticket) {
		Connection con = null;
		try {
			con = dataBaseConfig.getConnection();
			PreparedStatement ps = con.prepareStatement(DBConstants.UPDATE_TICKET);
			ps.setDouble(1, ticket.getPrice());
			ps.setTimestamp(2, new Timestamp(ticket.getOutTime().getTime()));
			ps.setInt(3,ticket.getId());
			ps.execute();
			return true;
		}catch (Exception ex){
			logger.error("Error saving ticket info",ex);
		}finally {
			dataBaseConfig.closeConnection(con);
		}
		return false;
	}

	public void checkExistingVehicle (Ticket ticket) {
		Connection con = null;
		try {		
			con = dataBaseConfig.getConnection();
			String vehicle = ticket.getVehicleRegNumber();
			PreparedStatement ps = con.prepareStatement(DBConstants.GET_AN_EXISTING_CAR);
			ps.setString(1, vehicle);
			ResultSet rs = ps.executeQuery();
			//			Statement stmt = con.createStatement();
			//			//String SQL = "SELECT count(*) as VEHICLE_REG_NUMBER FROM prod.ticket where VEHICLE_REG_NUMBER = '" + vehicle +"' having count(*)>2";
			//			//String SQL = "SELECT * FROM prod.ticket where VEHICLE_REG_NUMBER = '" + vehicle + "' AND OUT_TIME";
			//			ResultSet rs = stmt.executeQuery(SQL);
			if(rs.next()){
				ticket.setPrice(ticket.getPrice()*0.90);
			}
			else {
				ticket.getPrice();
			}}catch (Exception ex) {
				logger.error("Error trying to verify existing Vehicle number", ex);
			}
	}
}
