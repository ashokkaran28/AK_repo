package client.data;

import java.util.ArrayList;
import java.util.List;

import client.data.pojo.Customer;

public class Accumulator {

	private String sequenceID;
	private StringBuilder groupedSequenceID;
	private String street;
	private String city;
	private String zip;
	private String country;

	List<Customer> customers;

	Accumulator() {
		customers = new ArrayList<>();
		groupedSequenceID = new StringBuilder("");
	}

	void accumulate(Customer cust) {
		if (sequenceID == null && street == null && city == null && zip == null && country == null) {
			sequenceID = cust.getSequenceID();
			street = cust.getStreet();
			city = cust.getCity();
			zip = cust.getZip();
			country = cust.getCountry();
			customers.add(cust);
		}

		else {
			if (street.equalsIgnoreCase(cust.getStreet()) && city.equalsIgnoreCase(cust.getCity())
					&& zip.equalsIgnoreCase(cust.getZip()) && country.equalsIgnoreCase(cust.getCountry())) {
				if ("".equalsIgnoreCase(groupedSequenceID.toString())) {
					groupedSequenceID.append(sequenceID + "_" + cust.getSequenceID());
				} else {
					groupedSequenceID.append("_" + cust.getSequenceID());
				}
				for (Customer c : customers) {
					if (c.getStreet().equalsIgnoreCase(cust.getStreet()) && c.getCity().equalsIgnoreCase(cust.getCity())
							&& c.getZip().equalsIgnoreCase(cust.getZip())
							&& c.getCountry().equalsIgnoreCase(cust.getCountry())) {
						if (Integer.valueOf(cust.getSequenceID()) < Integer.valueOf(c.getSequenceID())) {
							sequenceID = cust.getSequenceID();
							customers.remove(c);
							customers.add(cust);
							cust.setGroupedSequenceID(groupedSequenceID.toString());
						} else {
							c.setGroupedSequenceID(groupedSequenceID.toString());
						}
					}
				}
			} else {
				customers.add(cust);
			}

		}
	}

	Accumulator combine(Accumulator other) {
		if (Integer.valueOf(this.sequenceID) < Integer.valueOf(other.getSequenceID())) {
			return this;
		} else if (Integer.valueOf(this.sequenceID) > Integer.valueOf(other.getSequenceID())) {
			return other;
		} else {
			this.customers.addAll(other.getCustomers());
			return this;
		}
	}

	List<Customer> getCustomers() {
		return customers;
	}

	public String getSequenceID() {
		return sequenceID;
	}

	public void setSequenceID(String sequenceID) {
		this.sequenceID = sequenceID;
	}

	public StringBuilder getGroupedSequenceID() {
		return groupedSequenceID;
	}

	public void setGroupedSequenceID(StringBuilder groupedSequenceID) {
		this.groupedSequenceID = groupedSequenceID;
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

	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}

}
