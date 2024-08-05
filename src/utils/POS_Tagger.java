package utils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.oujda_nlp_team.AlKhalil2Analyzer;
import net.oujda_nlp_team.entity.Result;

public class POS_Tagger {
	public String getPartOfSpeech(String token) {
		List<Result> results = AlKhalil2Analyzer.getInstance().processToken(token).getAllResults();
        Set<String> uniquePrimaryPartOfSpeechSet = new HashSet<>();
        String toReturn = "";

        for (Result result : results) {
            String partOfSpeech = result.getPartOfSpeech();
            // Extract the primary part of speech (before the first '|')
            String primaryPartOfSpeech = partOfSpeech.split("\\|")[0].trim();
            // Add the primary part of speech to the set to ensure uniqueness
            uniquePrimaryPartOfSpeechSet.add(primaryPartOfSpeech);
        }
        for (String uniquePrimaryPartOfSpeech : uniquePrimaryPartOfSpeechSet) {
            toReturn = uniquePrimaryPartOfSpeech;
        }
        return toReturn;
	}
}
