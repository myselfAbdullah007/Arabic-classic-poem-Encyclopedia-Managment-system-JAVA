package utils;

import java.util.Arrays;
import java.util.List;

public class Tokenizer {
    public List<String> tokenize(String verseText) {
        return Arrays.asList(verseText.split("\\s+"));
    }
}
