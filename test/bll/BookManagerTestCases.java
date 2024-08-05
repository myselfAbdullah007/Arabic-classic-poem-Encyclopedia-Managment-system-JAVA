package bll;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import businesslogic.BookManagerInterface;
import dal.BookDaoStub;
import dataacess.BookInterface;
import dtos.BookDTO;

public class BookManagerTestCases {

    private BookManagerInterface bookManager;
    private BookInterface bookDao;

    @Before
    public void setUp() {
        // Initialize with the stubs
        bookDao = new BookDaoStub();
        bookManager = new BookManagerTesting(bookDao);
    }

    @Test
    public void testCreateBook_Success() {
        // Arrange
        BookDTO book = new BookDTO(4, "Test Book");

        // Act
        bookManager.createBook(book);

        // Assert
        System.out.println("Actual books: " + bookDao.getAllBooks()); // Print actual books
        assertEquals(4, bookDao.getAllBooks().size());
        assertEquals(book, bookDao.getAllBooks().get(3));
    }


    
    @Test
    public void testGetAllBooks_Success() {
        // Arrange
        BookDTO book1 = new BookDTO(5, "Book5");
        BookDTO book2 = new BookDTO(6, "Book6");

        bookDao.createBook(book1);
        bookDao.createBook(book2);

        // Act
        List<BookDTO> books = bookDao.getAllBooks();

        // Assert
       // assertEquals();
        
        // Print the actual books for debugging
        System.out.println("Actual books: " + books);
        
        assertTrue(books.contains(book1));
        assertTrue(books.contains(book2));
    }


    @Test
    public void testUpdateBook_Success() {
        // Arrange
        BookDTO book = new BookDTO(1, "Updated Title");
        bookDao.createBook(book);

        // Act
        book.setBookTitle("Updated Title");
        bookDao.updateBook(book);

        // Assert
        assertEquals("Updated Title", bookDao.getAllBooks().get(0).getBookTitle());
    }

    @Test
    public void testDeleteBook_Success() {
        // Arrange
        BookDTO book = new BookDTO(9, "To be deleted");
        bookDao.createBook(book);

        // Act
        bookDao.deleteBook(9);
        List<BookDTO> books = bookDao.getAllBooks();
        // Assert
        assertFalse(books.contains(book));
    }
}
