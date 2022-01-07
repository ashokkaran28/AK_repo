package client.data.pojo;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * POJO CSV mapper class
 * 
 * @author Ashok Kumar Karan
 */
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

	/**
	 * toString method has been overridden in order to better debugging the Customer
	 * class
	 */
	@Override
	public String toString() {
		return "Customer [sequenceID=" + sequenceID + ", group=" + group + ", address1=" + address1 + ", street="
				+ street + ", city=" + city + ", zip=" + zip + ", country=" + country + ", clinetID=" + clinetID + "]";
	}

	@Override
	public boolean equals(Object obj) {
		// if both the object references are referring to the same object.
		if (this == obj)
			return true;

		// it checks if the argument is of the
		// type Customer by comparing the classes
		// of the passed argument and this object.
		if (obj == null || obj.getClass() != this.getClass())
			return false;

		// type casting of the argument.
		Customer cust = (Customer) obj;

		// comparing the state of argument with
		// the state of 'this' Object.
		return (this.getStreet().equalsIgnoreCase(cust.getStreet()) && this.getCity().equalsIgnoreCase(cust.getCity())
				&& this.getZip().equalsIgnoreCase(cust.getZip())
				&& this.getCountry().equalsIgnoreCase(cust.getCountry()));
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}
}
