package com.lexer;

public class Num extends Token {

    private final int value;

    public Num(int value){
        super(Tag.NUM);
        this.value = value;
    }

    @Override
    public String toString() {
        return ""+value;
    }
}
