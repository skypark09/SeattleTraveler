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
@WebServlet("/hostdelete")
public class HostDelete extends HttpServlet {
	
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
        // Provide a title and render the JSP.
        messages.put("title", "Delete host");        
        req.getRequestDispatcher("/HostDelete.jsp").forward(req, resp);
	}
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        // Retrieve and validate name.
        String hostId_string = req.getParameter("hostid");
        if (hostId_string == null || hostId_string.trim().isEmpty()) {
            messages.put("title", "Invalid HostId");
            messages.put("disableSubmit", "true");
        } else {
        	// Delete the Host.
        	int hostId = Integer.valueOf(hostId_string);
	        Hosts host = new Hosts(hostId);
	        try {
	        	host = hostsDao.delete(host);
	        	// Update the message.
		        if (host == null) {
		            messages.put("title", "Successfully deleted " + hostId);
		            messages.put("disableSubmit", "true");
		        } else {
		        	messages.put("title", "Failed to delete " + hostId);
		        	messages.put("disableSubmit", "false");
		        }
	        } catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
	        }
        }
        
        req.getRequestDispatcher("/HostDelete.jsp").forward(req, resp);
    }
}
