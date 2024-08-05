package businesslogic;

import java.sql.SQLException;

import dtos.RootDTO;

public interface RootManagerInterface {
	public int insertRoot(RootDTO root) throws SQLException;
	public void updateRootAssignmentStatus(RootDTO root, String status) throws SQLException;
	
}
