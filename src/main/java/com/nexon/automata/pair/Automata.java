package com.nexon.automata.pair;

import com.nexon.automata.analysis.DefaultAnalyzer;
import com.nexon.automata.divide.Divisor;
import com.nexon.automata.exception.KoreanException;
import com.nexon.automata.unicode.DefaultUnicodeChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by chan8 on 2017-01-11.
 */
public class Automata {

    private DefaultAnalyzer defaultAnalyzer;
    private Logger logger = LoggerFactory.getLogger(DefaultAnalyzer.class);

    public void initialize() {
        defaultAnalyzer = new DefaultAnalyzer();
        defaultAnalyzer.setUnicodeChecker(new DefaultUnicodeChecker());
        defaultAnalyzer.setDivisor(new Divisor());
    }
    
    public String analyze(String input) {
        try {
            return defaultAnalyzer.analyze(input);
        } catch (KoreanException e) {
            logger.error(e.getMessage());
        }
        return "INVALID KOREAN STRING";
    }
    
}
