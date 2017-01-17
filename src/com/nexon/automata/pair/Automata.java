package com.nexon.automata.pair;

import com.nexon.automata.analysis.DefaultAnalyzer;
import com.nexon.automata.divide.Divisor;
import com.nexon.automata.exception.KoreanException;
import com.nexon.automata.unicode.DefaultUnicodeChecker;

/**
 * Created by chan8 on 2017-01-11.
 */
public class Automata {

    private DefaultAnalyzer defaultAnalyzer;

    public void initialize() {
        defaultAnalyzer = new DefaultAnalyzer();
        defaultAnalyzer.setUnicodeChecker(new DefaultUnicodeChecker());
        defaultAnalyzer.setDivisor(new Divisor());
    }
    
    public String analyze(String input) {
        try {
            return defaultAnalyzer.analyze(input);
        } catch (KoreanException e) {
            e.printStackTrace();
        }
        return "INVALID KOREAN STRING";
    }
}
