package dal;

import java.util.ArrayList;
import java.util.List;

import dataacess.BookInterface;
import dtos.BookDTO;

public class BookDaoStub implements BookInterface {

    private final List<BookDTO> books;

    public BookDaoStub() {
        this.books = new ArrayList<>();
        initializeDummyData();
    }

    private void initializeDummyData() {
        createBook(new BookDTO(1, "Book1"));
        createBook(new BookDTO(2, "Book2"));
        createBook(new BookDTO(3, "Book3"));
    }

    @Override
    public void createBook(BookDTO book) {
        books.add(book);
    }

    @Override
    public List<BookDTO> getAllBooks() {
        return new ArrayList<>(books);
    }

    @Override
    public void updateBook(BookDTO book) {
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getBookID() == book.getBookID()) {
                books.set(i, book);
                return;
            }
        }
    }

    @Override
    public void deleteBook(int bookID) {
        books.removeIf(book -> book.getBookID() == bookID);
    }
}
