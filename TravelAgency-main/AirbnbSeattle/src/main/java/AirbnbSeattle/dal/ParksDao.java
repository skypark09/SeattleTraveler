package AirbnbSeattle.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import AirbnbSeattle.model.Parks;
import AirbnbSeattle.model.ThingsToDo;
import AirbnbSeattle.model.ZipCode;

public class ParksDao extends ThingsToDoDao{

	protected ConnectionManager connectionManager;

	private static ParksDao instance = null;
	protected ParksDao() {
		connectionManager = new ConnectionManager();
	}
	public static ParksDao getInstance() {
		if(instance == null) {
			instance = new ParksDao();
		}
		return instance;
	}
	
	public Parks create(Parks park) throws SQLException {
		create(new ThingsToDo(park.getName(), park.getAddress(), park.getLongitude(), park.getLatitude(), park.getZipCode()));
		String insertRestaurant = "INSERT INTO Park(Name,Address) VALUES(?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertRestaurant);
			insertStmt.setString(1, park.getName());
			insertStmt.setString(2, park.getAddress());		
			insertStmt.executeUpdate();
			return park;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(insertStmt != null) {
				insertStmt.close();
			}
		}
	}

	public Parks getParkByName(String name) throws SQLException {
		String selectPark =
			"SELECT Park.Name,Park.Address,Longitude,Latitude,ZipCodeId  " +
			"FROM Park INNER JOIN ThingsToDo " +
			"  ON Park.Name = ThingsToDo.Name " +
			"WHERE Park.Name=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectPark);
			selectStmt.setString(1, name);
			results = selectStmt.executeQuery();
			ZipCodeDao zipCodeDao = ZipCodeDao.getInstance();
			if(results.next()) {
				String resultName = results.getString("Name");
				String address = results.getString("Address");
				Float longitude = results.getFloat("Longitude");
				Float latitude = results.getFloat("Latitude");
				int zipCodeId = results.getInt("ZipCodeId");
				ZipCode zipCode = zipCodeDao.getZipCodeByZipCodeId(zipCodeId);
				Parks park = new Parks(resultName, address, longitude, latitude, zipCode);
				return park;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(selectStmt != null) {
				selectStmt.close();
			}
			if(results != null) {
				results.close();
			}
		}
		return null;
	}
	
	public Parks delete(Parks park) throws SQLException {
		String deleteRestaurant = "DELETE FROM Park WHERE Name=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteRestaurant);
			deleteStmt.setString(1, park.getName());
			int affectedRows = deleteStmt.executeUpdate();
			if (affectedRows == 0) {
				throw new SQLException("No records available to delete for Name=" + park.getName());
			}
			super.delete(park);

			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(deleteStmt != null) {
				deleteStmt.close();
			}
		}
	}
	
}
