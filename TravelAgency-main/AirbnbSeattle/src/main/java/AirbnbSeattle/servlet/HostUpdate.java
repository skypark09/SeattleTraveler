package AirbnbSeattle.servlet;

import AirbnbSeattle.dal.*;
import AirbnbSeattle.model.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.annotation.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@SuppressWarnings("serial")
@WebServlet("/hostupdate")
public class HostUpdate extends HttpServlet {
	
	protected HostsDao hostsDao;
	
	@Override
	public void init() throws ServletException {
		hostsDao = HostsDao.getInstance();
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        // Retrieve user and validate.
        String hostId_string = req.getParameter("hostid");
        if (hostId_string == null || hostId_string.trim().isEmpty()) {
        	messages.put("success", "Please enter a valid HostId.");
        } else {
        	int hostId = Integer.valueOf(hostId_string);
            try {
            	Hosts host = hostsDao.getHostByHostId(hostId);
            	if(host == null) {
            		messages.put("success", "UserId does not exist.");
            	}
            	req.setAttribute("host", host);
            } catch (SQLException e) {
    			e.printStackTrace();
    			throw new IOException(e);
    	    }
        }
        req.getRequestDispatcher("/HostUpdate.jsp").forward(req, resp);
	}
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        // Retrieve user and validate.
        String hostId_string = req.getParameter("hostid");
        if (hostId_string == null || hostId_string.trim().isEmpty()) {
            messages.put("success", "Please enter a valid HostId.");
        } else {
        	int hostId = Integer.valueOf(hostId_string);
        	try {
        		Hosts host = hostsDao.getHostByHostId(hostId);
        		String newAbout = req.getParameter("about");
        		if (newAbout == null || newAbout.trim().isEmpty()) {
        			messages.put("success", "Please enter a valid description.");
        		} else {
        			host = hostsDao.updateAbout(host, newAbout);
    	        	messages.put("success", "Successfully updated " + hostId);
        		}
        		req.setAttribute("host", host);
        	} catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
	        }
        }
        
        req.getRequestDispatcher("/HostUpdate.jsp").forward(req, resp);
    }
}
