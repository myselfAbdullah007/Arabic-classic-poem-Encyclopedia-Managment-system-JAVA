package dal;

import dtos.RootDTO;
import dtos.TokenDTO;
import dtos.VerseDTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dataacess.RootInterface;
import dataacess.TokenInterface;

public class TokenDAOStub implements TokenInterface , RootInterface{

    private List<TokenDTO> insertedTokens;
    private List<RootDTO> insertedRoots;
    private boolean isAssignPOSMethodCalled;

    public TokenDAOStub() {
        this.insertedTokens = new ArrayList<>();
        this.insertedRoots = new ArrayList<>();
        this.isAssignPOSMethodCalled = false;
        initializeDummyData();
    }

    private void initializeDummyData() {
        // Simulate initial data for testing
        TokenDTO token1 = new TokenDTO(1, 1, "Token1", 0, null);
        TokenDTO token2 = new TokenDTO(2, 1, "Token2", 0, null);
        RootDTO root1 = new RootDTO(1, "Root1", "automatic");

        insertedTokens.add(token1);
        insertedTokens.add(token2);
        insertedRoots.add(root1);
    }

    public List<TokenDTO> getInsertedTokens() {
        return insertedTokens;
    }

    public List<RootDTO> getInsertedRoots() {
        return insertedRoots;
    }

    public boolean isAssignPOSMethodCalled() {
        return isAssignPOSMethodCalled;
    }

    @Override
    public int insertToken(String token, int verseID) throws SQLException {
        // Simulate database insertion
        int tokenID = generateTokenID();
        TokenDTO tokenDTO = new TokenDTO(tokenID, verseID, token, 0, null);
        insertedTokens.add(tokenDTO);
        return tokenID;
    }

    @Override
    public void assignPOS(String pos, int tokenID) throws SQLException {
        // Simulate assigning POS
        isAssignPOSMethodCalled = true;
    }

    @Override
    public void assignTokensRootsPOS(String verse1, String verse2, int poemID, int verseID) {
        // Simulate the behavior of assigning tokens, roots, and POS
        processAndInsertTokensRootsPOS(verse1, verseID);
        processAndInsertTokensRootsPOS(verse2, verseID);
        isAssignPOSMethodCalled = true;

    }

    @Override
    public List<VerseDTO> getVerseByRootID(String name) {
        // Simulate fetching verse data based on root ID
        List<VerseDTO> verseDTOs = new ArrayList<>();
        // Dummy implementation - replace with actual logic
        return verseDTOs;
    }
    @Override
    public Boolean setManualRoot(String value) throws SQLException {
        // Simulate setting a root as manual
        // Dummy implementation - replace with actual logic

        for (RootDTO rootDTO : insertedRoots) {
            if (rootDTO.getValue().equals(value)) {
                rootDTO.setAssignmentStatus("Manual");
                return true; // Return true to simulate success
            }
        }

        return false; // Return false if root with the specified value is not found
    }

    private int generateTokenID() {
        // Simulate generating a unique token ID
        return insertedTokens.size() + 1;
    }

    private void processAndInsertTokensRootsPOS(String verseContent, int verseID) {
        // Simulate the behavior of processing and inserting tokens, roots, and POS
        List<String> tokens = new ArrayList<>(); // Simulate tokenization
        for (String token : tokens) {
            int tokenID = generateTokenID();
            TokenDTO tokenDTO = new TokenDTO(tokenID, verseID, token, 0, null);
            insertedTokens.add(tokenDTO);

            // Simulate inserting roots
            String rootValue = "Root_" + tokenID;
            RootDTO rootDTO = new RootDTO(tokenID, rootValue, "automatic");
            insertedRoots.add(rootDTO);

            // Simulate assigning POS
            isAssignPOSMethodCalled = true;
        }
    }

    @Override
    public void saveRoot(String root) {
        // Simulate saving root to the list
        RootDTO rootDTO = new RootDTO(0, root, "automatic");
        insertedRoots.add(rootDTO);
    }

    @Override
    public void updateRoot(String oldValue, String newValue) throws SQLException {
        // Simulate updating root in the list
        for (RootDTO rootDTO : insertedRoots) {
            if (rootDTO.getValue().equals(oldValue)) {
                rootDTO.setValue(newValue);
                break;
            }
        }
    }

    @Override
    public void deleteRoot(String value) throws SQLException {
        // Simulate deleting root from the list
        insertedRoots.removeIf(rootDTO -> rootDTO.getValue().equals(value));
    }

    @Override
    public List<String> getAllRoots() {
        // Simulate fetching all roots from the list
        List<String> roots = new ArrayList<>();
        for (RootDTO rootDTO : insertedRoots) {
            roots.add(rootDTO.getValue());
        }
        return roots;
    }

    @Override
    public List<TokenDTO> getTokenByVerseID(int verseID) {
        // Simulate fetching tokens by verse ID from the list
        List<TokenDTO> tokens = new ArrayList<>();
        for (TokenDTO tokenDTO : insertedTokens) {
            if (tokenDTO.getVerseID() == verseID) {
                tokens.add(tokenDTO);
            }
        }
        return tokens;
    }

    public List<RootDTO> getRootByVerseID(int verseID) throws SQLException {
      
        List<RootDTO> roots = new ArrayList<>();

        for (RootDTO rootDTO : insertedRoots) {
            if (rootDTO.getVerseID() == verseID) {
                roots.add(rootDTO);
            }
        }

        return roots;
    }


}
