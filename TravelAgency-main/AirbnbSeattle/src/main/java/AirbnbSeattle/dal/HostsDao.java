package AirbnbSeattle.dal;

import AirbnbSeattle.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

public class HostsDao {
	protected static ConnectionManager connectionManager;
	
	// Single pattern: instantiation is limited to one object.
	private static HostsDao instance = null;
	protected HostsDao() {
		connectionManager = new ConnectionManager();
	}
	public static HostsDao getInstance() {
		if(instance == null) {
			instance = new HostsDao();
		}
		return instance;
	}

	/**
	 * Save the Hosts instance by storing it in your MySQL instance.
	 * This runs a INSERT statement.
	 */
	public Hosts create(Hosts host) throws SQLException {
		String insertHosts = "INSERT INTO Hosts(HostUrl, HostName, Since, About, ResponseRate, AcceptanceRate) VALUES(?,?,?,?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertHosts,
					Statement.RETURN_GENERATED_KEYS);

			insertStmt.setString(1, host.getHostUrl());
			insertStmt.setString(2, host.getHostName());
			insertStmt.setTimestamp(3, new Timestamp(host.getSince().getTime()));
			insertStmt.setString(4, host.getAbout());
			insertStmt.setInt(5, host.getResponseRate());
			insertStmt.setInt(6, host.getAcceptanceRate());

			insertStmt.executeUpdate();
		
		// Retrieve the auto-generated key and set it, so it can be used by the caller.
		// For more details, see:
		// http://dev.mysql.com/doc/connector-j/en/connector-j-usagenotes-last-insert-id.html
			resultKey = insertStmt.getGeneratedKeys();
			int hostId = -1;
			if(resultKey.next()) {
				hostId = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			host.setHostId(hostId);
			return host;
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
	 * Get Host by HostId
	 */
	public Hosts getHostByHostId(int hostId) throws SQLException {
		String selectHosts = "SELECT HostId, HostUrl, HostName, Since, About, ResponseRate, AcceptanceRate FROM Hosts WHERE HostId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectHosts);
			selectStmt.setInt(1, hostId);
			// Note that we call executeQuery(). This is used for a SELECT statement
			
			results = selectStmt.executeQuery();
			// You can iterate the result set 
			if(results.next()) {
				int resultHostId  = results.getInt("HostId");
				String hostUrl = results.getString("HostUrl");
				String hostName = results.getString("HostName");
				java.sql.Date since = results.getDate("Since");
				String about = results.getString("About");
				int responseRate = results.getInt("ResponseRate");
				int acceptanceRate = results.getInt("AcceptanceRate");

				Hosts hosts = new Hosts(resultHostId, hostName, hostUrl, since, about, responseRate, acceptanceRate);
				return hosts;
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
	 * Update the About.
	 * This runs a UPDATE statement.
	 */
	public Hosts updateAbout(Hosts host, String newAbout) throws SQLException {
		String updateHosts = "UPDATE Hosts SET About=? WHERE HostId=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateHosts);
			updateStmt.setString(1, newAbout);
			updateStmt.setInt(2, host.getHostId());
			updateStmt.executeUpdate();

			host.setAbout(newAbout);
			return host;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(updateStmt != null) {
				updateStmt.close();
			}
		}
	}
	
	/**
	 * Delete the Hosts instance.
	 * This runs a DELETE statement.
	 */
	public Hosts delete(Hosts host) throws SQLException {
		String deleteHosts = "DELETE FROM Hosts WHERE HostId =?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteHosts);
			deleteStmt.setInt(1, host.getHostId());
			int affectedRows = deleteStmt.executeUpdate();
			if (affectedRows == 0) {
				throw new SQLException("No records available to delete for HostId=" + host.getHostId());
			}
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
