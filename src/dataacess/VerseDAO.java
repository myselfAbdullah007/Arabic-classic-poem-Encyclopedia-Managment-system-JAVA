package dataacess;


import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dtos.VerseDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class VerseDAO implements VerseInterface{
	DatabaseConnector connection;
	private static final Logger logger = LogManager.getLogger(VerseDAO.class.getName());

	public VerseDAO(DatabaseConnector dbConnector) {
		connection = dbConnector;

	}
	
	public int createVerse(int poemID, String verse1, String verse2) throws Exception {
		String sql = "INSERT INTO verses (poemID, Verse1, Verse2) VALUES (?, ?, ?)";
		int verseID = -1;  // Initialize verseID to -1 to indicate an issue if not updated

		try (
				PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			stmt.setInt(1, poemID);
			stmt.setString(2, verse1);
			stmt.setString(3, verse2);

			int affectedRows = stmt.executeUpdate();
			if (affectedRows > 0) {
				// Retrieve the generated keys
				try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
					if (generatedKeys.next()) {
						verseID = generatedKeys.getInt(1);
                        logger.info("Verse Inserted with verseID: {}", verseID);
					} else {
                        logger.error("Failed to retrieve verseID.");
					}
				}
			} else {
                logger.error("Failed to insert verse.");
			}
		}

		return verseID;
	}

	public Integer getVerseID(Integer poemID, String verseContent) {
		try {
			String sql = "SELECT verseID FROM verses WHERE poemID = ? AND Verse1 = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, poemID);
			preparedStatement.setString(2, verseContent);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				return resultSet.getInt("verseID");
			}
		} catch (Exception e) {
            logger.error("Error getting verse ID: {}", e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	public List<VerseDTO> getVersesForPoem(int poemID) {
		List<VerseDTO> verses = new ArrayList<>();
		String query = "SELECT verseID, Verse1, Verse2 FROM verses WHERE poemID = ?";

		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, poemID);
			ResultSet rs = statement.executeQuery();

			while (rs.next()) {
				int verseID = rs.getInt("verseID");
				String verseContent = rs.getString("Verse1");
				String verseContent2 = rs.getString("Verse2");
				verses.add(new VerseDTO(poemID, verseID, verseContent,verseContent2));
			}
		} catch (Exception e) {
            logger.error("Error getting verses for poem: {}", e.getMessage());
			e.printStackTrace();
		}

		return verses;
	}


	public void updateVerse(VerseDTO verse) {
		try {
			String sql = "UPDATE verses SET Verse1 = ?, Verse2 = ? WHERE VerseID = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, verse.getVerse1());
			statement.setString(2, verse.getVerse2());
			statement.setInt(3, verse.getVerseID());
			statement.executeUpdate();
            logger.info("Verse updated - VerseID: {}", verse.getVerseID());

		} catch (SQLException e) {
            logger.error("Error updating verse: {}", e.getMessage());
			e.printStackTrace();
		}
	}

	public void deleteVerse(VerseDTO verse) {
		try {
			String sql = "DELETE FROM verses WHERE VerseID = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, verse.getVerseID());
			statement.executeUpdate();
            logger.info("Verse deleted - VerseID: {}", verse.getVerseID());
		} catch (SQLException e) {
            logger.error("Error deleting verse: {}", e.getMessage());
			e.printStackTrace();
		}
	}
}
