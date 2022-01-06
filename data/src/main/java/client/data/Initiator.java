package client.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;

import client.data.pojo.Customer;

/**
 * This is the initiator class which is the entrypoint to the
 * program. This accepts two argument as input one is the input
 * file path and other one is the output file path.
 * 
 * Basically this is written to convert a CSV file into JSON format
 * and while doing so grouping,splitting and sorting are done using  
 * Java 1.8 {@code Stream}.   
 * 
 * @author Ashok Kumar Karan
 *
 */
public class Initiator {

	public static void main(String[] args) {
		try {
			// If program argument does not contain input and output path then return
			// src/main/java/client/data/inputDataFile/data_splitting_assessment.csv
			// src/main/java/client/data/results/
			if (args.length < 2) {
				System.out.println("Input and Output path not passed as program args so exiting");
				return;
			}

			// Get File Path from program argument
			File csvFile = Paths.get(args[0]).toFile();

			// Map CSV entries to list of Customer POJO
			MappingIterator<Customer> customerIter = new CsvMapper().readerWithTypedSchemaFor(Customer.class)
					.readValues(csvFile);

			// Make sure to skip the Header
			List<Customer> customers = customerIter.readAll().stream().skip(1).collect(Collector.of(Accumulator::new,
					Accumulator::accumulate, Accumulator::combine, Accumulator::getCustomers));

			// Using Stream API to group and sort the items as per requirement given
			Map<String, Map<String, Map<String, List<Customer>>>> groupedCustomers = customers.stream()
					.sorted(Comparator.comparing(Customer::getSequenceID))
					.collect(Collectors.groupingBy(Customer::getGroup, groupByCountryAndSequenceID()));

			ObjectMapper mapper = new ObjectMapper();
			mapper.enable(SerializationFeature.INDENT_OUTPUT);

			// create the Json files in the path provided as second program argument
			createJsonFiles(args[1], groupedCustomers, mapper);
		} catch (FileNotFoundException e) {
			System.out.println("FileNotFoundException :" + e);
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("IOException :" + e);
			e.printStackTrace();
		}

	}
	
	/**
     * This method returns a map after grouping country and sequenceIds
     */
	private static Collector<Customer, ?, Map<String, Map<String, List<Customer>>>> groupByCountryAndSequenceID() {
		return Collectors.groupingBy(Customer::getCountry,
				Collectors.groupingBy(Customer::getSequenceID, LinkedHashMap::new, Collectors.toList()));
	}
	
	/**
     * This method converts the {@code groupedCustomers} map to json files
     * JSON conversion is done in two ways first by creating separate files for
     * each group_country groups and the other one a single final.json file combining
     * all groups
     */
	private static void createJsonFiles(String path,
			Map<String, Map<String, Map<String, List<Customer>>>> groupedCustomers, ObjectMapper mapper)
			throws JsonGenerationException, JsonMappingException, IOException {
		// create final.json
		mapper.writeValue(Paths.get(path + "final.json").toFile(), groupedCustomers);

		// create group_country.json
		groupedCustomers.forEach((group, outerMap) -> {
			outerMap.forEach((country, innerMap) -> {
				try {
					mapper.writeValue(Paths.get(path + group + "_" + country + ".json").toFile(), innerMap);
				} catch (IOException e) {
					System.out.println("IOException :" + e);
					e.printStackTrace();
				}
			});
		});
	}
}
