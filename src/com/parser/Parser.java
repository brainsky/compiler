package com.parser;

import com.inter.Id;
import com.inter.Stmt;
import com.lexer.Lexer;
import com.lexer.Tag;
import com.lexer.Token;
import com.lexer.Word;
import com.symbols.Env;
import com.symbols.Type;

import java.io.IOException;

/**
 * 语法分析器
 */
public class Parser {

    /**
     * 词法分析器
     */
    private Lexer lexer;

    //向前看词法单元
    private Token look;

    //当前或顶层的符合表
    Env top = null;

    //用于存储变量声明的存储位置
    int used = 0;

    public Parser(Lexer lexer) throws IOException {
        this.lexer = lexer;
        move();
    }

    void move() throws IOException {
        look = lexer.scan();
    }

    void error(String s){
        throw new Error("near line " + Lexer.line + ": "+s);
    }

    void match(int t) throws IOException{
        if( look.getTag() == t){
            move();
        }else {
            error("syntax error");
        }

    }

    public void program() throws IOException {
        Stmt s = block();

        int begin = s.newlabel();

        int after = s.newlabel();

        s.emitlabel(begin);

        s.gen(begin, after);

        s.emitlabel(after);
    }


    Stmt block() throws IOException {
        match('{');
        Env savedEnv = top;
        top = new Env(top);
        decls();
        Stmt s = stmts();
        match('}');
        top = savedEnv;
        return s;
    }

    void decls() throws IOException{
        while ( look.getTag() == Tag.BASIC){
            Type type = type();
            Token token = look;
            match(Tag.ID);
            match(';');
            Id id = new Id((Word) token, type, used);
            top.put(token, id);
            used = used + type.width;
        }
    }

    Type type() throws IOException{
        Type type = (Type) look;

        match(Tag.BASIC);

        if( look.getTag() != '['){
            return type;
        }else {
            return dims(type);
        }
    }

}
