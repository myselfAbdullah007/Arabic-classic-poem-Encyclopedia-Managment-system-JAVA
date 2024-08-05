package dataacess;

import java.util.List;

import dtos.VerseDTO;

public interface VerseInterface {
	public int createVerse(int poemID, String verse1, String verse2) throws Exception;
	public List<VerseDTO> getVersesForPoem(int poemID);
	public void updateVerse(VerseDTO verse);
	public void deleteVerse(VerseDTO verseID);
}
