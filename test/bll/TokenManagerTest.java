package bll;
import java.sql.SQLException;
import java.util.List;

import businesslogic.TokenBlInterface;
import dal.TokenDAOStub;
import dtos.RootDTO;
import dtos.TokenDTO;
import dtos.VerseDTO;

//ALI UMAIS

public class TokenManagerTest implements TokenBlInterface {
	private TokenDAOStub rootDao;

	public TokenManagerTest(TokenDAOStub dal) {
		this.rootDao = dal;
	}

	public void addRoot(String root) {
		rootDao.saveRoot(root);
	}

	public void updateRoot(String oldValue, String newValue) throws SQLException {
		rootDao.updateRoot(oldValue, newValue);
	}

	public void deleteRoot(String value) throws SQLException {
		rootDao.deleteRoot(value);
	}

	public List<String> getAllRoots() {
		return rootDao.getAllRoots();
	}

	public List<VerseDTO> getVerseFromRoot(String name) throws SQLException {
		return rootDao.getVerseByRootID(name);
	}

	@Override
	public List<RootDTO> getRootsFromVerses(int verseID) throws SQLException {
		return rootDao.getRootByVerseID(verseID);
		
	}

	public Boolean setManualRoot(String value) throws SQLException {
		return rootDao.setManualRoot(value);
	}

	@Override
	public List<TokenDTO> getTokenByVerseID(int verseID) {
		return rootDao.getTokenByVerseID(verseID);
	}

	@Override
	public void assignTokensRootsPOS(String verse1, String verse2, int poemID, int verseID) {
		rootDao.assignTokensRootsPOS(verse1, verse2, poemID, verseID);
	}
}

