package carroll;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;

public class ViewController {

	// Application elements: UI and Objects
	private static JFrame jframeWindow;
	private static JPanel panel;
	private static JButton inputBtn;
	private static JTextField inputTxt;
	private static JButton runAnalysisBtn;
	private static JTextArea display;
	private static File fileToRead;

	public static void main(String[] args) {
		constructAppWindow();
		addListenerEvents();
	}

	private static void addListenerEvents() {
		inputBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				requestInputFile();
			}

			private void requestInputFile() {
				JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
				int returnValue = jfc.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					fileToRead = jfc.getSelectedFile();
					inputTxt.setText(fileToRead.toString());
				}
			}
		});

		runAnalysisBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SA analyzer = new SA(fileToRead);
				analyzer.processSentences(); // Added missing semicolon
				display.setText("" + analyzer.getAverage());
			}
		});
	}

	private static void constructAppWindow() {
		jframeWindow = new JFrame();
		jframeWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Buttons, text elements
		inputBtn = new JButton("Code File: ");
		inputTxt = new JTextField(25);
		inputTxt.setEditable(false);
		runAnalysisBtn = new JButton("Build Catalog"); // Corrected spelling

		display = new JTextArea("", 10, 400);
		display.setEditable(false);
		display.setLineWrap(true);
		JScrollPane scrollPane = new JScrollPane(display);
		scrollPane.setPreferredSize(new Dimension(390, 400));

		panel = new JPanel();
		panel.setPreferredSize(new Dimension(400, 520));
		panel.setBackground(Color.DARK_GRAY);
		panel.add(inputBtn);
		panel.add(inputTxt);
		panel.add(runAnalysisBtn);
		panel.add(scrollPane);

		jframeWindow.add(panel);
		jframeWindow.pack();
		jframeWindow.setVisible(true);
	}
}
