package com.inter;

import com.lexer.Word;
import com.symbols.Type;

public class Id extends Expr {

    //相对地址
    public int offset;

    public Id(Word id, Type type, int inputOffset){
        super(id, type);
        offset = inputOffset;
    }
}
