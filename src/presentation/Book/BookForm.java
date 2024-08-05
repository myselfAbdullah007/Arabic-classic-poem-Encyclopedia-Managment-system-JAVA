package presentation.Book;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import businesslogic.BusinessLogicInterface;
import dtos.BookDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BookForm {
    private static final Logger logger = LogManager.getLogger(BookForm.class);

    private JFrame frame;

    public BookForm(BusinessLogicInterface bll) {
		 logger.info( "Entered BookForm Class");
        frame = new JFrame("Book CRUD Operations");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 400);

        JPanel panel = new JPanel();
        frame.add(panel);
        panel.setLayout(new GridLayout(4, 1));
        frame.setLocationRelativeTo(null); // center the window

        JLabel titleLabel = new JLabel("Book Management");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 40));
    //    titleLabel.setForeground(Color.DARK_GRAY);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        frame.add(titleLabel, BorderLayout.NORTH);

        JButton createButton = new JButton("Create New Book");
        JButton updateButton = new JButton("Update Existing Book");
        JButton deleteButton = new JButton("Delete Book");
        JButton viewButton = new JButton("View Books");

        // Customize the panel
        panel.setBorder(new EmptyBorder(new Insets(15, 15, 15, 15)));
       // panel.setBackground(Color.DARK_GRAY); // Set a background color

        panel.add(createButton);
        panel.add(updateButton);
        panel.add(deleteButton);
        panel.add(viewButton);

        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    CreateBookForm createBookForm = new CreateBookForm(bll);
                    createBookForm.showForm();
                } catch (Exception ex) {
                    // Log the exception
                    logger.error("Error creating new book", ex);
                }
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    UpdateBookForm updateBookForm = new UpdateBookForm(bll);
                    updateBookForm.showForm();
                } catch (Exception ex) {
                    // Log the exception
                    logger.error("Error updating book", ex);
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    DeleteBookForm deleteBookForm = new DeleteBookForm(bll);
                    deleteBookForm.showForm();
                } catch (Exception ex) {
                    // Log the exception
                    logger.error("Error deleting book", ex);
                }
            }
        });

        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    showBooks(bll);
                } catch (Exception ex) {
                    // Log the exception
                    logger.error("Error viewing books", ex);
                }
            }
        });
    }

    public void showForm() {
        frame.setVisible(true);
    }

    private void showBooks(BusinessLogicInterface bll) {
        try {
            List<BookDTO> books = bll.getAllBooks();

            DefaultListModel<String> listModel = new DefaultListModel<>();
            for (BookDTO book : books) {
                listModel.addElement(book.getBookTitle());
            }

            JList<String> bookList = new JList<>(listModel);
            JScrollPane scrollPane = new JScrollPane(bookList);

            JFrame listFrame = new JFrame("Books List");
            listFrame.add(scrollPane);
            listFrame.setSize(800, 400);
            listFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            listFrame.setVisible(true);
        } catch (Exception ex) {
            // Log the exception
            logger.error("Error displaying books", ex);
        }
    }
}
