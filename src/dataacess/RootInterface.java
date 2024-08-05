package dataacess;

import java.sql.SQLException;
import java.util.List;

import dtos.TokenDTO;
import dtos.VerseDTO;

public interface RootInterface {
    public void saveRoot(String root);
    public void updateRoot(String oldValue, String newValue) throws SQLException;
    public void deleteRoot(String value) throws SQLException;
    public List<String> getAllRoots();
	public List<VerseDTO> getVerseByRootID(String name);
	public Boolean setManualRoot(String value) throws SQLException;
	public List<TokenDTO> getTokenByVerseID(int verseID);
    
}
