package presentation.Book;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import businesslogic.BusinessLogicInterface;
import dtos.BookDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UpdateBookForm {

    private static final Logger logger = LogManager.getLogger(UpdateBookForm.class);

    private JFrame frame;
    private JComboBox<BookDTO> bookList;
    private JTextField titleField;
    private JButton updateButton;

    private BusinessLogicInterface bookBLL;

    public UpdateBookForm(BusinessLogicInterface bll) {
		 logger.info( "Entered UpdateBookForm Class");
        bookBLL = bll;
        frame = new JFrame("Update Book");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 400);
        frame.setLocationRelativeTo(null); // center the window


        JPanel panel = new JPanel();
        frame.add(panel);
        panel.setLayout(new GridLayout(4, 1));

        JLabel titleLabel = new JLabel("Select a book to update:");
        bookList = new JComboBox<>();
        updateBookList();
        JLabel newTitleLabel = new JLabel("New Title:");
        titleField = new JTextField();
        updateButton = new JButton("Update");

        panel.add(titleLabel);
        panel.add(bookList);
        panel.add(newTitleLabel);
        panel.add(titleField);
        panel.add(updateButton);

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateSelectedBook();
            }
        });
    }

    public void showForm() {
        frame.setVisible(true);
    }

    private void updateBookList() {
        try {
            List<BookDTO> books = bookBLL.getAllBooks();
            bookList.setModel(new DefaultComboBoxModel<>(books.toArray(new BookDTO[0])));
        } catch (Exception e) {
            // Log the exception
            logger.error("Error updating book list", e);
        }
    }

    private void updateSelectedBook() {
        try {
            BookDTO selectedBook = (BookDTO) bookList.getSelectedItem();
            if (selectedBook == null) {
                JOptionPane.showMessageDialog(frame, "Please select a book to update.");
                return;
            }

            String newTitle = titleField.getText();
            if (newTitle.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please enter a new title.");
                return;
            }

            selectedBook.setBookTitle(newTitle);
            bookBLL.updateBook(selectedBook);

            JOptionPane.showMessageDialog(frame, "Book updated successfully.");

            updateBookList();
        } catch (Exception e) {
            // Log the exception
            logger.error("Error updating book", e);
        }
    }
}
