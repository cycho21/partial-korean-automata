package com.nexon.automata.bean;

/**
 * Created by chan8 on 2017-01-16.
 */
public class KoreanBean {

    private String value;
    private String code;

    public KoreanBean(String value) {
        this.value = value;
    }

    public KoreanBean(String value, String code) {
        this.value = value;
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public String getCode() {
        return code;
    }
}
