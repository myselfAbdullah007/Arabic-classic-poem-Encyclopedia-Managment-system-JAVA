package bll;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dal.TokenDAOStub;
import dtos.RootDTO;
import dtos.TokenDTO;
import dtos.VerseDTO;

class TokenManagerTestCases {

	private TokenManagerTest tokenManager;
    private TokenDAOStub tokenDAOStub;

	@BeforeEach
	void setUp() {
		// Initialize TokenManagerTest with the TokenDAOStub
		tokenDAOStub  = new TokenDAOStub();
		tokenManager = new TokenManagerTest(tokenDAOStub);
	}

	@Test
	void testAddRoot() {
		// Test adding a root
		tokenManager.addRoot("NewRoot");
		List<String> allRoots = tokenManager.getAllRoots();
		assertTrue(allRoots.contains("NewRoot"));
	}

	@Test
	void testUpdateRoot() throws SQLException {
		// Test updating a root
		tokenManager.addRoot("OldRoot");
		tokenManager.updateRoot("OldRoot", "UpdatedRoot");
		List<String> allRoots = tokenManager.getAllRoots();
		assertFalse(allRoots.contains("OldRoot"));
		assertTrue(allRoots.contains("UpdatedRoot"));
	}

	@Test
	void testDeleteRoot() throws SQLException {
		// Test deleting a root
		tokenManager.addRoot("RootToDelete");
		tokenManager.deleteRoot("RootToDelete");
		List<String> allRoots = tokenManager.getAllRoots();
		assertFalse(allRoots.contains("RootToDelete"));
	}

	@Test
	void testGetVerseFromRoot() throws SQLException {
		// Test getting verses from a root
		tokenManager.addRoot("TestRoot");
		List<VerseDTO> verses = tokenManager.getVerseFromRoot("TestRoot");
		assertNotNull(verses);
		assertEquals(0, verses.size()); // Assuming no verses are associated with the root
	}

	@Test
	void testGetRootsFromVerses() throws SQLException {
		// Test getting roots from verses
		int verseID = 1; // Assuming verseID 1 exists
		List<RootDTO> roots = tokenManager.getRootsFromVerses(verseID);
		assertNotNull(roots);
		assertEquals(1, roots.size()); // Assuming 1 roots are associated with the verse
	}

	@Test
	void testGetTokenByVerseID() {
		// Test getting tokens by verse ID
		int verseID = 1; // Assuming verseID 1 exists
		List<TokenDTO> tokens = tokenManager.getTokenByVerseID(verseID);
		assertNotNull(tokens);
		assertEquals(2, tokens.size()); // Assuming 2 tokens are associated with the verse
	}

	@Test
	void testAssignTokensRootsPOS() {
	    // Test assigning tokens, roots, and POS
	    tokenManager.assignTokensRootsPOS("Verse1", "Verse2", 1, 1);
	    
	    // Verify that tokens, roots, and POS are assigned in the stub
	    List<TokenDTO> tokens = tokenDAOStub.getInsertedTokens();
	    List<RootDTO> roots = tokenDAOStub.getInsertedRoots();
	    assertTrue(tokens.size() > 0);
	    assertTrue(roots.size() > 0);
	    assertTrue(tokenDAOStub.isAssignPOSMethodCalled());
	}

}
