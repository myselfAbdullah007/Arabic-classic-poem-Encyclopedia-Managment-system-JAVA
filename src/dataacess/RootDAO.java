package dataacess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import businesslogic.RootManagerInterface;
import dtos.RootDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RootDAO implements RootManagerInterface {
	DatabaseConnector connector;
	private static final Logger logger = LogManager.getLogger(RootDAO.class.getName());

	public RootDAO(DatabaseConnector connector) {
		this.connector = connector;
	}

	@Override
	public int insertRoot(RootDTO root) throws SQLException {
		String sql = "INSERT INTO roots (tokenID, value, assignmentStatus) VALUES (?, ?, ?)";

		PreparedStatement pstmt = connector.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

		pstmt.setInt(1, root.getVerseID());
		pstmt.setString(2, root.getValue());
		pstmt.setString(3, root.getAssignmentStatus());

		int affectedRows = pstmt.executeUpdate();

		if (affectedRows > 0) {
			logger.info("Root inserted");
			try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					return generatedKeys.getInt(1); // Return the generated root ID
				}
			}
		}
		logger.warn("Failed to insert root");
		return -1;

	}

	@Override
	public void updateRootAssignmentStatus(RootDTO root, String status) throws SQLException {
		String sql = "UPDATE roots SET assignmentStatus = ? WHERE value = ?";

		PreparedStatement pstmt = connector.prepareStatement(sql);

		pstmt.setString(1, status);
		pstmt.setString(2, root.getValue());

		pstmt.executeUpdate();
		logger.info("Root assignment status updated");

	}

}