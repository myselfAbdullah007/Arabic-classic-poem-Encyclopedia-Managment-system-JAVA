package dtos;

public class BookDTO {
    private int bookID;
    private String bookTitle;

    public BookDTO(int bookID, String bookTitle) {
        this.bookID = bookID;
        this.bookTitle = bookTitle;
    }

    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    @Override
    public String toString() {
        return bookTitle;
    }
}
