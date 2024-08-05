package dtos;

public class RootDTO {
	private int verseID;
    private String value;
    private String assignmentStatus;

    public RootDTO(int verseID, String value, String assignmentStatus) {
    	this.verseID = verseID;
        this.value = value;
        this.assignmentStatus = assignmentStatus;
    }

    public int getVerseID() {
		return verseID;
	}

	public void setVerseID(int verseID) {
		this.verseID = verseID;
	}

	public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getAssignmentStatus() {
        return assignmentStatus;
    }

    public void setAssignmentStatus(String assignmentStatus) {
        this.assignmentStatus = assignmentStatus;
    }

    public RootDTO() {
    }
}
