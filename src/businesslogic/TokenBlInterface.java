package businesslogic;

import java.sql.SQLException;
import java.util.List;

import dtos.RootDTO;
import dtos.TokenDTO;
import dtos.VerseDTO;

public interface TokenBlInterface {
	public void addRoot(String root);
    public void updateRoot(String oldValue, String newValue) throws SQLException;
    public void deleteRoot(String value) throws SQLException;
    public List<String> getAllRoots();
	public List<VerseDTO> getVerseFromRoot(String name) throws SQLException;
	public List<RootDTO> getRootsFromVerses(int verseID) throws SQLException;
	public Boolean setManualRoot(String value) throws SQLException;
	public List<TokenDTO> getTokenByVerseID(int verseID);
	public  void assignTokensRootsPOS(String verse1, String verse2, int poemID,int verseID);
}
