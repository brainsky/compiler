package com.inter;

import com.lexer.Token;
import com.symbols.Type;

/**
 * 逻辑运算符，如 &&、||
 */
public class Logical extends Expr {

    public  Expr expr1, expr2;

    Logical(Token token, Expr exp1, Expr exp2){
        super(token, null);
        expr1 = exp1;
        expr2 = exp2;
        type = check(exp1.type, exp2.type);
        if(type == null){
            error("type error");
        }
    }

    /**
     * 检查类型
     * @param type
     * @param type1
     * @return
     */
    private Type check(Type type, Type type1) {
        return type == Type.Bool && type1 == Type.Bool ? Type.Bool : null;
    }


    public Expr gen(){
        int f = newlabel();
        int a = newlabel();
        Temp temp = new Temp(type);
        this.jumping(0, f);
        emit(temp.toString() + " = true");
        emit("goto L" + a);
        emitlabel(f);
        emit(temp.toString() + " = false");
        emitlabel(a);
        return temp;
    }

    @Override
    public String toString() {
        return expr1.toString() + " "+operation.toString()+" "+expr2.toString();
    }
}
