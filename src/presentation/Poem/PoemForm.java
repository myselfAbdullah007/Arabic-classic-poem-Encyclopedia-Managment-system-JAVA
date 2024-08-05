package presentation.Poem;

import javax.swing.*;

import businesslogic.BusinessLogicInterface;

import dtos.PoemDTO;
import dtos.VerseDTO;
import presentation.Verse.ManualVerseInsertion;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class PoemForm {
	Connection connection;
	private JFrame frame;
	private BusinessLogicInterface bll;
	private JComboBox<String> poemDropdown;
	private JList<VerseDTO> versesList;
	private JButton addVerseButton;
	private JButton updateVerseButton;
	private JButton deleteVerseButton;

	public PoemForm(BusinessLogicInterface bll) {
	    this.poemDropdown = new JComboBox<>(); // Initialize the JComboBox
		this.bll = bll;
	}

	public void refreshVersesList() throws SQLException {
		// Get the selected poem title
		List<PoemDTO> poems = bll.getAllPoems();
		String selectedPoemTitle = (String) poemDropdown.getSelectedItem();
		if (selectedPoemTitle != null) {
			// Get the corresponding PoemDTO based on the title
			PoemDTO selectedPoem = poems.stream().filter(poem -> poem.getPoemTitle().equals(selectedPoemTitle))
					.findFirst().orElse(null);

			if (selectedPoem != null) {
				// Get verses for the selected poem using the new function
				List<VerseDTO> verses = bll.getVersesByPoemID(selectedPoem.getPoemID());
				// Update the JList with the new verses
				versesList.setListData(verses.toArray(new VerseDTO[0]));
			}
		}
	}
	public void refreshPoemDropdown() throws SQLException {
	    // Get the updated list of poems
	    List<PoemDTO> updatedPoems = bll.getAllPoems();

	    // Create an array of strings from the updated poems' titles
	    String[] updatedPoemTitles = updatedPoems.stream().map(PoemDTO::getPoemTitle).toArray(String[]::new);

	    // Update the JComboBox model with the new titles
	    DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(updatedPoemTitles);
	    poemDropdown.setModel(model);

	    // Optionally, you can select the previously selected poem if available
	    String selectedPoemTitle = (String) poemDropdown.getSelectedItem();
	    if (selectedPoemTitle != null) {
	        poemDropdown.setSelectedItem(selectedPoemTitle);
	    }
	}

	public void showGUI() throws SQLException {

		frame = new JFrame("Poem Manager");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(800, 400);
		frame.setLocationRelativeTo(null); // Center the window
		// Adding buttons
		addVerseButton = createButton("Add Verse", 10, 10, 100, 30);
		updateVerseButton = createButton("Update Verse", 120, 10, 100, 30);
		deleteVerseButton = createButton("Delete Verse", 230, 10, 100, 30);
		JPanel buttonsPanel = new JPanel(new FlowLayout());
		buttonsPanel.add(addVerseButton);
		buttonsPanel.add(updateVerseButton);
		buttonsPanel.add(deleteVerseButton);

		JPanel panel = new JPanel();
		panel.add(buttonsPanel, BorderLayout.NORTH);

		frame.add(panel);
		List<PoemDTO> poems = bll.getAllPoems();

		// Create an array of strings from the poems' titles
		String[] poemTitles = poems.stream().map(PoemDTO::getPoemTitle).toArray(String[]::new);

		// Explicitly specify the type parameter for JComboBox<String>
		poemDropdown = new JComboBox<>(poemTitles);
		poemDropdown.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					// Refresh the verses list based on the selected poem
					refreshVersesList();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
		});

		panel.add(poemDropdown);

		// Adding the scroll pane for verses
		versesList = new JList<>();
		JScrollPane scrollPane = new JScrollPane(versesList);
		scrollPane.setBounds(10, 60, 350, 250);
		panel.add(scrollPane, BorderLayout.CENTER);

		poemDropdown.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					// Refresh the verses list based on the selected poem
					refreshVersesList();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
		});

		addVerseButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					ManualVerseInsertion manualInsertion = new ManualVerseInsertion(bll);
					manualInsertion.showGUI();
					refreshVersesList();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
		});

		updateVerseButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				VerseDTO selectedVerse = versesList.getSelectedValue();
				if (selectedVerse != null) {
					// Ask the user for a new verse
					String newVerse1 = JOptionPane.showInputDialog(frame, "Enter the new verse 1:");
					String newVerse2 = JOptionPane.showInputDialog(frame, "Enter the new verse 2:");
					if (newVerse1 != null && !newVerse1.isEmpty() && newVerse2 != null && !newVerse2.isEmpty()) {
						// Update the verse in the database
						selectedVerse.setVerse1(newVerse1);
						selectedVerse.setVerse2(newVerse2);
						try {
							bll.updateVerse(selectedVerse);
							// Refresh the verses list
							refreshVersesList();
						} catch (SQLException ex) {
							ex.printStackTrace();
						}
					}
				} else {
					JOptionPane.showMessageDialog(frame, "Please select a verse to update.");
				}
			}
		});

		deleteVerseButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				VerseDTO selectedVerse = versesList.getSelectedValue();
				if (selectedVerse != null) {
					int confirm = JOptionPane.showConfirmDialog(frame, "Are you sure you want to delete this verse?",
							"Confirm Deletion", JOptionPane.YES_NO_OPTION);
					if (confirm == JOptionPane.YES_OPTION) {
						bll.deleteVerse(selectedVerse);
						// Refresh the verses list
						try {
							refreshVersesList();
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				} else {
					JOptionPane.showMessageDialog(frame, "Please select a verse to delete.");
				}
			}
		});

		buttonsPanel.setLayout(new FlowLayout());
		buttonsPanel.add(addVerseButton);
		buttonsPanel.add(updateVerseButton);
		buttonsPanel.add(deleteVerseButton);

		// Adding the panel to the frame
		frame.add(panel);
		frame.setVisible(true);

		// Initial refresh of the verses list based on the selected poem
		refreshVersesList();

		placeComponents(panel);
		frame.setVisible(true);
		frame.add(panel);

	}

	private void placeComponents(JPanel panel) {
		JLabel titleLabel = new JLabel("موسوعة الشعر العربية في العصر الجاهلية");
		titleLabel.setFont(new Font("Arial", Font.BOLD, 40));
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		frame.add(titleLabel, BorderLayout.NORTH);
		JButton importButton = createButton("Import Data", 10, 210, 100, 30);
		importButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				int returnValue = fileChooser.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooser.getSelectedFile();

					// Ask for confirmation
					int confirm = JOptionPane.showConfirmDialog(frame,
							"You have selected: " + selectedFile.getName()
									+ ".\nDo you want to proceed with importing this file?",
							"Confirm Import", JOptionPane.YES_NO_OPTION);

					if (confirm == JOptionPane.YES_OPTION) {
						// Proceed with import
						bll.importData(selectedFile);

						// Notify user of completion
						JOptionPane.showMessageDialog(frame, "Data has been successfully imported.",
								"Import Successful", JOptionPane.INFORMATION_MESSAGE);
					} else {
						// User chose not to proceed
						JOptionPane.showMessageDialog(frame, "Import cancelled.", "Cancelled",
								JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}
		});

		JButton updateButton = createButton("Update Poem", 120, 210, 100, 30);
		updateButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					List<PoemDTO> allPoems = bll.getAllPoems();
					if (allPoems.isEmpty()) {
						JOptionPane.showMessageDialog(frame, "No poems available to update.", "Info",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}

					// Convert the list of poems to a display format
					String[] poemTitles = allPoems.stream().map(PoemDTO::getPoemTitle).toArray(String[]::new);
					JList<String> poemList = new JList<>(poemTitles);
					JOptionPane.showMessageDialog(frame, new JScrollPane(poemList), "Select a Poem to Update",
							JOptionPane.PLAIN_MESSAGE);

					int selectedIndex = poemList.getSelectedIndex();
					if (selectedIndex != -1) {
						PoemDTO selectedPoem = allPoems.get(selectedIndex);
						String newTitle = JOptionPane.showInputDialog("Enter new title for the poem:",
								selectedPoem.getPoemTitle());
						if (newTitle != null && !newTitle.trim().isEmpty()) {
							boolean updated = bll.updatePoem(selectedPoem.getPoemID(), newTitle);
							if (updated) {
								refreshPoemDropdown() ;
								JOptionPane.showMessageDialog(frame, "Poem updated successfully.");
							} else {
								JOptionPane.showMessageDialog(frame, "Failed to update poem.");
							}
						}
					} else {
						JOptionPane.showMessageDialog(frame, "No poem selected.", "Info",
								JOptionPane.INFORMATION_MESSAGE);
					}
				} catch (Exception e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(frame, "Error fetching poems.", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		JButton viewAllButton = createButton("View All Poems", 230, 210, 100, 30);
		viewAllButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					List<PoemDTO> allPoems = bll.getAllPoems();

					if (allPoems == null || allPoems.isEmpty()) {
						JOptionPane.showMessageDialog(frame, "No poems found.", "Alert", JOptionPane.WARNING_MESSAGE);
						return;
					}

					Object[][] rowData = bll.transformDataForTable(allPoems);
					String[] columnNames = { "Title", "Verse1", "Verse2" };
					JTable table = new JTable(rowData, columnNames);

					JScrollPane scrollPane = new JScrollPane(table);
					JFrame tableFrame = new JFrame("All Poems with Verses");
					tableFrame.setSize(800, 400);
					tableFrame.add(scrollPane);
					tableFrame.setVisible(true);
					tableFrame.setFont(new Font("Arial", Font.PLAIN, 14));
					tableFrame.setLocationRelativeTo(null);
					tableFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

				} catch (Exception ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(frame, "Error fetching poems.", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		JButton manualInsertion = createButton("Manual Insertion", 10, 250, 100, 30);
		manualInsertion.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				try {
					ManualPoemInsertion manualPoemInsertion = new ManualPoemInsertion(bll);
					manualPoemInsertion.showGUI();
					refreshPoemDropdown() ;
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		JButton deleteButton = createButton("Delete Poem", 120, 250, 100, 30);

		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					List<PoemDTO> allPoems = bll.getAllPoems();
					if (allPoems.isEmpty()) {
						JOptionPane.showMessageDialog(frame, "No poems available to delete.", "Info",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}

					String[] poemTitles = allPoems.stream().map(PoemDTO::getPoemTitle).toArray(String[]::new);
					JList<String> poemList = new JList<>(poemTitles);
					JOptionPane.showMessageDialog(frame, new JScrollPane(poemList), "Select a Poem to Delete",
							JOptionPane.PLAIN_MESSAGE);

					int selectedIndex = poemList.getSelectedIndex();
					if (selectedIndex != -1) {
						int confirm = JOptionPane.showConfirmDialog(frame, "Are you sure you want to delete this poem?",
								"Confirm Deletion", JOptionPane.YES_NO_OPTION);
						if (confirm == JOptionPane.YES_OPTION) {
							PoemDTO selectedPoem = allPoems.get(selectedIndex);
							boolean deleted = bll.deletePoem(selectedPoem.getPoemID());
							if (deleted) {
								refreshPoemDropdown() ;
								JOptionPane.showMessageDialog(frame, "Poem deleted successfully.");
							} else {
								JOptionPane.showMessageDialog(frame, "Failed to delete poem.");
							}
						}
					} else {
						JOptionPane.showMessageDialog(frame, "No poem selected.", "Info",
								JOptionPane.INFORMATION_MESSAGE);
					}
				} catch (Exception e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(frame, "Error fetching poems.", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		JPanel bottomButtonsPanel = new JPanel(new GridLayout(1, 3)); // 1 row, 3 columns

		bottomButtonsPanel.add(importButton, BorderLayout.WEST);
		bottomButtonsPanel.add(updateButton, BorderLayout.CENTER);
		bottomButtonsPanel.add(viewAllButton, BorderLayout.EAST);
		bottomButtonsPanel.add(manualInsertion, BorderLayout.WEST);
		bottomButtonsPanel.add(deleteButton, BorderLayout.EAST);
		panel.add(bottomButtonsPanel, BorderLayout.SOUTH);

	}

	private JButton createButton(String text, int x, int y, int width, int height) {
		JButton button = new JButton(text);
		// button.setBounds(x, y, width, height);
		button.setFocusPainted(false);
		return button;
	}
}
