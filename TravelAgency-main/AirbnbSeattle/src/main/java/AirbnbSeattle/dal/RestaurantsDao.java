package AirbnbSeattle.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import AirbnbSeattle.model.Restaurants;
import AirbnbSeattle.model.ThingsToDo;
import AirbnbSeattle.model.ZipCode;

public class RestaurantsDao extends ThingsToDoDao{

	protected ConnectionManager connectionManager;

	private static RestaurantsDao instance = null;
	protected RestaurantsDao() {
		connectionManager = new ConnectionManager();
	}
	public static RestaurantsDao getInstance() {
		if(instance == null) {
			instance = new RestaurantsDao();
		}
		return instance;
	}
	
	public Restaurants create(Restaurants restaurant) throws SQLException {
		create(new ThingsToDo(restaurant.getName(), restaurant.getAddress(), restaurant.getLongitude(), restaurant.getLatitude(),restaurant.getZipCode()));
		String insertRestaurant = "INSERT INTO Restaurant(Name,Address) VALUES(?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertRestaurant);
			insertStmt.setString(1, restaurant.getName());
			insertStmt.setString(2, restaurant.getAddress());
			
			insertStmt.executeUpdate();
			return restaurant;
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

	public Restaurants getRestaurantByName(String name) throws SQLException {
		String selectRestaurant =
			"SELECT Restaurant.Name,Restaurant.Address,Longitude,Latitude,ZipCodeId  " +
			"FROM Restaurant INNER JOIN ThingsToDo " +
			"  ON Restaurant.Name = ThingsToDo.Name " +
			"WHERE Restaurant.Name=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectRestaurant);
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
				Restaurants restaurant = new Restaurants(resultName, address, longitude, latitude, zipCode);
				return restaurant;
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
	
	public Restaurants delete(Restaurants restaurant) throws SQLException {
		String deleteRestaurant = "DELETE FROM Restaurant WHERE Name=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteRestaurant);
			deleteStmt.setString(1, restaurant.getName());
			int affectedRows = deleteStmt.executeUpdate();
			if (affectedRows == 0) {
				throw new SQLException("No records available to delete for Name=" + restaurant.getName());
			}
			super.delete(restaurant);

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
