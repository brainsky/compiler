package com.lexer;

/**
 * 存储标识符，“+”，“-”
 */
public class Token {

    private final  int tag;

    public Token(int tag){
        this.tag = tag;
    }

    public int getTag() {
        return tag;
    }

    @Override
    public String toString() {
        return "" + tag;
    }
}
