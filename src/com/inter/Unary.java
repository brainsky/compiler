package com.inter;

import com.lexer.Token;
import com.symbols.Type;

/**
 * 单目运算符
 */
public class Unary extends Op {

    public Expr expr;

    public Unary(Token token, Expr exprInput){

        super(token, null);

        expr = exprInput;

        type = Type.max(Type.Int, expr.type);

        if(type == null){
            error("type error");
        }
    }

    public Expr gen(){
        return new Unary(operation, expr.reduce());
    }

    @Override
    public String toString() {
        return operation.toString() + " " +expr.toString();
    }
}
