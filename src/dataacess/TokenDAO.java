package dataacess;

import java.sql.PreparedStatement;
import utils.POS_Tagger;
import utils.Tokenizer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import dtos.RootDTO;
import dtos.TokenDTO;
import dtos.VerseDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
public class TokenDAO implements RootInterface, TokenInterface {
	static DatabaseConnector connection;
	VerseDAO verseDao;
	private static POS_Tagger tagger;
	private static Tokenizer tokenizer;
    private RootDAO rootDAO;
    private static final Logger logger = LogManager.getLogger(TokenDAO.class.getName());

	public TokenDAO(DatabaseConnector dbConnector) {
		connection = dbConnector;
		rootDAO = new RootDAO(dbConnector);
		tagger = new POS_Tagger();
		tokenizer= new Tokenizer();

	}
	@Override
	public void saveRoot(String root) {
		try {
			String sql = "INSERT INTO roots (value) VALUES (?)";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, root);
			preparedStatement.executeUpdate();
            logger.info("Root saved: {}", root);
		} catch (Exception e) {
            logger.error("Error saving root: {}", e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public void assignPOS(String pos, int tokenID) throws SQLException {
		String sql = "UPDATE tokens SET tag = ? WHERE tokenID = ?";
		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setString(1, pos);
			preparedStatement.setInt(2, tokenID);
			int rowsUpdated = preparedStatement.executeUpdate();
			if (rowsUpdated > 0) {
                logger.info("POS assigned to TokenID: {}", tokenID);
				//System.out.println("POS assigned to TokenID: " + tokenID);
			} else {
                logger.warn("Failed to assign POS. TokenID not found: {}", tokenID);
			//	System.err.println("Failed to assign POS. TokenID not found.");
			}
		} catch (SQLException e) {
            logger.error("Error assigning POS: {}", e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public int insertToken(String token, int verseID) throws SQLException {
		String sql = "INSERT INTO tokens(verseId, token) VALUES (?,?)";
		int tokenID = -1; // Initialize tokenID to -1 to indicate an issue if not updated

		try (PreparedStatement pdstm = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
			pdstm.setInt(1, verseID);
			pdstm.setString(2, token);

			int affectedRows = pdstm.executeUpdate();
			if (affectedRows > 0) {
				// Retrieve the generated keys
				try (ResultSet generatedKeys = pdstm.getGeneratedKeys()) {
					if (generatedKeys.next()) {
						tokenID = generatedKeys.getInt(1);
                        logger.info("Token inserted - ID: {}, Value: {}, VerseID: {}", tokenID, token, verseID);
					//	System.out.println("Token Inserted with tokenID: " + tokenID);
					} else {
                        logger.error("Failed to retrieve tokenID.");
						//System.err.println("Failed to retrieve tokenID.");
					}
				}
			} else {
                logger.error("Failed to insert token.");
			}
		} catch (SQLException e) {
            logger.error("Error inserting token: {}", e.getMessage());
			e.printStackTrace();
		}

		return tokenID;
	}
	@Override
	public void updateRoot(String oldValue, String newValue) throws SQLException {
		try {
			String sql = "UPDATE roots SET value = ? WHERE value = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, newValue);
			preparedStatement.setString(2, oldValue);
			preparedStatement.executeUpdate();
            logger.info("Root updated - Old Value: {}, New Value: {}", oldValue, newValue);
		} catch (Exception e) {
            logger.error("Error updating root: {}", e.getMessage());
			e.printStackTrace();
		}
	}
	@Override
	public void deleteRoot(String value) throws SQLException {
		try {
			String sql = "DELETE FROM roots WHERE value = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, value);
			preparedStatement.executeUpdate();
            logger.info("Root deleted - Value: {}", value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public List<String> getAllRoots() {
		List<String> roots = new ArrayList<>();
		try {
			String sql = "SELECT value FROM roots WHERE value != '-'";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				roots.add(resultSet.getString("value"));
			}
		} catch (Exception e) {
            logger.error("Error getting all roots: {}", e.getMessage());
			e.printStackTrace();
		}
		return roots;
	}

	@Override
	public List<VerseDTO> getVerseByRootID(String rootValue) {
		List<String> tokenIDs = new ArrayList<>();

		String sql = "SELECT tokenID FROM roots WHERE value = ?";

		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setString(1, rootValue);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					String tokenID = resultSet.getString("tokenID");
					tokenIDs.add(tokenID);
				}
			}

			
		} catch (SQLException e) {
			 logger.error("Error getting verse by root ID: {}", e.getMessage());
	            e.printStackTrace();		}

		return getVerseIDs(tokenIDs);
	}

	
	public List<VerseDTO> getVerseIDs(List<String> tokenIDs) {
		List<Integer> verseIDs = new ArrayList<>();

		String sql = "SELECT verseid FROM tokens WHERE tokenid = ?";

		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			for (String tokenID : tokenIDs) {
				preparedStatement.setString(1, tokenID);

				try (ResultSet resultSet = preparedStatement.executeQuery()) {
					while (resultSet.next()) {
						int verseID = resultSet.getInt("verseid");
						verseIDs.add(verseID);
					}
				}
			}


		} catch (SQLException e) {
			e.printStackTrace(); 
		}

		return getVerseDTOs(verseIDs);
	}

	public static List<VerseDTO> getVerseDTOs(List<Integer> verseIDs) {
		List<VerseDTO> verseDTOs = new ArrayList<>();

		String sql = "SELECT verseID, poemID, Verse1, Verse2 FROM verses WHERE verseID = ?";

		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			for (int verseID : verseIDs) {
				preparedStatement.setInt(1, verseID);

				try (ResultSet resultSet = preparedStatement.executeQuery()) {
					while (resultSet.next()) {
						int retrievedVerseID = resultSet.getInt("verseID");
						int poemID = resultSet.getInt("poemID");
						String verse1 = resultSet.getString("Verse1");
						String verse2 = resultSet.getString("Verse2");

						// Create VerseDTO object and add to the list
						VerseDTO verseDTO = new VerseDTO(poemID, retrievedVerseID, verse1, verse2);
						verseDTOs.add(verseDTO);
					}
				}
			}
			for (VerseDTO verseDTO : verseDTOs) {
				System.out.println("VerseDTO: " + verseDTO);
			}
		} catch (SQLException e) {
			e.printStackTrace(); 
		}

		return verseDTOs;
	}
	
	public static List<RootDTO> getRootByVerseID(int verseID) throws SQLException {
		    List<String> tokenIDs = new ArrayList<>();

		    String sql = "SELECT tokenID FROM tokens WHERE verseID = ?";
		    
		    try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
		        preparedStatement.setInt(1, verseID);

		        try (ResultSet resultSet = preparedStatement.executeQuery()) {
		            while (resultSet.next()) {
		            	String tokenID = resultSet.getString("tokenid");
		                tokenIDs.add(tokenID);
		            }
		            
		            for(String tokn : tokenIDs) {
		        		System.out.println(tokn);
		            }
		            System.out.println("-------------------------");
		            
		        }
		    }

		    return getRootFromToken(tokenIDs);
		}
	public static List<RootDTO> getRootFromToken(List<String> toknid) throws SQLException {
	    List<RootDTO> rootIDs = new ArrayList<>();

	    String sql = "SELECT value FROM roots WHERE value != '-' AND tokenID = ?";
	    
	    try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			for (String tID : toknid) {
				preparedStatement.setString(1, tID);


	        try (ResultSet resultSet = preparedStatement.executeQuery()) {
	            while (resultSet.next()) {
	            	String tokenID = resultSet.getString("value");
	            	
	            	RootDTO rootDTO = new RootDTO(0, tokenID, "null");
	                rootIDs.add(rootDTO);
	            }
	        }
	            
	            
	        }
			for(RootDTO tokn : rootIDs) {
        		System.out.println(tokn.getValue());
            }
	    }

	    return rootIDs;
	}
	
	@Override
	public Boolean setManualRoot(String value) throws SQLException {
	    String sql = "UPDATE roots SET assignmentStatus = 'Manual' WHERE value = ?";

	    System.out.println("Inside TokenDao");
	    try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
	        pstmt.setString(1, value);

	        return pstmt.executeUpdate() == 1;
	    } catch (SQLException e) {
	        e.printStackTrace(); // Log or handle the exception appropriately
	        return false;
	    }
	}

	@Override
	public List<TokenDTO> getTokenByVerseID(int verseID) {
		List<TokenDTO> token = new ArrayList<>();
		String query = "SELECT token, tag FROM tokens WHERE verseID = ?";

		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, verseID);
			ResultSet rs = statement.executeQuery();

			while (rs.next()) {
				String tokenName = rs.getString("token");
				String tagName = rs.getString("tag");
				token.add(new TokenDTO(0, 0, tokenName, 0, tagName));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return token;
	}

	 public  void assignTokensRootsPOS(String verse1, String verse2, int poemID,int verseID) {
	        try {

	            // Process and insert tokens, roots, and POS for verse1
	            processAndInsertTokensRootsPOS(verse1, verseID);

	            // Process and insert tokens, roots, and POS for verse2
	            processAndInsertTokensRootsPOS(verse2, verseID);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	    private  void processAndInsertTokensRootsPOS(String verseContent, int verseID) {
			List<String> previousMisraTokens = tokenizer.tokenize(verseContent);
	        for (String token : previousMisraTokens) {
	            try {
	                int tokenID = insertToken(token, verseID);

	                // Root Insertion
	                String temp = net.oujda_nlp_team.AlKhalil2Analyzer.getInstance().processToken(token).getAllRootString();
	                String[] rootsArray = temp.split(":");
	                List<String> rootsList = new ArrayList<>();
	                for (String root : rootsArray) {
	                    if (!root.trim().isEmpty()) {
	                        rootsList.add(root.trim());
	                    }
	                }
	                for (String root : rootsList) {
	                    RootDTO rootDTO = new RootDTO(tokenID, root, "automatic");
						rootDAO.insertRoot(rootDTO);
	                }

	                // Part of Speech Tagging
					assignPOS(tagger.getPartOfSpeech(token), tokenID);
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
	    }
	}


