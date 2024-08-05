package businesslogic;

import java.sql.SQLException;
import java.util.List;

import dtos.BookDTO;
import dtos.PoemDTO;
import dtos.RootDTO;
import dtos.TokenDTO;
import dtos.VerseDTO;

public interface BusinessLogicInterface extends BookManagerInterface, PoemManagerInterface, TokenBlInterface,VerseManagerInterface, RootManagerInterface,DataImportInterface {
	public void createBook(BookDTO book);

	public List<BookDTO> getAllBooks();

	public void updateBook(BookDTO book);

	public void deleteBook(int bookID);

	public int createPoem(int bookID, String poemTitle) throws Exception;

	public List<PoemDTO> getAllPoems() throws SQLException;

	public boolean updatePoem(int poemID, String newTitle) throws SQLException;

	public PoemDTO getPoemByID(int poemID);

	public void addRoot(String root);

	public void updateRoot(String oldValue, String newValue) throws SQLException;

	public void deleteRoot(String value) throws SQLException;

	public Object[][] transformDataForTable(List<PoemDTO> allPoems);

	public List<String> getAllRoots();
	
	public List<VerseDTO> getVerseFromRoot(String name) throws SQLException;
	
	public List<RootDTO> getRootsFromVerses(int verseID) throws SQLException;

	public Boolean setManualRoot(String value) throws SQLException;

	public List<TokenDTO> getTokenByVerseID(int verseID);
	
}
