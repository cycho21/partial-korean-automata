package com.nexon.automata.analysis;

import com.nexon.automata.exception.KoreanException;

/**
 * Created by chan8 on 2017-01-13.
 */
public interface Analyzer {
    
    String analyze(String inputString) throws KoreanException;

}
