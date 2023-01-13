package AirbnbSeattle.model;

public class Neighborhoods{
	protected int NeighborhoodId;
	protected String NeighborhoodName;
	protected ZipCode zip;
	
	
	public Neighborhoods(int neighborhoodId, String neighborhoodName, ZipCode zip) {
		super();
		NeighborhoodId = neighborhoodId;
		NeighborhoodName = neighborhoodName;
		this.zip = zip;
	}


	public Neighborhoods(String neighborhoodName, ZipCode zip) {
		super();
		NeighborhoodName = neighborhoodName;
		this.zip = zip;
	}


	public int getNeighborhoodId() {
		return NeighborhoodId;
	}


	public void setNeighborhoodId(int neighborhoodId) {
		NeighborhoodId = neighborhoodId;
	}


	public String getNeighborhoodName() {
		return NeighborhoodName;
	}


	public void setNeighborhoodName(String neighborhoodName) {
		NeighborhoodName = neighborhoodName;
	}


	public ZipCode getZip() {
		return zip;
	}


	public void setZip(ZipCode zip) {
		this.zip = zip;
	}
	
	
	
	
}