package client.data.pojo;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

//POJO CSV mapper class
@JsonPropertyOrder({ "sequenceID", "group", "address1", "street", "city", "zip", "country", "clientID" })
public class Customer {

	private String sequenceID;
	private String group;
	private String address1;
	private String street;
	private String city;
	private String zip;
	private String country;
	private String clinetID;
	private String groupedSequenceID = "";

	public String getGroupedSequenceID() {
		return groupedSequenceID;
	}

	public void setGroupedSequenceID(String groupedSequenceID) {
		this.groupedSequenceID = groupedSequenceID;
	}

	public String getSequenceID() {
		return sequenceID;
	}

	public void setSequenceID(String sequenceID) {
		this.sequenceID = sequenceID;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getClinetID() {
		return clinetID;
	}

	public void setClinetID(String clinetID) {
		this.clinetID = clinetID;
	}

	@Override
	public String toString() {
		return "Customer [sequenceID=" + sequenceID + ", group=" + group + ", address1=" + address1 + ", street="
				+ street + ", city=" + city + ", zip=" + zip + ", country=" + country + ", clinetID=" + clinetID + "]";
	}
}
