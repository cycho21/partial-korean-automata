package com.nexon.automata.unicode;

import com.nexon.automata.divide.Divisor;

import java.io.UnsupportedEncodingException;

/**
 * Created by chan8 on 2017-01-10.
 */
public class DefaultUnicodeChecker implements UnicodeChecker {

    private final int START_OF_KOREAN = 0xAC00;
    private final int END_OF_KOREAN = 0xD74F;
    
    public boolean isConsonant(char singleCharacter) {
        
        if (singleCharacter >= 'A' && singleCharacter <= 'Z')
            return true;
        
        return false;
    }
    

    public String[] isKorean(String koString, Divisor divisor) {
        String[] ret = new String[2];

        if (koString.length() < 2 || koString.length() > 5) {
            ret[0] = "FALSE";
            return ret;
        }

        int[] dividedString = divisor.divideString(koString);

        if (dividedString == null) {
            ret[0] = "FALSE";
            return ret;
        }

        ret[1] = String.valueOf(dividedString.length);

        int unicode = 0;
        
        if (dividedString[2] != 0)
            unicode = START_OF_KOREAN + ((28 * 21 * (dividedString[0])) + 28 * dividedString[1]) + dividedString[2];
        else 
            unicode = START_OF_KOREAN + ((28 * 21 * (dividedString[0])) + 28 * dividedString[1]) + 0;
        
        if (unicode >= START_OF_KOREAN && unicode <= END_OF_KOREAN) {
            ret[0] = String.valueOf((char) unicode);
            return ret;
        }

        ret[0] = "FALSE";
        return ret;
    }
    
}