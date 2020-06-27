package com.inter;

import com.symbols.Type;

public class Set extends Stmt {

    public Id id;

    public Expr expr;

    public Set(Id i, Expr e){

        id = i;

        expr = e;

        if(check(id.type, expr.type) == null){
            error("type error");
        }
    }

    public Type check(Type type, Type type1) {

       return  Type.numeric(type) && Type.numeric(type1) ? type1
               : type == Type.Bool && type1 == Type.Bool ? type1 : null;

    }

    public void gen(int b, int a){
        emit(id.toString() + " = "+expr.gen().toString());
    }

}
