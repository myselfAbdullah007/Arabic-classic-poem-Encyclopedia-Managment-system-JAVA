package businesslogic;

import java.io.File;
import java.sql.SQLException;
import java.util.List;

import dataacess.FacadeDalInterface;
import dtos.BookDTO;
import dtos.PoemDTO;
import dtos.RootDTO;
import dtos.VerseDTO;
import dtos.TokenDTO;

public class FacadeBusinessLogic implements BusinessLogicInterface {

	BookManagerInterface manager;
	PoemManagerInterface manager2;
	TokenBlInterface manager3;
	VerseManagerInterface manager4;
	DataImportInterface manager5;

	public FacadeBusinessLogic(FacadeDalInterface dal) {
		super();
		manager = new BookManager(dal);
		manager2 = new PoemManager(dal);
		manager3 = new TokenManager(dal);
		manager4 = new VerseManager(dal);
		manager5 = new DataimportBLL(dal);
	}

	@Override
	public void createBook(BookDTO book) {
		manager.createBook(book);
	}

	@Override
	public List<BookDTO> getAllBooks() {
		// TODO Auto-generated method stub
		return manager.getAllBooks();
	}

	@Override
	public void updateBook(BookDTO book) {
		// TODO Auto-generated method stub
		manager.updateBook(book);
	}

	@Override
	public void deleteBook(int bookID) {
		// TODO Auto-generated method stub
		manager.deleteBook(bookID);
	}

	@Override
	public int createPoem(int bookID, String poemTitle) throws Exception {
		// TODO Auto-generated method stub
		return manager2.createPoem(bookID, poemTitle);
	}

	@Override
	public List<PoemDTO> getAllPoems() throws SQLException {
		// TODO Auto-generated method stub
		return manager2.getAllPoems();
	}

	@Override
	public boolean updatePoem(int poemID, String newTitle) throws SQLException {
		// TODO Auto-generated method stub
		return manager2.updatePoem(poemID, newTitle);
	}

	@Override
	public PoemDTO getPoemByID(int poemID) {
		// TODO Auto-generated method stub
		System.out.println("BLL Main Facad Class");
		return manager2.getPoemByID(poemID);
	}

	@Override
	public void addRoot(String root) {
		manager3.addRoot(root);
	}

	@Override
	public void updateRoot(String oldValue, String newValue) throws SQLException {
		manager3.updateRoot(oldValue, newValue);
	}

	@Override
	public void deleteRoot(String value) throws SQLException {
		manager3.deleteRoot(value);
	}

	@Override
	public int createVerse(int poemID, String misra, String Misra2) throws Exception {
		return manager4.createVerse(poemID, misra, Misra2);
	}

	@Override
	public List<VerseDTO> getVersesByPoemID(int poemID) {
		return manager4.getVersesByPoemID(poemID);
	}

	@Override
	public Object[][] transformDataForTable(List<PoemDTO> allPoems) {
		// TODO Auto-generated method stub
		return manager2.transformDataForTable(allPoems);
	}

	@Override
	public List<String> getAllRoots() {
		// TODO Auto-generated method stub
		return manager3.getAllRoots();
	}

	@Override
	public List<PoemDTO> getPoemsForBook(int bookID) throws SQLException {
		// TODO Auto-generated method stub
		return manager2.getPoemsForBook(bookID);
	}

	@Override
	public boolean deletePoem(int poemID) throws SQLException {
		// TODO Auto-generated method stub
		return manager2.deletePoem(poemID);
	}

	public void updateVerse(VerseDTO verse) {
		manager4.updateVerse(verse);
	}

	@Override
	public void deleteVerse(VerseDTO selectedVerse) {
		manager4.deleteVerse(selectedVerse);
	}

	@Override
	public List<VerseDTO> getVerseFromRoot(String name) throws SQLException {
		return manager3.getVerseFromRoot(name);
	}

	public List<RootDTO> getRootsFromVerses(int verseID) throws SQLException {
		return manager3.getRootsFromVerses(verseID);
	}

	@Override
	public int insertRoot(RootDTO root) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void updateRootAssignmentStatus(RootDTO root, String status) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public Boolean setManualRoot(String value) throws SQLException {
		System.out.println("Fascade Business Logic");
		return manager3.setManualRoot(value);
	}

	@Override
	public List<TokenDTO> getTokenByVerseID(int verseID) {
		// TODO Auto-generated method stub
		return manager3.getTokenByVerseID(verseID);
	}

	@Override
	public void assignTokensRootsPOS(String verse1, String verse2, int poemID, int verseID) {
		manager3.assignTokensRootsPOS(verse1, verse2, poemID, verseID);
	}

	@Override
	public void importData(File selectedFile) {
		// TODO Auto-generated method stub
		manager5.importData(selectedFile);

	}
}
