package dtos;

public class PoemDTO {
    private int bookID;
    private int poemID;
    private String poemTitle;

    public PoemDTO(int bookID, int poemID, String poemTitle) {
        this.bookID = bookID;
        this.poemID = poemID;
        this.poemTitle = poemTitle;
    }

	public int getBookID() {
		return bookID;
	}

	public void setBookID(int bookID) {
		this.bookID = bookID;
	}

	public int getPoemID() {
		return poemID;
	}

	public void setPoemID(int poemID) {
		this.poemID = poemID;
	}

	public String getPoemTitle() {
		return poemTitle;
	}

	public void setPoemTitle(String poemTitle) {
		this.poemTitle = poemTitle;
	}

}
