package AirbnbSeattle.dal;

import AirbnbSeattle.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



public class ReviewsDao {
    protected ConnectionManager connectionManager;

	private static ReviewsDao instance = null;
	protected ReviewsDao() {
		connectionManager = new ConnectionManager();
	}
	public static ReviewsDao getInstance() {
		if(instance == null) {
			instance = new ReviewsDao();
		}
		return instance;
	}

    public Reviews create(Reviews review) throws SQLException {
        String insertReview = "INSERT INTO Reviews(UserId,UserName,Created,Content,ListingId)" +
                " VALUES(?,?,?,?,?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        ResultSet resultKey = null;
        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertReview, Statement.RETURN_GENERATED_KEYS);

            insertStmt.setInt(1, review.getUserId());
            insertStmt.setString(2, review.getUsername());
            insertStmt.setTimestamp(3, new Timestamp(review.getCreated().getTime()));
            insertStmt.setString(4, review.getContent());
            insertStmt.setLong(5, review.getListingId());

            insertStmt.executeUpdate();

			resultKey = insertStmt.getGeneratedKeys();
			Long reviewId = (long) -1;
			if(resultKey.next()) {
				reviewId = resultKey.getLong(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			review.setReviewId(reviewId);
            
            
            return review;
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

    public Reviews getReviewById(Long reviewId) throws SQLException {
        String selectReview = "SELECT * FROM Reviews WHERE ReviewId = ?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectReview);

            selectStmt.setLong(1, reviewId);
            results = selectStmt.executeQuery();
            if (results.next()) {
                return new Reviews(results.getLong(1), results.getInt(2), results.getString(3),
                        results.getDate(4), results.getString(5), results.getLong(6));
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
        }
        return null;
    }

    public List<Reviews> getReviewsByUserId(int userId) throws SQLException {
    	List<Reviews> reviews = new ArrayList<>();
        String selectReviews = "SELECT * FROM Reviews WHERE UserId = ?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectReviews);

            selectStmt.setInt(1, userId);
            results = selectStmt.executeQuery();
            while (results.next()) {
            	reviews.add(new Reviews(results.getLong(1), results.getInt(2), results.getString(3),
                        results.getDate(4), results.getString(5), results.getLong(6)));
            	
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
        return reviews;
    }

    public List<Reviews> getReviewsByListingId(Long listingId) throws SQLException {
    	List<Reviews> reviews = new ArrayList<>();
        String selectPersons = "SELECT * FROM Reviews WHERE ListingId = ?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectPersons);

            selectStmt.setLong(1, listingId);
            results = selectStmt.executeQuery();
            while (results.next()) {
            	reviews.add(new Reviews(results.getLong(1), results.getInt(2), results.getString(3),
                        results.getDate(4), results.getString(5), results.getLong(6)));
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
        return reviews ;
    }

    public Reviews updateContent(Reviews review, String newContent) throws SQLException {
		String updateContent = "UPDATE Reviews SET Content=?,Created=? WHERE ReviewId=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateContent);
			updateStmt.setString(1, newContent);
			Date newCreatedTimestamp = new Date();
			updateStmt.setTimestamp(2, new Timestamp(newCreatedTimestamp.getTime()));
			updateStmt.setLong(3, review.getReviewId());
			updateStmt.executeUpdate();

			// Update the blogComment param before returning to the caller.
			review.setContent(newContent);
			review.setCreated(newCreatedTimestamp);
			return review;
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
    
    
    
    public Reviews delete(Reviews review) throws SQLException {
        String deleteReview = "DELETE FROM Reviews WHERE ReviewId=?;";
        Connection connection = null;
        PreparedStatement deleteStmt = null;
        try {
            connection = connectionManager.getConnection();
            deleteStmt = connection.prepareStatement(deleteReview);
            deleteStmt.setLong(1, review.getReviewId());
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
