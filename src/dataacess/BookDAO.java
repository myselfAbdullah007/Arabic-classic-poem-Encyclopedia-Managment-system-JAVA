package dataacess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import dtos.BookDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.spi.LoggerAdapter;
public class BookDAO implements BookInterface{
	DatabaseConnector connection;
    private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(LoggerAdapter.class.getName());

    public BookDAO(DatabaseConnector dbConnector) {
        logger.info("Inside BookDAO");

    	connection = dbConnector;
    }

    public void createBook(BookDTO book) {
        try {
            // Get the maximum 'BookID' from the 'books' table
            String getMaxBookIDQuery = "SELECT MAX(BookID) FROM books";
            PreparedStatement maxIdStatement = connection.prepareStatement(getMaxBookIDQuery);
            ResultSet maxIdResult = maxIdStatement.executeQuery();

            int maxBookID = 0;
            if (maxIdResult.next()) {
                maxBookID = maxIdResult.getInt(1);
            }

            // Increment the 'BookID' for the new record
            book.setBookID(maxBookID + 1);

            // Insert the new book record
            String sql = "INSERT INTO books (BookID, BookTitle) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, book.getBookID());
            statement.setString(2, book.getBookTitle());
            statement.executeUpdate();
            
            logger.info("Book created successfully. Book: " + book);
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("Error creating book: " + book, e);
        }
    }

    public List<BookDTO> getAllBooks() {
        List<BookDTO> books = new ArrayList<>();
        try {
            String sql = "SELECT * FROM books";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int bookID = resultSet.getInt("BookID");
                String bookTitle = resultSet.getString("BookTitle");
                books.add(new BookDTO(bookID, bookTitle));
                logger.info("Retrieved all books: " + books);

            }
        } catch (SQLException e) {
            logger.error("Error getting all books", e);
            e.printStackTrace();
            
        }
        return books;
    }

    public void updateBook(BookDTO book) {
        try {
            String sql = "UPDATE books SET BookTitle = ? WHERE BookID = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, book.getBookTitle());
            statement.setInt(2, book.getBookID());
            statement.executeUpdate();
            logger.info("Book updated successfully. Book: " + book);
        } catch (SQLException e) {
            logger.error("Error updating book: " + book, e);
            e.printStackTrace();
        }
    }

    public void deleteBook(int bookID) {
        try {
            String sql = "DELETE FROM books WHERE BookID = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, bookID);
            statement.executeUpdate();
            logger.info("Book deleted successfully. Book ID: " + bookID);
        } catch (SQLException e) {
            logger.error("Error deleting book. Book ID: " + bookID, e);
            e.printStackTrace();
        }
    }
}
