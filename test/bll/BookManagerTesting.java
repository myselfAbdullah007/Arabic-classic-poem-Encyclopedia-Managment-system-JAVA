package bll;

import java.util.List;

import businesslogic.BookManagerInterface;
import dataacess.BookInterface;
import dtos.BookDTO;

public class BookManagerTesting implements BookManagerInterface {

    private BookInterface bookDao;

    public BookManagerTesting(BookInterface bookDao) {
        this.bookDao = bookDao;
    }

    @Override
    public void createBook(BookDTO book) {
        bookDao.createBook(book);
    }

    @Override
    public List<BookDTO> getAllBooks() {
        return bookDao.getAllBooks();
    }

    @Override
    public void updateBook(BookDTO book) {
        bookDao.updateBook(book);
    }

    @Override
    public void deleteBook(int bookID) {
        bookDao.deleteBook(bookID);
    }
}
