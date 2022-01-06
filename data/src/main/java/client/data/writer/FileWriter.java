package client.data.writer;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

import client.data.pojo.Customer;

/**
 * @author Ashok Kumar Karan
 *
 */
@FunctionalInterface
public interface FileWriter {
	public void writetoFile(String filePath, Map<String, Map<String, Map<String, List<Customer>>>> groupedCustomers)
			throws JsonGenerationException, JsonMappingException, IOException;
}
