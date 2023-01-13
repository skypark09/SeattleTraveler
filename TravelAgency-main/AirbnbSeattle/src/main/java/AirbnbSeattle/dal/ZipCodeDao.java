
package AirbnbSeattle.dal;

import AirbnbSeattle.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ZipCodeDao {
	protected ConnectionManager connectionManager;
	
	// Single pattern: instantiation is limited to one object.
	private static ZipCodeDao instance = null;
	protected ZipCodeDao() {
		connectionManager = new ConnectionManager();
	}
	public static ZipCodeDao getInstance() {
		if(instance == null) {
			instance = new ZipCodeDao();
		}
		return instance;
	}

	/**
	 * Save the ZipCode instance by storing it in your MySQL instance.
	 * This runs a INSERT statement.
	 */
	public ZipCode create(ZipCode zipCode) throws SQLException {
		String insertZipCode = "INSERT INTO ZipCode(ZipCodeId, ZipCodeNumber) VALUES(?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertZipCode);

			insertStmt.setInt(1, zipCode.getZipCodeId());
			insertStmt.setInt(2, zipCode.getZipCodeNumber());
			insertStmt.executeUpdate();
			
			// Retrieve the auto-generated key and set it, so it can be used by the caller.
			// For more details, see:
			// http://dev.mysql.com/doc/connector-j/en/connector-j-usagenotes-last-insert-id.html
			resultKey = insertStmt.getGeneratedKeys();
			int zipCodeId = -1;
				if(resultKey.next()) {
					zipCodeId = resultKey.getInt(1);
				} else {
					throw new SQLException("Unable to retrieve auto-generated key.");
				}
				zipCode.setZipCodeId(zipCodeId);
				return zipCode;
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
				if(resultKey != null) {
					resultKey.close();
				}
			}
		}



	/**
	 * Get ZipCode by ZipCodeNumber
	 */
	public ZipCode getZipCodeByZipCodeNumber(int zipCodeNumber) throws SQLException {
		String selectZipCode = "SELECT ZipCodeId, ZipCodeNumber FROM ZipCode WHERE ZipCodeNumber=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectZipCode);
			selectStmt.setInt(1, zipCodeNumber);
			// Note that we call executeQuery(). This is used for a SELECT statement
			
			results = selectStmt.executeQuery();
			// You can iterate the result set 
			if(results.next()) {
				int resultZipCodeNumber = results.getInt("ZipCodeNumber");
				int zipCodeId = results.getInt("ZipCodeId");

				ZipCode zipCode = new ZipCode(zipCodeId, resultZipCodeNumber);
				return zipCode;
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
	
	/**
	 * Get ZipCode by ZipCodeId
	 */
	public ZipCode getZipCodeByZipCodeId(int zipCodeId) throws SQLException {
		String selectZipCode = "SELECT ZipCodeId, ZipCodeNumber FROM ZipCode WHERE ZipCodeId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectZipCode);
			selectStmt.setInt(1, zipCodeId);
			// Note that we call executeQuery(). This is used for a SELECT statement
			
			results = selectStmt.executeQuery();
			// You can iterate the result set 
			if(results.next()) {
				int resultZipCodeId = results.getInt("ZipCodeId");
				int zipCodeNumber = results.getInt("ZipCodeNumber");

				ZipCode zipCode = new ZipCode(resultZipCodeId, zipCodeNumber);
				return zipCode;
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
	
	public List<ZipCode> getTop10ThingsToDoZipCode() throws SQLException{
		List<ZipCode> topTen = new ArrayList<ZipCode>();
		String selectZipCode = "SELECT ZipCode.ZipCodeId, ZipCodeNumber, COUNT(ThingsToDo.ZipCodeId) as CNT "
				+ "FROM ThingsToDo JOIN Zipcode ON ThingsToDo.ZipCodeId = Zipcode.ZipCodeId "
				+ "GROUP BY ThingsToDo.ZipCodeId "
				+ "ORDER BY CNT DESC "
				+ "LIMIT 10;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectZipCode);
			// Note that we call executeQuery(). This is used for a SELECT statement
		
			results = selectStmt.executeQuery();
			// You can iterate the result set 
			while(results.next()) {
				int resultZipCodeId = results.getInt("ZipCode.ZipCodeId");
				int zipCodeNumber = results.getInt("ZipCodeNumber");

				ZipCode zipCode = new ZipCode(resultZipCodeId, zipCodeNumber);
				topTen.add(zipCode);
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
		return topTen;
	}
	
	public List<ZipCode> getTop10ListingZipCode() throws SQLException{
		List<ZipCode> topTen = new ArrayList<ZipCode>();
		String selectZipCode = "SELECT ZipCode.ZipCodeId, ZipCodeNumber, COUNT(ZipCode.ZipCodeId) as CNT"
				+ " FROM AirbnbListing JOIN Neighborhood ON AirbnbListing.NeighborhoodId = Neighborhood.NeighborhoodId"
				+" JOIN ZipCode ON Neighborhood.ZipCodeId = ZipCode.ZipCodeId"
				+ " GROUP BY ZipCode.ZipCodeId "
				+ " ORDER BY CNT DESC "
				+ "LIMIT 10;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectZipCode);
			// Note that we call executeQuery(). This is used for a SELECT statement
		
			results = selectStmt.executeQuery();
			// You can iterate the result set 
			while(results.next()) {
				int resultZipCodeId = results.getInt("ZipCode.ZipCodeId");
				int zipCodeNumber = results.getInt("ZipCodeNumber");

				ZipCode zipCode = new ZipCode(resultZipCodeId, zipCodeNumber);
				topTen.add(zipCode);
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
		return topTen;
	}
	/**
	 * Delete the ZipCode instance.
	 * This runs a DELETE statement.
	 */
	public ZipCode delete(ZipCode zipCode) throws SQLException {
		String deleteZipCode = "DELETE FROM ZipCode WHERE ZipCodeId=?;";
		String deleteThingsToDo = "DELETE FROM ThingsToDo WHERE ZipCodeId=?;";
		Connection connection = null;
		Connection connection2 = null;
		PreparedStatement deleteStmt = null;
		PreparedStatement deleteStmt2 = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteZipCode);
			deleteStmt.setInt(1, zipCode.getZipCodeId());
			deleteStmt.executeUpdate();
			try {
				connection2 = connectionManager.getConnection();
				deleteStmt2 = connection2.prepareStatement(deleteThingsToDo);
				deleteStmt2.setInt(1, zipCode.getZipCodeId());
				deleteStmt2.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
				throw e;
			} finally {
				if(connection2 != null) {
					connection.close();
				}
				if(deleteStmt2 != null) {
					deleteStmt.close();
				}
			}
			
			// Return null so the caller can no longer operate on the User instance.
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
