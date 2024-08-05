package businesslogic;

import java.sql.SQLException;
import java.util.List;

import dtos.PoemDTO;

public interface PoemManagerInterface {
	public int createPoem(int bookID, String poemTitle) throws Exception;

	public List<PoemDTO> getAllPoems() throws SQLException;

	public boolean updatePoem(int poemID, String newTitle) throws SQLException;

	public PoemDTO getPoemByID(int poemID);

	public Object[][] transformDataForTable(List<PoemDTO> allPoems);
	
	public List<PoemDTO> getPoemsForBook(int bookID) throws SQLException;
	
	public boolean deletePoem(int poemID) throws SQLException;
}
