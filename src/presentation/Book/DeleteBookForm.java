package presentation.Book;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import businesslogic.BusinessLogicInterface;
import dtos.BookDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.spi.LoggerAdapter;

public class DeleteBookForm {
	private JFrame frame;
	private JComboBox<BookDTO> bookList;
	private JButton deleteButton;
	private BusinessLogicInterface bookBLL;
	private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(LoggerAdapter.class.getName());

	public DeleteBookForm(BusinessLogicInterface bll) {
		 logger.info( "Entered DeleteBookForm Class");
		bookBLL = bll;
		frame = new JFrame("Delete Book");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(800, 400);
		frame.setLocationRelativeTo(null); // centre the window


		JPanel panel = new JPanel();
		frame.add(panel);
		panel.setLayout(new GridLayout(3, 1));

		JLabel titleLabel = new JLabel("Select a book to delete:");
		bookList = new JComboBox<>();
		updateBookList();
		deleteButton = new JButton("Delete");

		panel.add(titleLabel);
		panel.add(bookList);
		panel.add(deleteButton);

		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				deleteSelectedBook();
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

	private void deleteSelectedBook() {
		try {
			BookDTO selectedBook = (BookDTO) bookList.getSelectedItem();
			if (selectedBook == null) {
				JOptionPane.showMessageDialog(frame, "Please select a book to delete.");
				return;
			}

			int confirm = JOptionPane.showConfirmDialog(frame, "Are you sure you want to delete this book?",
					"Confirm Deletion", JOptionPane.YES_NO_OPTION);
			if (confirm == JOptionPane.YES_OPTION) {
				bookBLL.deleteBook(selectedBook.getBookID());
				JOptionPane.showMessageDialog(frame, "Book deleted successfully.");

				updateBookList();
			}
		} catch (Exception e) {
			// Log the exception
			logger.error("Error Deleting book", e);
		}
	}
}
