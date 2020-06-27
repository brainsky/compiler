package com.inter;

import com.lexer.Num;
import com.lexer.Token;
import com.lexer.Word;
import com.symbols.Type;

/**
 * 创建一个常量对象
 */
public class Constant extends Expr {

    public Constant(Token token, Type type){
        super(token, type);
    }

    public Constant(int i){
        super(new Num(i), Type.Int);
    }

    public static final Constant
            True = new Constant(Word.True, Type.Bool),
            Flase = new Constant(Word.False, Type.Bool);

    /**
     * 判断跳转 t-true, f-false, 生成跳转命令
     * @param t
     * @param f
     */
    public void jumping(int t, int f){

        if( this == True && t != 0){
            emit("goto L"+t);
        }else if(this == Flase && f != 0){
            emit("goto L"+f);
        }

    }
}
