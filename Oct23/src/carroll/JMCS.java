package carroll;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class JMCS {

	// Data Members
	private File inputFile;
	private ArrayList<String> catalog;

	// Constructor
	public JMCS(File inputFile) {
		this.inputFile = inputFile;
		catalog = new ArrayList<>();
	}

	public void buildCatalog() {
		// Create a scanner to read the file
		Scanner fileInputScan = null;

		try {
			fileInputScan = new Scanner(inputFile);
			search(fileInputScan);
		} catch (FileNotFoundException e) {
			System.out.println("Error - this file could not be found");
		} finally {
			if (fileInputScan != null) {
				fileInputScan.close();
			}
		}

		// Handle case where no valid classes, methods, or structures are found
		if (catalog.isEmpty()) {
			System.out.println("No classes, methods, or structures were found in the file.");
		}
	}

	// Search Utility : Finds classes, methods, and control structures
	private void search(Scanner fileInputScan) {
		Stack<String> stack = new Stack<>();
		String codeLine;
		String possibleMCS = ""; // Possible method, class, or structure

		while (fileInputScan.hasNextLine()) {
			// Task 1: Grab a single line of code and trim whitespace from the front
			codeLine = fileInputScan.nextLine().trim();

			// Skip lines that aren't valid code
			if (!isCode(codeLine)) {
				continue;
			}

			// Task 2: Check if the code line is the start of an MCS
			if (codeLine.length() > 0) {
				if (!codeLine.equals("{") && !codeLine.equals("}")) {
					// Save potential MCS, if it’s not an opening or closing brace
					possibleMCS = codeLine;
				} else if (codeLine.equals("{")) {
					// If opening brace, push the possible MCS onto the stack
					stack.push(possibleMCS);
				} else if (codeLine.equals("}") && !stack.isEmpty()) {
					// If closing brace, pop from stack and add to catalog
					catalog.add(0, stack.pop());
				}
			}
		}
	}

	// Helper method to check if a line is valid code
	private boolean isCode(String str) {
		// Ignore empty lines and comments
		return str.length() > 0 && !str.startsWith("//") && !str.startsWith("/*") && !str.startsWith("*") && !str.startsWith("*/");
	}

	@Override
	public String toString() {
		StringBuilder MCSString = new StringBuilder();
		for (String item : catalog) {
			MCSString.append(item).append("\n");
		}
		return MCSString.toString();
	}

	// Additional method to get catalog as a list
	public List<String> getCatalog() {
		return new ArrayList<>(catalog); // Return a copy to avoid modification
	}

	// Optional method to print catalog directly
	public void printCatalog() {
		if (catalog.isEmpty()) {
			System.out.println("Catalog is empty.");
		} else {
			System.out.println("Catalog Contents:");
			for (String item : catalog) {
				System.out.println(item);
			}
		}
	}
}
