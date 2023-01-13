package AirbnbSeattle.model;

public class ThingsToDo {
	protected String name;
	protected String address;
	protected float longitude;
	protected float latitude;
	protected ZipCode zipCode;
	
	public ThingsToDo(String name, String address, float longitude, float latitude, ZipCode zipCode) {
		this.name = name;
		this.address = address;
		this.longitude = longitude;
		this.latitude = latitude;
		this.zipCode = zipCode;
	}
	
	public ThingsToDo(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public ZipCode getZipCode() {
		return zipCode;
	}

	public void setZipCode(ZipCode zipCode) {
		this.zipCode = zipCode;
	}

	public float getLatitude() {
		return latitude;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	public float getLongitude() {
		return longitude;
	}

	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

	
}
