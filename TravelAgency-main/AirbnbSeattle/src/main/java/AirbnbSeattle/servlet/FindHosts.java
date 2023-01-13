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

@WebServlet("/findhosts")
public class FindHosts extends HttpServlet {
	
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

        Hosts host = null;
        

        String hostId = req.getParameter("hostId");
        if (hostId == null || hostId.trim().isEmpty()) {
            messages.put("success", "Please enter a valid name.");
        } else {
        	try {
            	host = hostsDao.getHostByHostId(Integer.valueOf(hostId));
            } catch (SQLException e) {
    			e.printStackTrace();
    			throw new IOException(e);
            }
        	messages.put("success", "Displaying results for HostId" + hostId);
        	// Save the previous search term, so it can be used as the default
        	// in the input box when rendering FindUsers.jsp.
        	messages.put("previousHostId", hostId);
        }
        req.setAttribute("Hosts", host);
        
        req.getRequestDispatcher("/FindHosts.jsp").forward(req, resp);
	}
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        Hosts host = null;
        
        String hostId = req.getParameter("hostId");
        if (hostId == null || hostId.trim().isEmpty()) {
            messages.put("success", "Please enter a valid hostId.");
        } else {
        	try {
        		host = hostsDao.getHostByHostId(Integer.valueOf(hostId));
            } catch (SQLException e) {
    			e.printStackTrace();
    			throw new IOException(e);
            }
        	messages.put("success", "Displaying results for HostId :" + hostId);
        }
        req.setAttribute("Hosts", host);
        
        req.getRequestDispatcher("/FindHosts.jsp").forward(req, resp);
    }
}
