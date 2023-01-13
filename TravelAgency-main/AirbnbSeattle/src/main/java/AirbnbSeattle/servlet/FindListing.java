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


@WebServlet("/FindAirbnbListings")
public class FindListing extends HttpServlet {
	protected AirbnbListingDao airbnbListingDao;
	protected ThingsToDoDao thingTodoDao;
	protected ZipCodeDao zipcodeDao;
	
	@Override
	public void init() throws ServletException {
		airbnbListingDao = AirbnbListingDao.getInstance();
		thingTodoDao = ThingsToDoDao.getInstance();
		zipcodeDao = ZipCodeDao.getInstance();
	}
	
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Map for storing messages.
		Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        List<AirbnbListing> listings = new ArrayList<AirbnbListing>();
        
        List<Parks> parks = new ArrayList<Parks>();
        
        List<Restaurants> restaurants = new ArrayList<Restaurants>();
        
        List<ZipCode> top10ListingZip = new ArrayList<ZipCode>();
        
        List<ZipCode> top10todoZip = new ArrayList<ZipCode>();
        // Retrieve and validate name.
        // firstname is retrieved from the URL query string.
        String input = req.getParameter("search");
        try {
			top10ListingZip = zipcodeDao.getTop10ListingZipCode();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			top10todoZip = zipcodeDao.getTop10ThingsToDoZipCode();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        if (input == null || input.trim().isEmpty()) {
            messages.put("success", "Please enter a valid zipcode or neighborhood.");
        } else {
        	// Retrieve BlogUsers, and store as a message.
        	try {
        		
        		if(checkInputStringOrNum(input)) {
        			listings = airbnbListingDao.getListingByZipcode(Integer.valueOf(input));
        			parks = thingTodoDao.getParkFromZipCode(Integer.valueOf(input));
        			restaurants = thingTodoDao.getRestaurantsFromZipCode(Integer.valueOf(input));
        		}else {
        			listings = airbnbListingDao.getListingByNeighborhoodName(input);
        			parks = thingTodoDao.getParkFromNeighborhood(input);
        			restaurants = thingTodoDao.getRestaurantFromNeighborhood(input);
        		}
        		
        		
            } catch (SQLException e) {
    			e.printStackTrace();
    			throw new IOException(e);
            }
        	if(listings.size() == 0 || listings == null) {
        		messages.put("success", "Please enter a valid zipcode or neighborhood.");
        	}else {
        		messages.put("success", "Displaying results for " + input);
        	}
        	// Save the previous search term, so it can be used as the default
        	// in the input box when rendering FindUsers.jsp.
        	messages.put("previous zipcode", input);
        }
        req.setAttribute("listings", listings);
        req.setAttribute("parks", parks);
        req.setAttribute("restaurants", restaurants);
        req.setAttribute("top10todoZip", top10todoZip);
        req.setAttribute("top10ListingZip", top10ListingZip);
        
        req.getRequestDispatcher("/FindListing.jsp").forward(req, resp);
	}
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
		// Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        List<AirbnbListing> listings = new ArrayList<AirbnbListing>();
        
        
        List<Parks> parks = new ArrayList<Parks>();
        List<Restaurants> restaurants = new ArrayList<Restaurants>();
        List<ZipCode> top10ListingZip = new ArrayList<ZipCode>();
        
        List<ZipCode> top10todoZip = new ArrayList<ZipCode>();
        
        // Retrieve and validate name.
        // firstname is retrieved from the URL query string.
        String input = req.getParameter("search");
        try {
			top10ListingZip = zipcodeDao.getTop10ListingZipCode();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			top10todoZip = zipcodeDao.getTop10ThingsToDoZipCode();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        if (input == null || input.trim().isEmpty()) {
            messages.put("success", "Please enter a valid zipcode or neighborhood.");
        } else {
        	// Retrieve BlogUsers, and store as a message.
        	try {
        		
        		if(checkInputStringOrNum(input)) {
        			listings = airbnbListingDao.getListingByZipcode(Integer.valueOf(input));
        			parks = thingTodoDao.getParkFromZipCode(Integer.valueOf(input));
        			restaurants = thingTodoDao.getRestaurantsFromZipCode(Integer.valueOf(input));
        		}else {
        			listings = airbnbListingDao.getListingByNeighborhoodName(input);
        			parks = thingTodoDao.getParkFromNeighborhood(input);
        			restaurants = thingTodoDao.getRestaurantFromNeighborhood(input);
        		}
        		
            } catch (SQLException e) {
    			e.printStackTrace();
    			throw new IOException(e);
            }
        	if(listings.size() == 0 || listings == null) {
        		messages.put("success", "Please enter a valid zipcode or neighborhood.");
        	}else {
        		messages.put("success", "Displaying results for " + input);
        	}
        	// Save the previous search term, so it can be used as the default
        	// in the input box when rendering FindUsers.jsp.
        	messages.put("previous zipcode", input);
        }
        req.setAttribute("listings", listings);
        req.setAttribute("parks", parks);
        req.setAttribute("restaurants", restaurants);
        req.setAttribute("top10todoZip", top10todoZip);
        req.setAttribute("top10ListingZip", top10ListingZip);
        
        req.getRequestDispatcher("/FindListing.jsp").forward(req, resp);
    }
	
	public boolean checkInputStringOrNum(String search) {
		for (int i = 0; i < search.length(); i++)
            if (Character.isDigit(search.charAt(i)) == false)
                return false;
 
        return true;
	}
	
	
//	@Override
//    public void doPost(HttpServletRequest req, HttpServletResponse resp)
//    		throws ServletException, IOException {
//		// Map for storing messages.
//        Map<String, String> messages = new HashMap<String, String>();
//        req.setAttribute("messages", messages);
//
//        List<AirbnbListing> listings = new ArrayList<AirbnbListing>();
//        
//        // Retrieve and validate name.
//        // firstname is retrieved from the URL query string.
//        String zipcode = req.getParameter("zipcode");
//        if (zipcode == null || zipcode.trim().isEmpty()) {
//            messages.put("success", "Please enter a valid zipcode.");
//        } else {
//        	// Retrieve BlogUsers, and store as a message.
//        	try {
//        		
//            	listings = airbnbListingDao.getListingByZipcode(Integer.valueOf(zipcode));
//            } catch (SQLException e) {
//    			e.printStackTrace();
//    			throw new IOException(e);
//            }
//        	if(listings.size() == 0 || listings == null) {
//        		messages.put("success", "Please enter a valid zipcode.");
//        	}else {
//        		messages.put("success", "Displaying results for " + zipcode);
//        	}
//        	// Save the previous search term, so it can be used as the default
//        	// in the input box when rendering FindUsers.jsp.
//        	messages.put("previous zipcode", zipcode);
//        }
//        req.setAttribute("listings", listings);
//        
//        req.getRequestDispatcher("/FindListing.jsp").forward(req, resp);
//    }
	
	
	
	
}