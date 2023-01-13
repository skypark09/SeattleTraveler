package AirbnbSeattle.servlet;

import AirbnbSeattle.dal.*;
import AirbnbSeattle.model.*;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.annotation.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



@WebServlet("/listingCreate")
public class ListingCreate extends HttpServlet {
	protected AirbnbListingDao listingDao;
	protected HostsDao hostDao;
	protected NeighborhoodsDao neighborDao;
	
	@Override
	public void init() throws ServletException {
		listingDao = AirbnbListingDao.getInstance();
		hostDao = HostsDao.getInstance();
		neighborDao = NeighborhoodsDao.getInstance();
	}
	
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
        List<Neighborhoods> neighborhoods = new ArrayList<Neighborhoods>();
        
        try {
			neighborhoods = neighborDao.getAllNeighborhood();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        //Just render the JSP.   
        req.setAttribute("neighborhoods", neighborhoods);
        req.getRequestDispatcher("/CreateNewListing.jsp").forward(req, resp);
	}
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
        AirbnbListing resultListing = null;
        Hosts host = null;
        Neighborhoods neighbor = null;
        // Retrieve and validate name.
        String newProperty = req.getParameter("propertyName");
        if (newProperty == null || newProperty.trim().isEmpty()) {
            messages.put("success", "Invalid Property Name");
        } else {
        	// Create the new Listing.
        	String listingURL = req.getParameter("listing_url");
        	String picture_URL = req.getParameter("picture_url");
        	String latitude = req.getParameter("latitude");
        	String longitude = req.getParameter("longitude");
        	String propertyType = req.getParameter("property_type");
        	String roomtype = req.getParameter("room_type");
        	String accommdates = req.getParameter("accommodates");
        	String bathrooms = req.getParameter("bathrooms");
        	String bedrooms = req.getParameter("bedrooms");
        	String beds = req.getParameter("beds");
        	String price = req.getParameter("price");
        	String hostId = req.getParameter("hostId");
        	String neighborhood = req.getParameter("neighborhood_Overview");
        	String description = req.getParameter("description");
        	String neighborhoodId = req.getParameter("neighborhoodId");
	        try {
	        	host = hostDao.getHostByHostId(Integer.parseInt(hostId));
	        	if(host == null) {
	        		messages.put("success", "host id " + hostId);
	        	}else {
	        		neighbor = neighborDao.getNeighborhoodById(Integer.parseInt(neighborhoodId));
		        	
		        	AirbnbListing listing = new AirbnbListing(listingURL,description, neighborhood, picture_URL, Float.parseFloat(latitude),Float.parseFloat(longitude),
		        			propertyType,roomtype,Integer.parseInt(accommdates),Float.parseFloat(bathrooms), Float.parseFloat(bedrooms), Float.parseFloat(beds),Double.parseDouble(price),
		        			host, newProperty, neighbor);
		        	resultListing = listingDao.create(listing);
		        	messages.put("success", "Successfully created " + newProperty);
	        	}
	        
	        } catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
	        }
        }
        req.getRequestDispatcher("/CreateNewListing.jsp").forward(req, resp);
    }
	
	
}