package dal;

import dataacess.PoemInterface;
import dtos.PoemDTO;
import dtos.VerseDTO;

import java.util.ArrayList;
import java.util.List;

public class PoemDAOStub implements PoemInterface {

    private final List<PoemDTO> poems;

    public PoemDAOStub() throws Exception {
        this.poems = new ArrayList<>();
        initializeDummyData();
    }

    private void initializeDummyData() throws Exception {
        createPoem(1, "Poem1");
        createPoem(1, "Poem2");
        createPoem(3, "Poem3");
    }

    @Override
    public int createPoem(int bookID, String poemTitle) throws Exception {
        int poemID = poems.size() + 1; // Simulating auto-incremented ID
        PoemDTO poemDTO = new PoemDTO(bookID, poemID, poemTitle);
        poems.add(poemDTO);
        return poemID;
    }

    @Override
    public List<PoemDTO> getAllPoems() {
        return new ArrayList<>(poems);
    }

    @Override
    public boolean updatePoem(int poemID, String newTitle) {
        for (PoemDTO poem : poems) {
            if (poem.getPoemID() == poemID) {
                poem.setPoemTitle(newTitle);
                return true;
            }
        }
        return false;
    }

    @Override
    public PoemDTO getPoemByID(int poemID) {
        for (PoemDTO poem : poems) {
            if (poem.getPoemID() == poemID) {
                return poem;
            }
        }
        return null;
    }

    @Override
    public List<PoemDTO> getPoemsForBook(int bookID) {
        List<PoemDTO> poemsForBook = new ArrayList<>();
        for (PoemDTO poem : poems) {
            if (poem.getBookID() == bookID) {
                poemsForBook.add(poem);
            }
        }
        return poemsForBook;
    }

    @Override
    public boolean deletePoem(int poemID) {
        PoemDTO poemToRemove = null;
        for (PoemDTO poem : poems) {
            if (poem.getPoemID() == poemID) {
                poemToRemove = poem;
                break;
            }
        }
        if (poemToRemove != null) {
            poems.remove(poemToRemove);
            return true;
        }
        return false;
    }

    public Object[][] transformDataForTable(List<PoemDTO> allPoems) {
        List<Object[]> dataList = new ArrayList<>();

        for (PoemDTO poem : allPoems) {
            List<VerseDTO> verses = getVersesForPoem(poem.getPoemID());

            if (verses != null && !verses.isEmpty()) {
                for (VerseDTO verse : verses) {
                    dataList.add(new Object[]{poem.getPoemTitle(), verse.getVerse1(), verse.getVerse2()});
                }
            }
        }

        return dataList.toArray(new Object[0][]);
    }
    public List<VerseDTO> getVersesForPoem(int poemID) {
        List<VerseDTO> verses = new ArrayList<>();

        // Simulating dummy data for verses
        verses.add(new VerseDTO(poemID, 1, "Verse1_Content", "Verse1_Content2"));
        verses.add(new VerseDTO(poemID, 2, "Verse2_Content", "Verse2_Content2"));

        return verses;
    }

}

