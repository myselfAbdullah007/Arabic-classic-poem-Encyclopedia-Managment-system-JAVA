package dataacess;

import java.io.File;
import java.sql.SQLException;
import java.util.List;

import dtos.BookDTO;
import dtos.PoemDTO;
import dtos.VerseDTO;

public interface FacadeDalInterface extends BookInterface, PoemInterface, VerseInterface,RootInterface,FileImporterInterface {
	public void createBook(BookDTO book);

	public List<BookDTO> getAllBooks();

	public void updateBook(BookDTO book);

	public void deleteBook(int bookID);

	public int createVerse(int poemID, String verse1, String verse2) throws Exception;

	public List<PoemDTO> getAllPoems() throws SQLException;

	public boolean updatePoem(int poemID, String newTitle) throws SQLException;

	public PoemDTO getPoemByID(int poemID);

	public void createVerse(int poemID, String verse) throws Exception;

	public List<VerseDTO> getVersesForPoem(int poemID);

//	public void updateVerse(VerseDTO verse);

//	public void deleteVerse(int verseID);
	public Object[][] transformDataForTable(List<PoemDTO> allPoems);
	
	public List<VerseDTO> getVerseByRootID(String name);
	
	public Boolean setManualRoot(String value) throws SQLException;

	public List<dtos.TokenDTO> getTokenByVerseID(int verseID);
	public  void assignTokensRootsPOS(String verse1, String verse2, int poemID,int verseID);
	public void importFile(File selectedFile);

}
