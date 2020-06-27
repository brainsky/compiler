package com.inter;

import com.lexer.Tag;
import com.lexer.Word;
import com.symbols.Type;

/**
 * 数组
 */
public class Access extends Op {

    public Id array;

    public Expr index;

    public Access(Id a, Expr i, Type p){
        super(new Word(Tag.INDEX,"[]"), p);
        array = a;
        index = i;
    }

    public Expr gen(){
        return new Access(array, index.reduce(), type);
    }

    public void jumping(int t, int f){
        emitJumps(reduce().toString(), t, f);
    }

    @Override
    public String toString() {
        return array.toString() + " [ " + index.toString() + " ] ";
    }
}
