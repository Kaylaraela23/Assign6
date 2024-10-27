package carroll;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class SA {

	// Data members

	private File inputFile;
	private String[] sentences;
	private ArrayList<Integer> sentenceWordCount;

	// Constructor
	public SA(File inputFile) {
		this.inputFile = inputFile;
		sentences = null;
		sentenceWordCount = new ArrayList<>();
	}

	public void processSentences() {
		// create a canner to read the essay file

		Scanner fileInputScan = null;

		// Open scanner to essay file and get word count
		try {
			fileInputScan = new Scanner(inputFile);
			getSentenceWordCount(fileInputScan);
		} catch (FileNotFoundException e) {
			System.out.println("Error - File not found.");
		} finally {
			if (fileInputScan != null)
				fileInputScan.close();
		}
	}

	private void getSentenceWordCount(Scanner fileInputScan) {
		while (fileInputScan.hasNextLine()) {
			// Task 1: grab a single paragraph and split into sentences
			String paragraphs = fileInputScan.nextLine();
			sentences = paragraphs.split("[.!?]");

			// Task 2: Process each sentence
			for (int i = 0; i < sentences.length; i++) {
				// Remove leading and trailing spaces from the sentence
				String s = sentences[i].trim();

				// split the sentence into words
				String[] words = s.split("\\s+");

				// count thr words and store in sentenceWordCount
				if (words.length > 1 || !words[0].equals("")) {
					// ensures empty sentences aren't coumted
					sentenceWordCount.add(words.length);
				}
			}
		}

	}

	public int getAverage() {
		double sum = 0;
		for (int i = 0; i < sentenceWordCount.size(); i++) {
			sum += sentenceWordCount.get(i);
		}
		return (int) (sum / sentenceWordCount.size());
	}
}
