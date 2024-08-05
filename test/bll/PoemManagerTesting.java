package bll;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import businesslogic.PoemManagerInterface;
import dal.PoemDAOStub;
import dataacess.PoemInterface;
import dtos.PoemDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

class PoemManagerTesting {

    private PoemManagerInterface poemManager;

    @BeforeEach
    void setUp() throws Exception {
        // Initialize the PoemManager with the PoemDaoStub
        PoemInterface stubObj = new PoemDAOStub();
        poemManager = new PoemManagerTest(stubObj);
    }

    @Test
    void createPoem() throws Exception {
        // Given
        int bookID = 1;
        String poemTitle = "New Poem";

        // When
        int poemID = poemManager.createPoem(bookID, poemTitle);

        // Then
        assertTrue(poemID > 0, "PoemID should be greater than 0");
    }

    @Test
    void getAllPoems() throws SQLException {
        // When
        List<PoemDTO> allPoems = poemManager.getAllPoems();

        // Then
        assertEquals(3, allPoems.size(), "There should be 3 poems in the list");
    }

    @Test
    void updatePoem() throws SQLException {
        // Given
        int poemID = 1;
        String newTitle = "Updated Poem Title";

        // When
        boolean result = poemManager.updatePoem(poemID, newTitle);

        // Then
        assertTrue(result, "Update should be successful");
    }

    @Test
    void getPoemByID() {
        // Given
        int poemID = 1;

        // When
        PoemDTO poem = poemManager.getPoemByID(poemID);

        // Then
        assertEquals(poemID, poem.getPoemID(), "PoemID should match");
    }

    @Test
    void transformDataForTable() {
        // Given
        List<PoemDTO> allPoems = Arrays.asList( new PoemDTO(66, 66, "Poem1 abc"));

        // When
        Object[][] data = poemManager.transformDataForTable(allPoems);

        // Then
        assertEquals(2, data.length, "There should be 2 rows of data");
    }

    @Test
    void getPoemsForBook() throws SQLException {
        // Given
        int bookID = 1;

        // When
        List<PoemDTO> poemsForBook = poemManager.getPoemsForBook(bookID);

        // Then
        assertEquals(2, poemsForBook.size(), "There should be 2 poems for the book");
    }

    @Test
    void deletePoem() throws SQLException {
        // Given
        int poemID = 1;

        // When
        boolean result = poemManager.deletePoem(poemID);

        // Then
        assertTrue(result, "Deletion should be successful");
        assertNull(poemManager.getPoemByID(poemID), "Poem should not exist after deletion");
    }
}
