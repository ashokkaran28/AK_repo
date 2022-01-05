package client.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;

import client.data.pojo.Customer;

public class Initiator {

	public static void main(String[] args) {
		try {
			// Get File Path from program argument or set as
			// src/main/java/client/data/inputDataFile/data_splitting_assessment.csv
			File csvFile = Paths.get(args[0]).toFile();

			// Map CSV entries to list of Customer POJO
			MappingIterator<Customer> personIter = new CsvMapper().readerWithTypedSchemaFor(Customer.class)
					.readValues(csvFile);

			// Make sure to skip the Header
			// List<Customer> customers =
			// personIter.readAll().stream().skip(1).collect(Collectors.toList());
			List<Customer> customers = personIter.readAll().stream().skip(1).collect(Collector.of(Accumulator::new,
					Accumulator::accumulate, Accumulator::combine, Accumulator::getCustomers));

			// Using Stream API to group and sort the items as per requirement given
			Map<String, Map<String, Map<String, List<Customer>>>> groupedCustomers = customers.stream()
					.sorted(Comparator.comparing(Customer::getSequenceID))
					.collect(Collectors.groupingBy(Customer::getGroup, groupByCountryAndSequenceID()));

			// print Final json
			createSplittedJsonFiles(args[1], groupedCustomers);

			// print Final json
			createFinalJsonFile(args[2], groupedCustomers);
		} catch (FileNotFoundException e) {
			System.out.println("FileNotFoundException :" + e);
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("IOException :" + e);
			e.printStackTrace();
		}

	}

	private static Collector<Customer, ?, Map<String, Map<String, List<Customer>>>> groupByCountryAndSequenceID() {
		return Collectors.groupingBy(Customer::getCountry,
				Collectors.groupingBy(Customer::getSequenceID, LinkedHashMap::new, Collectors.toList()));
	}

	private static void createSplittedJsonFiles(String path,
			Map<String, Map<String, Map<String, List<Customer>>>> groupedCustomers)
			throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		for (Entry<String, Map<String, Map<String, List<Customer>>>> entry : groupedCustomers.entrySet()) {
			String group = entry.getKey();
			Map<String, Map<String, List<Customer>>> customersInnerGrp = entry.getValue();
			for (Entry<String, Map<String, List<Customer>>> innerEntry : customersInnerGrp.entrySet()) {
				Map<String, Map<String, List<Customer>>> splittedmap = new LinkedHashMap<>();
				String country = innerEntry.getKey();
				splittedmap.put(group + "_" + country, innerEntry.getValue());
				mapper.writeValue(Paths.get(path + group + "_" + country + ".json").toFile(), splittedmap);
			}
		}

	}

	private static void createFinalJsonFile(String path,
			Map<String, Map<String, Map<String, List<Customer>>>> groupedCustomers)
			throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		mapper.writeValue(Paths.get(path).toFile(), groupedCustomers);
	}
}
