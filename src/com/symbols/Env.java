package com.symbols;

import com.inter.Id;
import com.lexer.Token;

import java.util.Hashtable;

/**
 * 链接符号表
 */
public class Env {

    /**
     * 符号表
     */
    private Hashtable<Token, Id> table;

    /**
     * 前一个环境
     */
    protected Env prev;

    public Env(Env prev){
        table = new Hashtable();
        this.prev = prev;
    }

    public void put(Token token, Id id){
        table.put(token, id);
    }

    /**
     * 向前搜索token对应的标识符，并且取出标识符的信息
     * @param token
     * @return
     */
    public Id get(Token token){
        Id res = null;
        for (Env env = this; env != null; env = env.prev){
            Id idFound = env.table.get(token);
            if(idFound != null){
                res = idFound;
                break;
            }
        }
        return res;
    }



}
