package com.inter;

import com.symbols.Type;

public class Do extends Stmt {

    Expr expr;

    Stmt stmt;

    public Do(){

        expr = null;

        stmt = null;
    }

    public void init(Stmt s, Expr e){

        expr = e;

        stmt = s;

        if(expr.type != Type.Bool){
            expr.error("boolean required in do");
        }

    }

    public void gen(int a, int b){

        after = a;

        int label = newlabel();

        stmt.gen(b, label);

        emitlabel(label);

        expr.jumping(b, 0);
    }
}
