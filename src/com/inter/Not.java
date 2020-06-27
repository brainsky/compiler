package com.inter;

import com.lexer.Token;

public class Not extends Logical {

    public Not(Token token, Expr expr){
        super(token, expr, expr);
    }

    public void jumping(int t, int f){
        expr2.jumping(f, t);
    }

    @Override
    public String toString() {
        return operation.toString() + " "+expr2.toString();
    }
}
