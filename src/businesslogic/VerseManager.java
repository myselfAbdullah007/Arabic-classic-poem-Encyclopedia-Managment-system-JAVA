package businesslogic;

import java.util.List;
import dataacess.FacadeDalInterface;
import dtos.VerseDTO;

public class VerseManager implements VerseManagerInterface {
	private FacadeDalInterface verseDAO;

	public VerseManager(FacadeDalInterface dal) {
		verseDAO = dal;
	}

	public int createVerse(int poemID, String misra,String misra2) throws Exception {
		return verseDAO.createVerse(poemID, misra,misra2);
	}

	public List<VerseDTO> getVersesByPoemID(int poemID) {
		return verseDAO.getVersesForPoem(poemID);
	}

	public void assignRootsToTokens(int verseId) {

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
