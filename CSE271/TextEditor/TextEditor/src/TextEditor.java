/* Name: Noah Dunn
 * Class: CSE 271
 * Date: 4/10/2018 
 * File: TextEditor
 * Assignment: Construct a word editor
 */
 
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JFileChooser;

public class TextEditor extends JFrame {
	//Establish all important variables as privatee
	private JPanel  buttonPanel;
	private JButton newButton;
	private JButton openButton;
	private JButton saveButton;
	private JButton saveAsButton;
	private JPanel textAreaRegion;
	private JTextArea textArea;
	private JScrollPane scrollPane; 
	private JPanel updaterPanel;
	private JLabel updaterLabel;

	
	public TextEditor(){ //Main constructor to build all the components
		this.setTitle("Text Editor");
		createButtons();
		textAreaGeneration();
		updaterPanelGeneration();
		
		
		
		
		
		
	}
		private void createButtons() { //Creates all buttons, assigns them to a panel, gives them action listeners
			
			buttonPanel = new JPanel();
			this.add(buttonPanel, BorderLayout.NORTH);
			
			newButton = new JButton();
			newButton.setText("New");
			newButton.addActionListener(new newButtonAction());
			
			openButton = new JButton();
			openButton.setText("Open");
			openButton.addActionListener(new openButtonAction());
			
			saveButton = new JButton();
			saveButton.setText("Save");
			saveButton.addActionListener(new saveButtonAction());
			
			saveAsButton = new JButton();
			saveAsButton.setText("Save As ...");
			saveAsButton.addActionListener(new saveAsButtonAction());
			
			buttonPanel.add(newButton);
			buttonPanel.add(openButton);
			buttonPanel.add(saveButton);
			buttonPanel.add(saveAsButton);
			
		}
		
		private void textAreaGeneration() {//Create the text area needed
			textAreaRegion = new JPanel();
			textArea = new JTextArea(35,60);
			textArea.setEditable(true);
			scrollPane = new JScrollPane(textArea);
			textAreaRegion.add(scrollPane);
			this.add(textAreaRegion, BorderLayout.CENTER);
		}
		
		private void updaterPanelGeneration() {//Create the panel that displays the current file 
			updaterPanel = new JPanel();
			updaterLabel = new JLabel();
			updaterLabel.setText("None");
			updaterPanel.add(updaterLabel);
			this.add(updaterPanel,BorderLayout.SOUTH);
		}
		
		class newButtonAction implements ActionListener{//Action Listener that allows the New Button to work

			public void actionPerformed(ActionEvent arg0) {
				textArea.setText("");
				updaterLabel.setText("None");
				
			}
			
		}
		class openButtonAction implements ActionListener{//Action Listener that enables files to be opened 

			public void actionPerformed(ActionEvent arg0) {
				textArea.setText("");
				JFileChooser opener = new JFileChooser();
				opener.setCurrentDirectory(new File("."));
				opener.setDialogTitle("Open");
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files","txt");
				opener.setFileFilter(filter);
				if(opener.showOpenDialog(openButton) == JFileChooser.APPROVE_OPTION) { //Only processes file output if a file is selected
					String fileLocation = opener.getSelectedFile().getAbsolutePath();
					if(fileLocation.endsWith(".txt")) {
					File file = new File(fileLocation);
					
					try { //Try-catch block to ensure the file actually is usable
						Scanner in = new Scanner(file);
						textArea.setText("");
						while(in.hasNextLine()) {
							textArea.append("" + in.nextLine() + "\n");
						}
						in.close();
						
					}
					catch(FileNotFoundException e) {
						System.out.println("Incorrect File Choice!");
					}
				
					updaterLabel.setText(fileLocation);
					}
				}
				else {
					System.out.println("Incorrect File Type!");
				}
				}
					}
			
			
			
		
		class saveButtonAction implements ActionListener{//ActionListener for the save button

			public void actionPerformed(ActionEvent arg0) { 
				
				if(updaterLabel.getText() == "None") { //If there is no file in use, open the save as menu
						createSaveFile();	
				}
				else { //If there is a file in use, overwrite the changes.
							String saveFileLocation = updaterLabel.getText();
							File file = new File(saveFileLocation);
							try {
								PrintWriter writer = new PrintWriter(file);
								for(String line: textArea.getText().split("\n")) { 
									writer.println(line); 
								}
								writer.close();
								
							} catch (FileNotFoundException e) {
								System.out.println("Incorrect way to operate this system!");
							}
							
							updaterLabel.setText(saveFileLocation);
							
						}
						

					}
					
					
					
					
				}
					
				
				
			
			
		
		class saveAsButtonAction implements ActionListener{

			public void actionPerformed(ActionEvent arg0) {
				
				createSaveFile();
				
			}
		}
			private void createSaveFile() { //This method generates a save file by activating a dialog box, saving the directory, and then writing to it
				
					JFileChooser saver = new JFileChooser();
					saver.setCurrentDirectory(new File("."));
					saver.setDialogTitle("Save");
					FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files","txt");
					saver.setFileFilter(filter);
					if(saver.showSaveDialog(saveButton) == JFileChooser.APPROVE_OPTION) {
						String saveFileLocation = saver.getSelectedFile().getAbsolutePath();
						if(saveFileLocation.endsWith(".txt")) {
							File file = new File(saveFileLocation);
							try {
								PrintWriter writer = new PrintWriter(file);
								for(String x: textArea.getText().split("\n")) { 
									writer.println(x); 
								}
								writer.close();
							} catch (FileNotFoundException e) {
								System.out.println("Incorrect way to operate this system!");
							}
							
							updaterLabel.setText(saveFileLocation);
							}
							else {
								System.out.println("This is not an appropriate file type");
							}
					}
			}
		}

		
		
		

	



