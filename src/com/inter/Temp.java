package com.inter;

import com.lexer.Word;
import com.symbols.Type;

/**
 * 存临时的地址
 * 如 a+b*c --> b*c = t --> a+t
 *
 */
public class Temp extends Expr {

    static int count = 0;

    int number = 0;

    public Temp(Type type){
        super(Word.temp, type);
        number = ++count;
    }

    @Override
    public String toString() {
        return "t" + number;
    }
}
