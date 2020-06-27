package com.inter;

import com.symbols.Array;
import com.symbols.Type;

/**
 * 对数组元素赋值
 */
public class SetElem extends Stmt {

    public  Id array;
    public  Expr index;
    public  Expr expr;

    public SetElem(Access x, Expr y){
        array = x.array;
        index = x.index;
        expr = y;
        if(check(x.type, expr.type) != null){
            error("type error");
        }
    }

    public Type check(Type type, Type type1){
        return type instanceof Array || type1 instanceof Array ?
                null : type == type1 ? type1 : Type.numeric(type) && Type.numeric(type1)
                ? type1 : null;
    }

    public void gen(int b, int a){
        String s1 = index.reduce().toString();
        String s2 = expr.reduce().toString();

        emit(array.toString() + " [ " + s1 + " ] = "+ s2);
    }

}
