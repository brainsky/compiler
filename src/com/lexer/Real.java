package com.lexer;

/**
 * 存放浮点数
 */
public class Real extends Token {

    private final float value;

    public Real(float value) {
        super(Tag.REAL);
        this.value = value;
    }

    @Override
    public String toString() {
        return "" + value;
    }
}
