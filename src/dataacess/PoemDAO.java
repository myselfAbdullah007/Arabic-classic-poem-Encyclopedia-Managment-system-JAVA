package dataacess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dtos.PoemDTO;
import dtos.VerseDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PoemDAO implements PoemInterface {
	DatabaseConnector connection;
    private static final Logger logger = LogManager.getLogger(PoemDAO.class.getName());


	public PoemDAO(DatabaseConnector dbConnector) {
		connection = dbConnector;

	}

	@Override
	public int createPoem(int bookID, String poemTitle) throws Exception {
		String sql = "INSERT INTO poems (BookID, PoemTitle) VALUES (?, ?)";
		try (PreparedStatement stmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
			stmt.setInt(1, bookID);
			stmt.setString(2, poemTitle);
			stmt.executeUpdate();

			ResultSet rs = stmt.getGeneratedKeys();
			if (rs.next()) {
                logger.info("Poem created: {}", poemTitle);
				return rs.getInt(1);
			} else {
	            logger.error("Error creating poem");
				throw new Exception("Failed to retrieve poemID");
			}
		}
	}

	public List<PoemDTO> getAllPoems() throws SQLException {
		  List<PoemDTO> poems = new ArrayList<>();
	        String query = "SELECT * FROM poems";
	        try (PreparedStatement stmt = connection.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
	            while (rs.next()) {
	                poems.add(new PoemDTO(rs.getInt("BookID"), rs.getInt("poemID"), rs.getString("PoemTitle")));
	            }
	            logger.info("Fetched all poems");
	        } catch (SQLException e) {
	            logger.error("Error fetching all poems: {}", e.getMessage());
	            throw e;
	        }
	        return poems;
	}

	public boolean updatePoem(int poemID, String newTitle) throws SQLException {
		String updateSQL = "UPDATE poems SET PoemTitle = ? WHERE poemID = ?";
		try (PreparedStatement statement = connection.prepareStatement(updateSQL)) {
			statement.setString(1, newTitle);
			statement.setInt(2, poemID);
            logger.info("Poem updated - ID: {}, New Title: {}", poemID, newTitle);
			return statement.executeUpdate() > 0;
			
		}
	}

	public PoemDTO getPoemByID(int poemID) {
		PoemDTO poem = null;
		System.out.println("Poem id from Dao= " + poemID);
		String query = "SELECT bookID, poemTitle FROM poems WHERE poemID = ?";

		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, poemID);
			ResultSet rs = statement.executeQuery();

			if (rs.next()) {
				int bookID = rs.getInt("bookID");
				String poemTitle = rs.getString("poemTitle");
				poem = new PoemDTO(bookID, poemID, poemTitle);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return poem;
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
				verses.add(new VerseDTO(poemID, verseID, verseContent, verseContent2));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return verses;
	}

	public Object[][] transformDataForTable(List<PoemDTO> allPoems) {
		List<Object[]> dataList = new ArrayList<>();

		for (PoemDTO poem : allPoems) {
			List<VerseDTO> verses = getVersesForPoem(poem.getPoemID());

			if (verses != null && !verses.isEmpty()) {
				for (VerseDTO verse : verses) {
					dataList.add(new Object[] { poem.getPoemTitle(), verse.getVerse1(), verse.getVerse2() });
				}
			}
		}

		return dataList.toArray(new Object[0][]);
	}

	public List<PoemDTO> getPoemsForBook(int bookID) throws SQLException {
		List<PoemDTO> poems = new ArrayList<>();
		String sql = "SELECT * FROM poems WHERE BookID = ?";
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setInt(1, bookID);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				poems.add(new PoemDTO(bookID, rs.getInt("PoemID"), rs.getString("PoemTitle")));
			}
            logger.info("Fetched poems for BookID: {}", bookID);

		}
		return poems;
	}

	public boolean deletePoem(int poemID) throws SQLException {
		String deleteVersesSql = "DELETE FROM verses WHERE PoemID = ?";
		String deletePoemSql = "DELETE FROM poems WHERE PoemID = ?";

		try (PreparedStatement deleteVersesStmt = connection.prepareStatement(deleteVersesSql);
				PreparedStatement deletePoemStmt = connection.prepareStatement(deletePoemSql)) {

			// Delete verses associated with the poem
			deleteVersesStmt.setInt(1, poemID);
			deleteVersesStmt.executeUpdate();

			// Delete the poem
			deletePoemStmt.setInt(1, poemID);
			int rowsDeleted = deletePoemStmt.executeUpdate();
            logger.info("Poem deleted - ID: {}", poemID);
			return rowsDeleted > 0;
		}
	}

}
