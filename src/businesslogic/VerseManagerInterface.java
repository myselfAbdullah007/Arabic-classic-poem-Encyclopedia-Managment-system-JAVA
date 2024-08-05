package businesslogic;

import java.util.List;

import dtos.VerseDTO;

public interface VerseManagerInterface {
    public int createVerse(int poemID, String misra,String Misra2) throws Exception;
	public List<VerseDTO> getVersesByPoemID(int poemID);
	public void updateVerse(VerseDTO verse);
	public void deleteVerse(VerseDTO selectedVerse);
}
