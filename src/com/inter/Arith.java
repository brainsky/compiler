package com.inter;

import com.lexer.Token;
import com.symbols.Type;

/**
 * 二目运算符，+、*
 */
public class Arith extends Op {

    public Expr expr1, expr2;

    public Arith(Token token, Expr x1, Expr x2){
        super(token, null);
        expr1 = x1;
        expr2 = x2;
        type = Type.max(expr1.type, expr2.type);
        if(type == null){
          error("type error");
        }
    }

    public Expr gen(){
        return new Arith(operation, expr1.reduce(), expr2.reduce());
    }

    @Override
    public String toString() {
        return expr1.toString() + " "+operation.toString() + " "+expr2.toString();
    }
}
