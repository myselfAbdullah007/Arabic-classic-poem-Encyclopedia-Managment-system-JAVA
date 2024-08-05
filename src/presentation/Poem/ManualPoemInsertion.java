package presentation.Poem;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import businesslogic.BusinessLogicInterface;
import dtos.BookDTO;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ManualPoemInsertion {

	private JFrame frame;
	private JComboBox<BookDTO> bookDropdown;
	private JButton addButton;
	private JButton submitButton;
	private JPanel poemsPanel;
	BusinessLogicInterface bll;

	private ArrayList<JTextField> poemTextFields;
	PoemForm parentForm;

	public ManualPoemInsertion(BusinessLogicInterface bll) {
		poemTextFields = new ArrayList<>();
		this.bll= bll;
	}

	public void showGUI() {
	    frame = new JFrame("Manual Poem Insertion");
	    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    frame.setSize(800, 400);
	    frame.setLocationRelativeTo(null); // Center the window

	    JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
	    mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

	    // Label for selecting a book
	    JLabel selectBookLabel = new JLabel("Select book to add poem into:");
	    selectBookLabel.setFont(new Font("Arial", Font.BOLD, 14));

	    List<BookDTO> books = bll.getAllBooks();
	    bookDropdown = new JComboBox<>(books.toArray(new BookDTO[0]));

	    // Panel for book selection and its label
	    JPanel bookSelectionPanel = new JPanel();
	    bookSelectionPanel.add(selectBookLabel);
	    bookSelectionPanel.add(bookDropdown);

	    poemsPanel = new JPanel();
	    poemsPanel.setLayout(new BoxLayout(poemsPanel, BoxLayout.Y_AXIS));

	    // Label for poem title
	    JLabel poemTitleLabel = new JLabel("Write Poem Title Below:");
	    poemTitleLabel.setFont(new Font("Arial", Font.BOLD, 14));

	    JTextField firstPoemField = new JTextField(20);
	    poemTextFields.add(firstPoemField);

	    // Adding label and first text field to poems panel
	    poemsPanel.add(poemTitleLabel);
	    poemsPanel.add(firstPoemField);

	    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

	    addButton = new JButton("Add Poem");
	    addButton.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            JTextField newPoemField = new JTextField(20);
	            poemTextFields.add(newPoemField);
	            poemsPanel.add(newPoemField);
	            poemsPanel.revalidate();
	        }
	    });

	    submitButton = new JButton("Submit");

	      
        submitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				BookDTO selectedBook = (BookDTO) bookDropdown.getSelectedItem();
				for (JTextField textField : poemTextFields) {
					try {
						bll.createPoem(selectedBook.getBookID(), textField.getText());
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});

        buttonPanel.add(addButton);
        buttonPanel.add(submitButton);

        mainPanel.add(bookSelectionPanel, BorderLayout.NORTH);
        mainPanel.add(new JScrollPane(poemsPanel), BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.PAGE_END);

        frame.add(mainPanel);
        frame.setVisible(true);
		
	}    
}
