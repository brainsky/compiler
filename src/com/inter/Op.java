package com.inter;

import com.lexer.Token;
import com.symbols.Type;

/**
 * 运算符
 */
public class Op extends Expr {

    public Op(Token token, Type type){
        super(token,type);
    }

    public Expr reduce(){
        Expr x = gen();
        Temp temp = new Temp(type);

        emit(temp.toString() + " = "+x.toString());

        return temp;
    }



}
