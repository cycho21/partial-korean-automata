package com.nexon.automata.unicode;

import com.nexon.automata.divide.Divisor;

/**
 * Created by chan8 on 2017-01-10.
 */
public interface UnicodeChecker {

    String[] isKorean(String koString, Divisor divisor);

    boolean isConsonant(char singleCharacter);
    
}
