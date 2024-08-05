package presentation.Book;

import javax.swing.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import businesslogic.*;

import dataacess.FacadeDalInterface;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import dtos.BookDTO;

public class CreateBookForm {
	private JFrame frame;
	private JTextField titleField;
	private JButton createButton;
	FacadeDalInterface dal;
    private static final Logger logger = LogManager.getLogger(UpdateBookForm.class);
	private BusinessLogicInterface bookBLL;

	public CreateBookForm(BusinessLogicInterface bll) {
		logger.info("Entered CreateBookForm Class");

		bookBLL = bll;
		frame = new JFrame("Create Book");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(800, 400);
		frame.setLocationRelativeTo(null); // centre the window

		JPanel panel = new JPanel();
		frame.add(panel);
		panel.setLayout(new GridLayout(3, 1));

		JLabel titleLabel = new JLabel("Enter The Title of New Book :");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
		panel.add(titleLabel, BorderLayout.CENTER);

		titleField = new JTextField();
		createButton = new JButton("Create Book");

		panel.add(titleField);
		panel.add(createButton);

		createButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String title = titleField.getText();
				if (!title.isEmpty()) {
					BookDTO newBook = new BookDTO(0, title);
					bookBLL.createBook(newBook); // Use the instance method
					JOptionPane.showMessageDialog(frame, "Book created SuccessFully");
				} else {
					JOptionPane.showMessageDialog(frame, "Please enter a valid book title.");
				}
			}
		});
	}

	public void showForm() {
		frame.setVisible(true);
	}
}
