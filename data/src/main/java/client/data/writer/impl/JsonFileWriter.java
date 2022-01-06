package client.data.writer.impl;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import client.data.pojo.Customer;
import client.data.writer.FileWriter;

public class JsonFileWriter implements FileWriter {

	/**
	 * This method converts the {@code groupedCustomers} map to json files JSON
	 * conversion is done in two ways first by creating separate files for each
	 * group_country groups and the other one a single final.json file combining all
	 * groups
	 * 
	 * @author Ashok Kumar Karan
	 */
	@Override
	public void writetoFile(String filePath, Map<String, Map<String, Map<String, List<Customer>>>> groupedCustomers)
			throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);

		// create final.json
		mapper.writeValue(Paths.get(filePath + "final.json").toFile(), groupedCustomers);

		// create group_country.json
		groupedCustomers.forEach((group, outerMap) -> {
			outerMap.forEach((country, innerMap) -> {
				try {
					mapper.writeValue(Paths.get(filePath + group + "_" + country + ".json").toFile(), innerMap);
				} catch (IOException e) {
					System.out.println("IOException :" + e);
					e.printStackTrace();
				}
			});
		});

	}

}
