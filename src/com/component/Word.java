package com.component;

public class Word extends Token {

    private final String lexeme;

    public Word(int tag, String word){
        super(tag);
        this.lexeme = word;
    }

    public String getLexeme(){
        return lexeme;
    }

    @Override
    public String toString() {
        return "Word = { tag:"+ super.getTag() +", lexeme :"+lexeme+"}";
    }
}
