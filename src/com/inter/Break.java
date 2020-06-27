package com.inter;

/**
 * 跳出循环控制
 */
public class Break extends Stmt {

    Stmt stmt;

    public Break(){
        if(Stmt.Enclosing == Stmt.Null){
            error("unenclosed break");
        }

        stmt = Stmt.Enclosing;
    }

    public void gen(int b, int a){
        emit("goto L" + stmt.after);
    }

}
