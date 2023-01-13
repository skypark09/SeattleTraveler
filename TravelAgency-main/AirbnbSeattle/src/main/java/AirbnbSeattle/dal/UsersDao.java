
package AirbnbSeattle.dal;

import AirbnbSeattle.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/**
* Data access object (DAO) class to interact with the underlying Users table in your MySQL
* instance. This is used to store {@link Users} into your MySQL instance and retrieve 
* {@link Users} from MySQL instance.
*/
public class UsersDao {
    protected ConnectionManager connectionManager;

	// Single pattern: instantiation is limited to one object.    
	private static UsersDao instance = null;
	protected UsersDao() {
		connectionManager = new ConnectionManager();
	}
	public static UsersDao getInstance() {
		if(instance == null) {
			instance = new UsersDao();
		}
		return instance;
	}
    
    
	/**
	 * Save the Users instance by storing it in your MySQL instance.
	 * This runs a INSERT statement.
	 */ 
    public Users create(Users user) throws SQLException {
        String insertUser = "INSERT INTO users(UserName) VALUES(?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertUser,Statement.RETURN_GENERATED_KEYS);

            insertStmt.setString(1, user.getUserName());

            insertStmt.executeUpdate();

			resultKey = insertStmt.getGeneratedKeys();
			int userId = -1;
			if(resultKey.next()) {
				userId = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			user.setUserId(userId);

            return user;
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
	 * Update the LastName of the Persons instance.
	 * This runs a UPDATE statement.
	 */
	public Users updateUserName(Users user, String newUserName) throws SQLException {
		String updateUser = "UPDATE Users SET UserName=? WHERE UserID=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateUser);
			updateStmt.setString(1, newUserName);
			updateStmt.setInt(2, user.getUserId());
			updateStmt.executeUpdate();
			
			// Update the person param before returning to the caller.
			user.setUserName(newUserName);
			return user;
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


    public Users getUserByUserId(int userId) throws SQLException {
        String selectUser = "SELECT UserId, UserName FROM Users WHERE userId = ?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectUser);

            selectStmt.setInt(1, userId);
            results = selectStmt.executeQuery();
            if (results.next()) {
                Integer resultUserId = results.getInt("UserId");
                String resultUserName = results.getString("UserName");
                Users user = new Users(resultUserId,resultUserName);
                return user;
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
	 * Delete the Persons instance.
	 * This runs a DELETE statement.
	 */   
    public Users delete(Users user) throws SQLException {
        String deleteUser = "DELETE FROM users WHERE UserId=?;";
        Connection connection = null;
        PreparedStatement deleteStmt = null;
        try {
            connection = connectionManager.getConnection();
            deleteStmt = connection.prepareStatement(deleteUser);
            deleteStmt.setInt(1, user.getUserId());
            deleteStmt.executeUpdate();

            // Return null so the caller can no longer operate on the Persons instance.
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
