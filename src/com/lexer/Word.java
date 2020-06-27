package com.lexer;

/**
 * 管理保留字、标识符、操作符
 */
public class Word extends Token {

    private final String lexeme;

    public Word(int tag, String word){
        super(tag);
        this.lexeme = word;
    }

    public static final Word
            and = new Word(Tag.AND, "&&"), or = new Word(Tag.OR, "||"),
            eq = new Word(Tag.EQ, "=="), ne = new Word(Tag.NE, "!="),
            le = new Word(Tag.LE, "<="), ge = new Word(Tag.GE, ">="),
            minus = new Word(Tag.MINUS, "minus"), True = new Word(Tag.TRUE, "true"),
            False = new Word(Tag.FALSE, "false"), temp = new Word(Tag.TEMP, "temp");


    public String getLexeme(){
        return lexeme;
    }

    @Override
    public String toString() {
        return lexeme;
    }
}
