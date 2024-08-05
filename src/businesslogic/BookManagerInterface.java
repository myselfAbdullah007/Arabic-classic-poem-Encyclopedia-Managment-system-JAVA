package businesslogic;

import java.util.List;

import dtos.BookDTO;

public interface BookManagerInterface {
	
	 public void createBook(BookDTO book);
	 public List<BookDTO> getAllBooks();
	 public void updateBook(BookDTO book);
	 public void deleteBook(int bookID);
}
