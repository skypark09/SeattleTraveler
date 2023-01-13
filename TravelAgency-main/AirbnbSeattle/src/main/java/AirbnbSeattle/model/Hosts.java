
package AirbnbSeattle.model;

import java.util.Date;

public class Hosts {
	protected int hostId;
	protected String hostUrl;
	protected String hostName;
	protected Date since;
	protected String about;
	protected int responseRate;
	protected int acceptanceRate;
	
	public Hosts(int hostId,  String hostUrl, String hostName,Date since, String about, 
			int responseRate, int acceptanceRate) {
		this.hostId = hostId;
		this.hostUrl = hostUrl;
		this.hostName = hostName;
		this.since = since;
		this.about = about;
		this.responseRate = responseRate;
		this.acceptanceRate = acceptanceRate;
	}
	
	public Hosts(String hostUrl, String hostName,Date since, String about, 
			int responseRate, int acceptanceRate) {
		this.hostUrl = hostUrl;
		this.hostName = hostName;
		this.since = since;
		this.about = about;
		this.responseRate = responseRate;
		this.acceptanceRate = acceptanceRate;
	}
	
	public Hosts(int hostId) {
		this.hostId = hostId;
	}

	public int getHostId() {
		return hostId;
	}

	public void setHostId(int hostId) {
		this.hostId = hostId;
	}
	
	public String getHostName() {
		return hostName;
	}
	
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	
	public String getHostUrl() {
		return hostUrl;
	}
	
	public void setHostUrl(String hostUrl) {
		this.hostUrl = hostUrl;
	}
	
	public Date getSince() {
		return since;
	}
	
	public void setSince(Date since) {
		this.since = since;
	}
	
	public String getAbout() {
		return about;
	}
	
	public void setAbout(String about) {
		this.about = about;
	}
	
	public int getResponseRate() {
		return responseRate;
	}
	
	public void setResponseRate(int responseRate) {
		this.responseRate = responseRate;
	}
	
	public int getAcceptanceRate() {
		return acceptanceRate;
	}
	
	public void setAcceptanceRate(int acceptanceRate) {
		this.acceptanceRate = acceptanceRate;
	}
	
}
