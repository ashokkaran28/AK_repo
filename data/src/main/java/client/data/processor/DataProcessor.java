package client.data.processor;

import java.util.List;
import java.util.Map;

import client.data.pojo.Customer;

/**
 * @author Ashok Kumar Karan
 *
 */
@FunctionalInterface
public interface DataProcessor {
	public Map<String, Map<String, Map<String, List<Customer>>>> processData(List<Customer> customers);
}
