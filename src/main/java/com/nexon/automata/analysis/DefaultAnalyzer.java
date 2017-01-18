package com.nexon.automata.analysis;

import com.nexon.automata.bean.KoreanBean;
import com.nexon.automata.divide.Divisor;
import com.nexon.automata.exception.KoreanException;
import com.nexon.automata.unicode.UnicodeChecker;

import java.util.ArrayDeque;

/**
 * Created by chan8 on 2017-01-13.
 */
public class DefaultAnalyzer implements Analyzer {

    private final int FAILURE = -2;
    private final char BACK_SPACE = '/';
    private final char WHITE_SPACE = ' ';

    private final int INIT = -1;
    private final int F = 0;
    private final int FM = 1;
    private final int FFM = 2;
    private final int FFML = 3;
    private final int FFMLL = 4;
    private final int FML = 5;
    private final int FMLL = 6;
    private final int FF = 7;
    private int status = INIT;

    private StringBuilder stringBuilder;
    private UnicodeChecker unicodeChecker;
    private Divisor divisor;

    public DefaultAnalyzer() {
    }

    @Override
    public String analyze(String inputString) throws KoreanException {
        ArrayDeque<KoreanBean> completedDeque = new ArrayDeque<KoreanBean>();
        ArrayDeque<Character> uncompletedDeque = new ArrayDeque<Character>();
        stringBuilder = new StringBuilder();

        status = INIT;
        int length = inputString.length();
        char lastChar = 0;
        int idx = 0;

        String[] retArr = new String[2];

        while (idx < length) {
            char nowChar = inputString.charAt(idx);
            boolean isConsonant = unicodeChecker.isConsonant(nowChar);

//            System.out.println(status + " : " + nowChar + " uncompletedStack size : " + uncompletedDeque.size());

            switch (nowChar) {

                case WHITE_SPACE:
                    
                    if (!uncompletedDeque.isEmpty()) {
                        makeCompleteCharacter(uncompletedDeque, completedDeque);
                        completedDeque.offerLast(new KoreanBean(" ", " "));
                    } else {
                        completedDeque.offerLast(new KoreanBean(" ", " "));
                    }
                    status = INIT;
                    break;

                case BACK_SPACE:
                    if (!uncompletedDeque.isEmpty())
                        uncompletedDeque.pollLast();
                    else
                        completedDeque.pollLast();
                    break;

                default:

                    switch (status) {

                        case FAILURE:
                            failure(lastChar, uncompletedDeque, completedDeque);
                            break;

                        case INIT:
                            if (isConsonant) {
                                uncompletedDeque.offerLast(nowChar);
                                status = F;
                            } else {
                                lastChar = nowChar;
                                status = FAILURE;
                            }
                            break;

                        case F: // ㄱ
                            uncompletedDeque.offerLast(nowChar);

                            if (isConsonant)
                                status = FF;
                            else
                                status = FM;
                            break;

                        case FF: // ㄲ
                            if (isConsonant) {
                                throw new KoreanException("INVALID KOREAN STRING");
                            } else {
                                uncompletedDeque.offerLast(nowChar);
                                status = FFM;
                            }
                            break;

                        case FM: // 그
                            if (isConsonant) {
                                uncompletedDeque.offerLast(nowChar);
                                status = FML;
                            } else {
                                uncompletedDeque.offerLast(nowChar);
                                status = FML;
                            }
                            break;

                        case FFM: // 끄
                            if (isConsonant) {
                                uncompletedDeque.offerLast(nowChar);
                                status = FFML;
                            } else {
                                throw new KoreanException("INVALID KOREAN STRING");
                            }
                            break;

                        case FFML: // 끅
                            if (isConsonant) { // 끆
                                uncompletedDeque.offerLast(nowChar);
                                makeCompleteCharacter(uncompletedDeque, completedDeque);
                                status = INIT;
                            } else { // 끄그
                                Character tempChar = uncompletedDeque.pollLast();
                                makeCompleteCharacter(uncompletedDeque, completedDeque);
                                uncompletedDeque.offerLast(tempChar);
                                uncompletedDeque.offerLast(nowChar);
                                status = FM;
                            }
                            break;

                        case FMLL: // 귺
                            lastChar = nowChar;
                            uncompletedDeque.offerLast(nowChar);
                            makeCompleteCharacter(uncompletedDeque, completedDeque);
                            status = INIT;
                            break;

                        case FML: // 극
                            if (isConsonant) {
                                uncompletedDeque.offerLast(nowChar);
                                makeCompleteCharacter(uncompletedDeque, completedDeque);
                                status = INIT;
                            } else {
                                Character tempChar = uncompletedDeque.pollLast();
                                makeCompleteCharacter(uncompletedDeque, completedDeque);
                                uncompletedDeque.offerLast(tempChar);
                                uncompletedDeque.offerLast(nowChar);
                                status = FM;
                            }
                            break;

                        default:
                            break;
                    }
                    break;
            }
            if (status != FAILURE)
                idx++;
        }
        return postProcess(uncompletedDeque, completedDeque);
    }

    private String postProcess(ArrayDeque<Character> uncompletedDeque, ArrayDeque<KoreanBean> completedDeque) {
        while (!uncompletedDeque.isEmpty()) {
            stringBuilder.append(uncompletedDeque.poll());
        }

        String[] ret = unicodeChecker.isKorean(stringBuilder.toString(), divisor);

        if (!ret[0].equals("FALSE"))
            completedDeque.offerLast(new KoreanBean(ret[0], stringBuilder.toString()));
        
            stringBuilder.setLength(0);

        return printResult(stringBuilder, uncompletedDeque, completedDeque);
    }

    private String printResult(StringBuilder stringBuilder, ArrayDeque<Character> uncompletedDeque,
                               ArrayDeque<KoreanBean> completedDeque) {
        stringBuilder.append("OUTPUT : ");
        String result = "";
        
        while (!completedDeque.isEmpty()) {
            KoreanBean pollFirst = completedDeque.pollFirst();
            result += pollFirst.getValue();
            stringBuilder.append(pollFirst.getCode());
            if (completedDeque.size() != 0)
                stringBuilder.append(", ");
        }
        stringBuilder.append(" (" + result + ")");

        return stringBuilder.toString();
    }

    private void makeCompleteCharacter(ArrayDeque<Character> uncompletedDeque, ArrayDeque<KoreanBean> completedDeque) {
        while (!uncompletedDeque.isEmpty()) {
            stringBuilder.append(uncompletedDeque.pollFirst());
        }

        String[] retArr = unicodeChecker.isKorean(stringBuilder.toString(), divisor);
        if (!retArr[0].equals("FALSE")) {
            completedDeque.offerLast(new KoreanBean(retArr[0], stringBuilder.toString()));
        }

        stringBuilder.setLength(0);
    }

    private void failure(char lastChar, ArrayDeque<Character> uncompletedDeque, ArrayDeque<KoreanBean> completedDeque) throws KoreanException {
        KoreanBean lastSyllable = completedDeque.pollLast();
        String code = lastSyllable.getCode();
        String[] ret = unicodeChecker.isKorean(code.substring(0, code.length() - 1), divisor);

        if (!ret[0].equals("FALSE")) {
            completedDeque.offerLast(new KoreanBean(ret[0], code.substring(0, code.length() - 1)));
            uncompletedDeque.offerLast((char) code.codePointAt(code.length() - 1));
            uncompletedDeque.offerLast(lastChar);
        } else {
            throw new KoreanException("INVALID KOREAN STRING");
        }

        if (unicodeChecker.isConsonant(lastChar))
            status = FF;
        else
            status = FM;
    }

    public void setDivisor(Divisor divisor) {
        this.divisor = divisor;
    }

    public void setUnicodeChecker(UnicodeChecker unicodeChecker) {
        this.unicodeChecker = unicodeChecker;
    }
}