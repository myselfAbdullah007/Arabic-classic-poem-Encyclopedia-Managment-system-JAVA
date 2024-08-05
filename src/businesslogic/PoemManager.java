package businesslogic;
import java.sql.SQLException;
import java.util.List;
import dataacess.FacadeDalInterface;
import dtos.PoemDTO;

public class PoemManager implements PoemManagerInterface {

	private FacadeDalInterface facade;

	public PoemManager(FacadeDalInterface dal) {
		this.facade = dal;

	}
	@Override
	public int createPoem(int bookID, String poemTitle) throws Exception {
		return facade.createPoem(bookID, poemTitle);
	}
	@Override
	public List<PoemDTO> getAllPoems() throws SQLException {
		return facade.getAllPoems();
	}
	@Override
	public boolean updatePoem(int poemID, String newTitle) throws SQLException {
		return facade.updatePoem(poemID, newTitle);
	}
	@Override
	public PoemDTO getPoemByID(int poemID) {
		return facade.getPoemByID(poemID);
	}

	@Override
	public Object[][] transformDataForTable(List<PoemDTO> allPoems) {
		// TODO Auto-generated method stub
		return facade.transformDataForTable(allPoems);
	}

	@Override
	public List<PoemDTO> getPoemsForBook(int bookID) throws SQLException {
		// TODO Auto-generated method stub
		return facade.getPoemsForBook(bookID);
	}

	@Override
	public boolean deletePoem(int poemID) throws SQLException {
		// TODO Auto-generated method stub
		return facade.deletePoem(poemID);
	}

	
}
