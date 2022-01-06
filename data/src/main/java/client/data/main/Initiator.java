package client.data.main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import client.data.pojo.Customer;
import client.data.processor.DataProcessor;
import client.data.processor.impl.DataProcessorImpl;
import client.data.reader.FileReader;
import client.data.reader.impl.CSVFileReader;
import client.data.writer.FileWriter;
import client.data.writer.impl.JsonFileWriter;

/**
 * This is the initiator class which is the entrypoint to the program containing
 * main method. This accepts two argument as input one is the input file path
 * and other one is the output file path.
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

			String inputFilePath = args[0];
			String outputFilePath = args[1];

			// reader
			FileReader reader = new CSVFileReader();
			List<Customer> customers = reader.readData(inputFilePath);

			// processor
			DataProcessor processor = new DataProcessorImpl();
			Map<String, Map<String, Map<String, List<Customer>>>> groupedCustomers = processor.processData(customers);

			// writer
			FileWriter writer = new JsonFileWriter();
			writer.writetoFile(outputFilePath, groupedCustomers);

		} catch (FileNotFoundException e) {
			System.out.println("FileNotFoundException :" + e);
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("IOException :" + e);
			e.printStackTrace();
		}
	}
}