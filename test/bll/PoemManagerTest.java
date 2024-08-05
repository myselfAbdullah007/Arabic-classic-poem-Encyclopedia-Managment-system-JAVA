package bll;

import java.sql.SQLException;
import java.util.List;

import businesslogic.PoemManagerInterface;
import dataacess.PoemInterface;
import dtos.PoemDTO;

public class PoemManagerTest  implements PoemManagerInterface{
	private PoemInterface dal;

	public PoemManagerTest(PoemInterface dal) {
		this.dal = dal;

	}
	@Override
	public int createPoem(int bookID, String poemTitle) throws Exception {
		return dal.createPoem(bookID, poemTitle);
	}
	@Override
	public List<PoemDTO> getAllPoems() throws SQLException {
		return dal.getAllPoems();
	}
	@Override
	public boolean updatePoem(int poemID, String newTitle) throws SQLException {
		return dal.updatePoem(poemID, newTitle);
	}
	@Override
	public PoemDTO getPoemByID(int poemID) {
		return dal.getPoemByID(poemID);
	}

	@Override
	public Object[][] transformDataForTable(List<PoemDTO> allPoems) {
		return dal.transformDataForTable(allPoems);
	}

	@Override
	public List<PoemDTO> getPoemsForBook(int bookID) throws SQLException {
		// TODO Auto-generated method stub
		return dal.getPoemsForBook(bookID);
	}

	@Override
	public boolean deletePoem(int poemID) throws SQLException {
		// TODO Auto-generated method stub
		return dal.deletePoem(poemID);
	}

}
