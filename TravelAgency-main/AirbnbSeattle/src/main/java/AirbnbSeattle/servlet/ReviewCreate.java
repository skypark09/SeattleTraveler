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

@WebServlet("/reviewcreate")
public class ReviewCreate extends HttpServlet{
	protected ReviewsDao reviewDao;

	@Override
	public void init() throws ServletException {
		reviewDao = ReviewsDao.getInstance();
		
	};
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
        req.getRequestDispatcher("/CreateReview.jsp").forward(req, resp);
	};
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException {
		Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
        Reviews review = null;
        AirbnbListing listing = null;
        Users user = null;
        
        String userId = req.getParameter("userId");
        String userName = req.getParameter("username");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String created = req.getParameter("created");
        Date create = new Date();
        String content = req.getParameter("content");
        String listingId = req.getParameter("listingId");
        
        try {
        	create = dateFormat.parse(created);
        } catch (ParseException e) {
        	e.printStackTrace();
        	throw new IOException(e);
        }
        
        try {
			review = new Reviews(Integer.valueOf(userId), userName, create, content, Long.valueOf(listingId) );
			review = reviewDao.create(review);
			messages.put("success", "Successfully created " + review);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (SQLException e) {
			e.printStackTrace();
			throw new IOException(e);
        }
        
        req.getRequestDispatcher("/CreateReview.jsp").forward(req, resp);
        
        
         
        
		
	}
	
}