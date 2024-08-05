package dtos;

public class TokenDTO {
	private int tokenID;
    private int verseID;
    private String token;
	private int rootID;
	private String tag;

	public TokenDTO(int tokenID, int verseID, String token, int rootID, String tag) {
		this.tokenID = tokenID;
		this.verseID = verseID;
		this.token = token;
		this.rootID = rootID;
		this.tag = tag;
	}

	public int getTokenID() {
		return tokenID;
	}

	public void setTokenID(int tokenID) {
		this.tokenID = tokenID;
	}

	public int getVerseID() {
		return verseID;
	}

	public void setVerseID(int verseID) {
		this.verseID = verseID;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public int getRootID() {
		return rootID;
	}

	public void setRootID(int rootID) {
		this.rootID = rootID;
	}



	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}
}
