package dataacess;

import java.sql.SQLException;
import java.util.List;

import dtos.VerseDTO;

public interface TokenInterface {
	public  void assignTokensRootsPOS(String verse1, String verse2, int poemID,int verseID);
	public int insertToken(String token, int verseID) throws SQLException;
	public void assignPOS(String pos, int tokenID) throws SQLException;
	public List<VerseDTO> getVerseByRootID(String name) throws SQLException;
	public Boolean setManualRoot(String value) throws SQLException;
}
