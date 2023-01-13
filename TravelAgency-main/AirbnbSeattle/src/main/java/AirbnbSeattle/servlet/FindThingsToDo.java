package AirbnbSeattle.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import AirbnbSeattle.dal.ThingsToDoDao;
import AirbnbSeattle.model.ThingsToDo;
import AirbnbSeattle.model.ZipCode;

@WebServlet("/findthingstodo")
public class FindThingsToDo extends HttpServlet {
	
	protected ThingsToDoDao thingsToDoDao;
	
	@Override
	public void init() throws ServletException {
		thingsToDoDao = ThingsToDoDao.getInstance();
	}
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException{
		Map<String, String> messages = new HashMap<String, String>();
		req.setAttribute("messages", messages);
		
		List<ThingsToDo> thingsToDo = new ArrayList<ThingsToDo>();
		
		String zipCodeId = req.getParameter("zipCodeId");
		
		if (zipCodeId == null || zipCodeId.trim().isEmpty()) {
			messages.put("success", "Please enter a valid zip code ID.");
		} else {
			try {
				ZipCode zipCode = new ZipCode(Integer.parseInt(zipCodeId));
				thingsToDo = thingsToDoDao.getThingsToDoFromZipCode(zipCode);
			} catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
			}
			messages.put("success", "Displaying results for " + zipCodeId);
			
		}
		req.setAttribute("thingsToDo", thingsToDo);
		
		req.getRequestDispatcher("/FindThingsToDo.jsp").forward(req, resp);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException{
		Map<String, String> messages = new HashMap<String, String>();
		req.setAttribute("messages", messages);
		
		List<ThingsToDo> thingsToDo = new ArrayList<ThingsToDo>();
		
		String zipCodeId = req.getParameter("zipCodeId");
		
		if (zipCodeId == null || zipCodeId.trim().isEmpty()) {
			messages.put("success", "Please enter a valid zip code ID.");
		} else {
			try {
				ZipCode zipCode = new ZipCode(Integer.parseInt(zipCodeId));
				thingsToDo = thingsToDoDao.getThingsToDoFromZipCode(zipCode);
			} catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
			}
			messages.put("success", "Displaying results for " + zipCodeId);
			
		}
		req.setAttribute("thingsToDo", thingsToDo);
		
		req.getRequestDispatcher("/FindThingsToDo.jsp").forward(req, resp);
	}
}
