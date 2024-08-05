package dal;
import dataacess.VerseInterface;
import dtos.VerseDTO;

import java.util.ArrayList;
import java.util.List;

public class VerseDAOStub implements VerseInterface {

    private final List<VerseDTO> verses;

    public VerseDAOStub() throws Exception {
        this.verses = new ArrayList<>();
        initializeDummyData();
    }

    private void initializeDummyData() throws Exception {
        createVerse(1, "Verse1_Misra1", "Verse1_Misra2");
        createVerse(1, "Verse2_Misra1", "Verse2_Misra2");
        createVerse(2, "Verse3_Misra1", "Verse3_Misra2");
    }

    @Override
    public int createVerse(int poemID, String verse1, String verse2) throws Exception {
        int verseID = verses.size() + 1; // Simulating auto-incremented ID
        VerseDTO verseDTO = new VerseDTO(poemID, verseID, verse1, verse2);
        verses.add(verseDTO);
        return verseID;
    }

    @Override
    public List<VerseDTO> getVersesForPoem(int poemID) {
        List<VerseDTO> versesForPoem = new ArrayList<>();
        for (VerseDTO verse : verses) {
            if (verse.getPoemID() == poemID) {
                versesForPoem.add(verse);
            }
        }
        return versesForPoem;
    }

   // @Override
    public Integer getVerseID(Integer poemID, String verseContent) {
        for (VerseDTO verse : verses) {
            if (verse.getPoemID() == poemID && (verse.getVerse1().equals(verseContent) || verse.getVerse2().equals(verseContent))) {
                return verse.getVerseID();
            }
        }
        return null;
    }

    @Override
    public void updateVerse(VerseDTO verse) {
        for (VerseDTO existingVerse : verses) {
            if (existingVerse.getVerseID() == verse.getVerseID()) {
                existingVerse.setVerse1(verse.getVerse1());
                existingVerse.setVerse2(verse.getVerse2());
                break;
            }
        }
    }

    @Override
    public void deleteVerse(VerseDTO verse) {
        verses.removeIf(existingVerse -> existingVerse.getVerseID() == verse.getVerseID());
    }
}
