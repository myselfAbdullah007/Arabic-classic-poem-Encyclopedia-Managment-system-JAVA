package dataacess;

import java.io.File;
import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.spi.LoggerAdapter;

import dtos.BookDTO;
import dtos.PoemDTO;
import dtos.VerseDTO;

public class FacadeDal implements FacadeDalInterface {
	private BookInterface bookDao;
	private PoemInterface poemDao;
	private VerseInterface verseDao;
	private RootInterface rootDao;
	private TokenInterface tokenDao;
	private FileImporterInterface importerDao;
	private static DatabaseConnector dbConnector;
	private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(LoggerAdapter.class.getName());

	public FacadeDal() {
		// Initialize the database connection
		dbConnector = DatabaseConnector.getInstance();
		logger.info("Connected to the database");
		System.out.println("Connected to the database");
		this.bookDao = new BookDAO(dbConnector);
		this.poemDao = new PoemDAO(dbConnector);
		this.verseDao = new VerseDAO(dbConnector);
		this.rootDao = new TokenDAO(dbConnector);
		this.tokenDao = new TokenDAO(dbConnector);
		this.importerDao = new FileImporter();
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

	@Override
	public int createPoem(int bookID, String poemTitle) throws Exception {
		return poemDao.createPoem(bookID, poemTitle);
	}

	@Override
	public List<PoemDTO> getAllPoems() throws SQLException {
		return poemDao.getAllPoems();
	}

	@Override
	public boolean updatePoem(int poemID, String newTitle) throws SQLException {
		return poemDao.updatePoem(poemID, newTitle);
	}

	@Override
	public PoemDTO getPoemByID(int poemID) {
		System.out.println("DAL Main Facad Class");
		return poemDao.getPoemByID(poemID);
	}

	@Override
	public int createVerse(int poemID, String verse1, String verse2) throws Exception {
		return verseDao.createVerse(poemID, verse1, verse2);
	}

	@Override
	public List<VerseDTO> getVersesForPoem(int poemID) {
		return verseDao.getVersesForPoem(poemID);
	}

	@Override
	public void saveRoot(String root) {
		rootDao.saveRoot(root);
	}

	@Override
	public void updateRoot(String oldValue, String newValue) throws SQLException {
		rootDao.updateRoot(oldValue, newValue);
	}

	@Override
	public void deleteRoot(String value) throws SQLException {
		rootDao.deleteRoot(value);
	}

	@Override
	public Object[][] transformDataForTable(List<PoemDTO> allPoems) {
		return poemDao.transformDataForTable(allPoems);
	}

	@Override
	public List<String> getAllRoots() {
		return rootDao.getAllRoots();
	}

	@Override
	public void createVerse(int poemID, String verse) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public List<PoemDTO> getPoemsForBook(int bookID) throws SQLException {
		// TODO Auto-generated method stub
		return poemDao.getPoemsForBook(bookID);
	}

	@Override
	public boolean deletePoem(int poemID) throws SQLException {
		// TODO Auto-generated method stub
		return poemDao.deletePoem(poemID);
	}

	@Override
	public void updateVerse(VerseDTO verse) {
		verseDao.updateVerse(verse);
	}

	@Override
	public void deleteVerse(VerseDTO verse) {
		verseDao.deleteVerse(verse);
	}

	@Override
	public List<VerseDTO> getVerseByRootID(String name) {
		// TODO Auto-generated method stub
		return rootDao.getVerseByRootID(name);
	}

	@Override
	public Boolean setManualRoot(String value) throws SQLException {

		return rootDao.setManualRoot(value);
	}

	@Override
	public List<dtos.TokenDTO> getTokenByVerseID(int verseID) {
		return rootDao.getTokenByVerseID(verseID);
	}

	@Override
	public void assignTokensRootsPOS(String verse1, String verse2, int poemID, int verseID) {
		tokenDao.assignTokensRootsPOS(verse1, verse2, poemID, verseID);
	}

	@Override
	public void importFile(File selectedFile) {
		// TODO Auto-generated method stub
		importerDao.importFile(selectedFile);
	}

}
