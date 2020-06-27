package com.inter;

import com.lexer.Token;
import com.symbols.Type;

public class Expr extends Node {

    public Token operation;

    public Type type;

    Expr(Token tok, Type type){
        operation = tok;
        this.type = type;
    }

    /**
     * 生成一个项，三目运算符的右部
     * @return
     */
    public Expr gen(){
        return this;
    }

    /**
     * 规约为一个地址，将返回一个常量、一个标识符、一个临时名字
     * @return
     */
    public Expr reduce(){
        return this;
    }

    public void jumping(int t, int f){
        emitJumps(operation.toString(), t, f);
    }

    /**
     * 跳转
     * @param test
     * @param t
     * @param f
     */
    public void emitJumps(String test, int t, int f) {

        if(t != 0 && f != 0){
            emit("if " + test + " goto L" + t);
            emit("goto L"+ f);
        }else if(t != 0){
            emit("if " + test + " goto L" + t);
        }else if(f != 0){
            emit("if False "+ test + " goto L" + f);
        }else ;

    }

    @Override
    public String toString() {
        return operation.toString();
    }
}
