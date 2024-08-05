package bll;

import dal.VerseDAOStub;
import dtos.VerseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class VerseManagerTestCases {

    private VerseManagerTest verseManager;

    @BeforeEach
    void setUp() throws Exception {
        VerseDAOStub verseDAOStub = new VerseDAOStub();
        verseManager = new VerseManagerTest(verseDAOStub);
    }

    @Test
    void createVerse() throws Exception {
        // Given
        int poemID = 1;
        String misra1 = "Verse_Misra1";
        String misra2 = "Verse_Misra2";

        // When
        int verseID = verseManager.createVerse(poemID, misra1, misra2);

        // Then
        assertEquals(verseID, 4, "VerseID should be 4 (auto-incremented)");
    }

    @Test
    void getVersesByPoemID() {
        // Given
        int poemID = 1;

        // When
        List<VerseDTO> verses = verseManager.getVersesByPoemID(poemID);

        // Then
        assertEquals(2, verses.size(), "There should be 2 verses for the poem");
    }

    @Test
    void updateVerse() {
        // Given
        VerseDTO verseToUpdate = new VerseDTO(1, 1, "Updated_Misra1", "Updated_Misra2");

        // When
        verseManager.updateVerse(verseToUpdate);

        // Then
        VerseDTO updatedVerse = verseManager.getVersesByPoemID(1).get(0);
        assertEquals("Updated_Misra1", updatedVerse.getVerse1(), "Verse content should be updated");
        assertEquals("Updated_Misra2", updatedVerse.getVerse2(), "Verse content should be updated");
    }

    @Test
    void deleteVerse() {
        // Given
        int initialSize = verseManager.getVersesByPoemID(1).size();
        VerseDTO verseToDelete = new VerseDTO(1,1, "Verse1_Misra1", "Verse1_Misra2");
        // When
        verseManager.deleteVerse(verseToDelete);

        // Then
        List<VerseDTO> verses = verseManager.getVersesByPoemID(1);
        int finalSize = verses.size();
        assertEquals(initialSize - 1, finalSize, "Verses should be one size shorter after deletion");
    }
}

