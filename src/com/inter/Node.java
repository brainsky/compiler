package com.inter;

import com.lexer.Lexer;

/**
 * 保存抽象语法树的结点, Node有2个子节点，分别位Expr节点和Stmt语句节点
 */
public class Node {

    int lexline = 0;

    Node(){
        lexline = Lexer.line;
    }

    void error(String msg){
        throw new Error("near line "+lexline+": "+msg);
    }

    static int labels = 0;

    public int newlabel(){
        return ++labels;
    }

    public void emitlabel(int i){
        System.out.print("L" + i +":");
    }

    public void emit(String s){
        System.out.println("\t"+s);
    }

}
