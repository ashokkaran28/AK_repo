package client.data.processor.impl;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import client.data.pojo.Customer;
import client.data.processor.DataProcessor;

/**
 * Here grouping,splitting and sorting of {@code Customer} list are done using
 * Java 1.8 {@code Stream}.
 * 
 * @author Ashok Kumar Karan
 *
 */
public class DataProcessorImpl implements DataProcessor {

	@Override
	public Map<String, Map<String, Map<String, List<Customer>>>> processData(List<Customer> customers) {
		// Creating the comparator
		Comparator<Customer> comparator = Comparator.comparing(Customer::getCountry)
				.thenComparing(Customer::getSequenceID);

		// Using Stream API to group and sort
		Map<String, Map<String, Map<String, List<Customer>>>> groupedCustomers = customers.stream().sorted(comparator)
				.collect(Collectors.groupingBy(Customer::getGroup, groupByCountryAndSequenceID()));
		return groupedCustomers;
	}

	/**
	 * This method returns a map after grouping country and sequenceIds
	 */
	private static Collector<Customer, ?, Map<String, Map<String, List<Customer>>>> groupByCountryAndSequenceID() {
		return Collectors.groupingBy(Customer::getCountry, LinkedHashMap::new,
				Collectors.groupingBy(Customer::getSequenceID, LinkedHashMap::new, Collectors.toList()));
	}

}
