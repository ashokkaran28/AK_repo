package client.data.helper;

import java.util.ArrayList;
import java.util.List;

import client.data.pojo.Customer;

/**
 * Accumulator class written to merge groups of customers whose street,city,zip
 * and country matches with each other. Also during grouping lowest sequenceID
 * group is picked and all other sequenceIDs grouped are added to
 * {@code groupedSequenceID} field
 * 
 * 
 * @author Ashok Kumar Karan
 */
public class Accumulator {

	private List<Customer> customers;

	/**
	 * Constructor
	 */
	public Accumulator() {
		customers = new ArrayList<>();
	}

	/**
	 * written to merge groups of customers whose street,city,zip and country
	 * matches with each other. Also during grouping lowest sequenceID group is
	 * picked and all other sequenceIDs grouped are added to
	 * {@code groupedSequenceID} field
	 */
	public void accumulate(Customer cust) {
		if (customers.contains(cust)) {
			Customer matchingCustomer = getMatchingCustomer(customers, cust);
			if (matchingCustomer.getGroupedSequenceID().isEmpty()) {
				matchingCustomer.setGroupedSequenceID(matchingCustomer.getSequenceID() + "_" + cust.getSequenceID());
			} else {
				matchingCustomer
						.setGroupedSequenceID(matchingCustomer.getGroupedSequenceID() + "_" + cust.getSequenceID());
			}
			if (Integer.valueOf(cust.getSequenceID()) < Integer.valueOf(matchingCustomer.getSequenceID())) {
				matchingCustomer.setSequenceID(cust.getSequenceID());
				matchingCustomer.setGroup(cust.getGroup());
				matchingCustomer.setAddress1(cust.getAddress1());
				matchingCustomer.setClinetID(cust.getClinetID());
			}
		} else {
			customers.add(cust);
		}
	}

	public Accumulator combine(Accumulator other) {

		return this;
	}

	public List<Customer> getCustomers() {
		return customers;
	}

	public Customer getMatchingCustomer(List<Customer> customers, Customer secondCust) {
		Customer matchingCustomer = null;
		for (int i = 0; i < customers.size(); i++) {
			if (customers.get(i).equals(secondCust)) {
				matchingCustomer = customers.get(i);
				break;
			}
		}
		return matchingCustomer;

	}

	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}

}
