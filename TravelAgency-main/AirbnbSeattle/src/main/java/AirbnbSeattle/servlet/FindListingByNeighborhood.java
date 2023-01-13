package AirbnbSeattle.servlet;

import AirbnbSeattle.dal.*;
import AirbnbSeattle.model.*;


import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.annotation.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/FindListingByNeighborhood")
public class FindListingByNeighborhood extends HttpServlet {
	protected AirbnbListingDao airbnbListingDao;
	
	@Override
	public void init() throws ServletException {
		airbnbListingDao = AirbnbListingDao.getInstance();
	}
	
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        List<AirbnbListing> listings = new ArrayList<AirbnbListing>();
        
        // Retrieve and validate name.
        // firstname is retrieved from the URL query string.
        String neighborhood = req.getParameter("neighborhood");
        if (neighborhood == null || neighborhood.trim().isEmpty()) {
            messages.put("success", "Please enter a valid Neighborhood.");
        } else {
        	// Retrieve BlogUsers, and store as a message.
        	try {
            	listings = airbnbListingDao.getListingByNeighborhoodName(neighborhood);
    
            } catch (SQLException e) {
    			e.printStackTrace();
    			throw new IOException(e);
            }
        	
        	if(listings.size() == 0 || listings == null) {
        		messages.put("success", "Please enter a valid neighborhood.");
        	}else {
        		messages.put("success", "Displaying results for Neighborhood" + neighborhood);
        	}

        	// Save the previous search term, so it can be used as the default
        	// in the input box when rendering FindUsers.jsp.
        	messages.put("previous neighborhood", neighborhood);
        }
        req.setAttribute("listings", listings);
        
        req.getRequestDispatcher("/FindListingByNeighborhood.jsp").forward(req, resp);
	}
	
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
		// Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        List<AirbnbListing> listings = new ArrayList<AirbnbListing>();
        
        // Retrieve and validate name.
        // firstname is retrieved from the URL query string.
        String neighborhood = req.getParameter("neighborhood");
        if (neighborhood == null || neighborhood.trim().isEmpty()) {
            messages.put("success", "Please enter a valid Neighborhood.");
        } else {
        	// Retrieve BlogUsers, and store as a message.
        	try {
            	listings = airbnbListingDao.getListingByNeighborhoodName(neighborhood);
    
            } catch (SQLException e) {
    			e.printStackTrace();
    			throw new IOException(e);
            }
        	
        	if(listings.size() == 0 || listings == null) {
        		messages.put("success", "Please enter a valid neighborhood.");
        	}else {
        		messages.put("success", "Displaying results for Neighborhood" + neighborhood);
        	}

        	// Save the previous search term, so it can be used as the default
        	// in the input box when rendering FindUsers.jsp.
        	messages.put("previous neighborhood", neighborhood);
        }
        req.setAttribute("listings", listings);
        
        req.getRequestDispatcher("/FindListingByNeighborhood.jsp").forward(req, resp);
    }
	
	
	
	
}