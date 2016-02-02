package com.cgtrc.bym.a10001store.domain;

/**
 * Created by BYM on 2016/2/2.
 */
public class Answer {

    public static final int MM_ANSWER = 0;
    public static final int JOKE_ANSWER = 1;

    public int answerType;
    public String stringAnswer;
    public int intAnswer;

    public static int getMmAnswer() {
        return MM_ANSWER;
    }

    public static int getStringAnswer() {
        return JOKE_ANSWER;
    }

    public void setStringAnswer(String stringAnswer) {
        this.stringAnswer = stringAnswer;
    }

    public int getIntAnswer() {
        return intAnswer;
    }

    public void setIntAnswer(int intAnswer) {
        this.intAnswer = intAnswer;
    }

    public int getAnswerType() {
        return answerType;
    }

    public void setAnswerType(int answerType) {
        this.answerType = answerType;
    }
}
