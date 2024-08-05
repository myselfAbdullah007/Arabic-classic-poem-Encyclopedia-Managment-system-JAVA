package businesslogic;

import java.util.List;

import dataacess.FacadeDalInterface;
import dtos.BookDTO;

public class BookManager implements BookManagerInterface {
	private FacadeDalInterface facade;

	public BookManager(FacadeDalInterface dal) {
		this.facade = dal;
	}

	@Override
	public void createBook(BookDTO book) {
		facade.createBook(book);
	}

	@Override
	public List<BookDTO> getAllBooks() {
		return facade.getAllBooks();
	}

	@Override
	public void updateBook(BookDTO book) {
		facade.updateBook(book);
	}

	@Override
	public void deleteBook(int bookID) {
		facade.deleteBook(bookID);
	}

}
