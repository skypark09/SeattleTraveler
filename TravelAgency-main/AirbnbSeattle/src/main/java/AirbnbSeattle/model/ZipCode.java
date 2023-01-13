package AirbnbSeattle.model;

public class ZipCode {
	protected int zipCodeId;
	protected int zipCodeNumber;

	
	public ZipCode(int zipCodeId, int zipCodeNumber) {
		this.zipCodeId = zipCodeId;
		this.zipCodeNumber = zipCodeNumber;
	}

	public ZipCode(int zipCodeId) {
		this.zipCodeId = zipCodeId;
	}
	
	public int getZipCodeId() {
		return zipCodeId;
	}
	
	public void setZipCodeId(int zipCodeId) {
		this.zipCodeId = zipCodeId;
	}

	public int getZipCodeNumber() {
		return zipCodeNumber;
	}
	
	public void setZipCodeNumber(int zipCodeNumber) {
		this.zipCodeNumber = zipCodeNumber;
	}

}
