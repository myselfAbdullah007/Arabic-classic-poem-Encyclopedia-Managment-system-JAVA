package bll;


import java.util.List;

import businesslogic.VerseManagerInterface;
import dal.VerseDAOStub;
import dtos.VerseDTO;

public class VerseManagerTest implements VerseManagerInterface {
	private VerseDAOStub verseDAO;

	public VerseManagerTest(VerseDAOStub verseDAOStub) {
		verseDAO = verseDAOStub;
	}
	@Override
	public int createVerse(int poemID, String misra,String misra2) throws Exception {
		return verseDAO.createVerse(poemID, misra,misra2);
	}
	
	@Override
	public List<VerseDTO> getVersesByPoemID(int poemID) {
		return verseDAO.getVersesForPoem(poemID);
	}
	

	@Override
	public void updateVerse(VerseDTO verse) {
		verseDAO.updateVerse(verse);
	}

	@Override
	public void deleteVerse(VerseDTO verse) {
		verseDAO.deleteVerse(verse);
	}
}
