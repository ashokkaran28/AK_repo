package client.data.reader;

import java.io.IOException;
import java.util.List;

import client.data.pojo.Customer;

/**
 * @author Ashok Kumar Karan
 *
 */
@FunctionalInterface
public interface FileReader {
	public List<Customer> readData(String filePath) throws IOException;
}
