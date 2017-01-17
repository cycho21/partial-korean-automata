package com.nexon.automata.divide;

/**
 * Created by chan8 on 2017-01-16.
 */
public class Divisor implements Dividable {

    @Override
    public int[] divideString(String koString) {
        int length = koString.length();
        int[] ret = new int[3];
        int idx = 0;
        
        while (koString.charAt(idx) >= 'A' && koString.charAt(idx) <= 'Z')
            idx++;

        String first = koString.substring(0, idx);
        int boundary = idx;

        if (idx != koString.length() - 1)
            while (koString.charAt(idx) >= '0' && koString.charAt(idx) <= '9')
                idx++;
        else
            idx++;

        String middle = koString.substring(boundary, idx);

        String last = "";
        if (idx != koString.length()) {
            last = koString.substring(idx, length);
        }

        switch (first) {
            case "AA":
                ret[0] = 1;
                break;
            case "CC":
                ret[0] = 4;
                break;
            case "A":
                ret[0] = 0;
                break;
            case "B":
                ret[0] = 2;
                break;
            case "C":
                ret[0] = 3;
                break;
            case "D":
                ret[0] = 5;
                break;
            case "E":
                ret[0] = 6;
                break;
            default:
                break;
        }

        switch (middle) {
            case "1":
                ret[1] = 0;
                break;
            case "2":
                ret[1] = 4;
                break;
            case "3":
                ret[1] = 8;
                break;
            case "4":
                ret[1] = 13;
                break;
            case "5":
                ret[1] = 20;
                break;
            case "31":
                ret[1] = 9;
                break;
            case "42":
                ret[1] = 15;
                break;
            default:
                break;
        }

        switch (last) {
            case "A":
                ret[2] = 0;
                break;
            case "B":
                ret[2] = 4;
                break;
            case "C":
                ret[2] = 7;
                break;
            case "D":
                ret[2] = 8;
                break;
            case "E":
                ret[2] = 16;
                break;
            case "AA":
                ret[2] = 2;
                break;
            case "DA":
                ret[2] = 9;
                break;
            case "DE":
                ret[2] = 10;
                break;
            default:
                break;
        }

        return ret;
    }

}
