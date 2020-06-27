package com.inter;

import com.lexer.Token;

public class And extends Logical {

    public And(Token token, Expr expr1, Expr expr2){
        super(token, expr1, expr2);
    }

    public void jumping(int t, int f){
        int label = f != 0 ? f : newlabel();

        expr1.jumping(0, f);

        expr2.jumping(t, f);

        if( f == 0){
            emitlabel(label);
        }

    }

}
