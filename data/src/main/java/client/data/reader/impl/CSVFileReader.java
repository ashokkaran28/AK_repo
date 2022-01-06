package client.data.reader.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collector;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;

import client.data.helper.Accumulator;
import client.data.pojo.Customer;
import client.data.reader.FileReader;

/**
 * @author Ashok Kumar Karan
 *
 */
public class CSVFileReader implements FileReader {

	@Override
	public List<Customer> readData(String filePath) throws IOException {
		// Get File Path from program argument
		File csvFile = Paths.get(filePath).toFile();

		// Map CSV entries to list of Customer POJO
		MappingIterator<Customer> customerIter = new CsvMapper().readerWithTypedSchemaFor(Customer.class)
				.readValues(csvFile);

		// Make sure to skip the Header
		List<Customer> customers = customerIter.readAll().stream().skip(1).collect(Collector.of(Accumulator::new,
				Accumulator::accumulate, Accumulator::combine, Accumulator::getCustomers));
		return customers;
	}

}
