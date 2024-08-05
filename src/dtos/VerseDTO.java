package dtos;

import java.util.List;

public class VerseDTO {
    private int poemID;
    private String verse1;
    private String verse2;
    private int verseID;

    private List<String> tokens;

    public VerseDTO(int poemID, int verses, String verseContent, String verseContent2) {
        this.poemID = poemID;
        this.verse1 = verseContent;
        this.verse2 = verseContent2;
        this.verseID = verses;
    }

    public int getPoemID() {
        return poemID;
    }

    public void setPoemID(int poemID) {
        this.poemID = poemID;
    }

    public String getVerse1() {
        return verse1;
    }

    public void setVerse1(String verse1) {
        this.verse1 = verse1;
    }

    public String getVerse2() {
        return verse2;
    }

    public void setVerse2(String verse2) {
        this.verse2 = verse2;
    }

    public int getVerseID() {
        return verseID;
    }

    public void setVerseID(int verseID) {
        this.verseID = verseID;
    }

    public List<String> getTokens() { return tokens; }

    public void setTokens(List<String> tokens) { this.tokens = tokens; }

    @Override
    public String toString() {
        return verse1 + "       " + verse2;
    }
}
