package com.inter;

import com.lexer.Token;
import com.symbols.Array;
import com.symbols.Type;

/**
 * 实现运算符 < 、 <= 、 ==、！= 、>=
 */
public class Rel extends Logical {

    public Rel(Token token, Expr expr1, Expr expr2){
        super(token, expr1, expr2);
    }

    public Type check(Type type1, Type type2){
        return type1 instanceof Array || type2 instanceof Array ?
                null : type1 == type2 ? Type.Bool : null;
    }

    public void jumping(int t, int f){
        Expr a = expr1.reduce();
        Expr b = expr2.reduce();
        String test = a.toString() + " "+operation.toString() + " "+b.toString();

        emitJumps(test, t, f);
    }

}
