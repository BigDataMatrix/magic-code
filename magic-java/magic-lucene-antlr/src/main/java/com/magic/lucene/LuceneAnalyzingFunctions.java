

package com.magic.lucene;

import java.util.function.Function;

import static com.google.common.base.CharMatcher.JAVA_LETTER_OR_DIGIT;
import static com.google.common.base.CharMatcher.WHITESPACE;


public interface LuceneAnalyzingFunctions {

     Function<String, String> removeMultipleSpace = s -> s.replaceAll("\\s+", " ");
     Function<String, String> lettersNumbersWhitespace = s -> s.replaceAll("[^a-zA-Z0-9\\s]", "");
     Function<String, String> lettersNumbersWildcard = s -> s.replaceAll("[^a-zA-Z0-9*?]", "");
     Function<String, String> removeWhitespace = WHITESPACE::removeFrom;
     Function<String, String> trim = String::trim;
     Function<String, String> lettersNumbers = JAVA_LETTER_OR_DIGIT::retainFrom;
     Function<String, String> lowerCase = String::toLowerCase;
     Function<String, String> lettersNumbersLowercase = lettersNumbers.andThen(lowerCase);
     Function<String, String> lettersNumbersTrimLowerCase = lettersNumbersLowercase.andThen(removeWhitespace);
}
