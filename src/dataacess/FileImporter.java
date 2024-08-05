package dataacess;

import java.io.*;
import java.util.List;

import dtos.RootDTO;
import utils.POS_Tagger;
import utils.Tokenizer;

import java.util.ArrayList;
import java.util.Arrays;

public class FileImporter implements FileImporterInterface{
	private static PoemDAO poemDAO;
	private static VerseDAO verseDAO;
	private DatabaseConnector connection;
	private static Tokenizer tokenizer;
	private static TokenDAO tokenDAO;
	private static RootDAO rootDAO;
	private static POS_Tagger tagger;
	static List<String> tokens = new ArrayList<>();

	public FileImporter() {
		connection = DatabaseConnector.getInstance();
		poemDAO = new PoemDAO(connection);
		verseDAO = new VerseDAO(connection);
		tokenizer = new Tokenizer();
		tokenDAO = new TokenDAO(connection);
		rootDAO = new RootDAO(connection);
		tagger = new POS_Tagger();
	}

	public void importFile(File selectedFile) {
		try (BufferedReader br = new BufferedReader(
				new InputStreamReader(new FileInputStream(selectedFile), "UTF-8"))) {
			String line;
			boolean skipContent = false;

			Integer poemID = null;
			@SuppressWarnings("unused")
			Integer verseID = null;
			String previousVerse = null;

			while ((line = br.readLine()) != null) {
				if (line.contains("_________")) {
					skipContent = true;
				} else if (line.contains("==========")) {
					skipContent = false;
				} else if (!skipContent) {
					if (line.startsWith("[") && line.contains("]")) {
						String poemTitle = line.substring(1, line.indexOf("]")).trim();
						poemID = poemDAO.createPoem(1, poemTitle);
					} else if (line.startsWith("(") && line.contains(")")) {
						String verseContent = line.substring(1, line.indexOf(")")).trim();
						List<String> misras = Arrays.asList(verseContent.split("\\.\\.\\."));
						for (String misra : misras) {
							if (poemID != null && !misra.trim().isEmpty()) {
								if (previousVerse == null) {
									previousVerse = misra.trim();
								} else {
									verseDAO.createVerse(poemID, previousVerse, misra.trim());
									Integer verseID1 = verseDAO.getVerseID(poemID, previousVerse);
									List<String> previousMisraTokens = tokenizer.tokenize(previousVerse);
									// Insert tokens for previous verse
									for (String token : previousMisraTokens) {
										int tokenID = tokenDAO.insertToken(token, verseID1.intValue());

										// Root Insertion
										String temp = net.oujda_nlp_team.AlKhalil2Analyzer.getInstance().processToken(token).getAllRootString();
										String[] rootsArray = temp.split(":");
										List<String> rootsList = new ArrayList<>();
										for (String root : rootsArray) {
											if (!root.trim().isEmpty()) {
												rootsList.add(root.trim());
											}
										}
										for (String root: rootsList) {
											RootDTO rootDTO = new RootDTO(tokenID, root, "automatic");
											rootDAO.insertRoot(rootDTO);
										}

										// Part of Speech Tagging
										tokenDAO.assignPOS(tagger.getPartOfSpeech(token), tokenID);
									}
									List<String> currentMisraTokens = tokenizer.tokenize(misra.trim());
									// Insert tokens for current verse
									for (String token : currentMisraTokens) {
										int tokenID = tokenDAO.insertToken(token, verseID1.intValue());
										// Root Insertion
										String temp = net.oujda_nlp_team.AlKhalil2Analyzer.getInstance().processToken(token).getAllRootString();
										String[] rootsArray = temp.split(":");
										List<String> rootsList = new ArrayList<>();
										for (String root : rootsArray) {
											if (!root.trim().isEmpty()) {
												rootsList.add(root.trim());
											}
										}
										for (String root: rootsList) {
											RootDTO rootDTO = new RootDTO(tokenID, root, "automatic");
											rootDAO.insertRoot(rootDTO);
										}

										// Part of Speech Tagging
										tokenDAO.assignPOS(tagger.getPartOfSpeech(token), tokenID);
									}
									previousVerse = null;
								}
							}
						}
					}
					// Handle case where the last verse does not have a pair
					if (previousVerse != null) {
						Integer verseID1=verseDAO.createVerse(poemID, previousVerse, null);
						verseID1 = verseDAO.getVerseID(poemID, previousVerse);
						for (String token : tokenizer.tokenize(previousVerse)) {
							tokenDAO.insertToken(token, verseID1.intValue());
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		tokens.clear();
	}

}
