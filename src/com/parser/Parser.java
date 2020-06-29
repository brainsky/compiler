package com.parser;

import com.inter.*;
import com.lexer.*;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import com.symbols.Array;
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

    Type dims(Type type) throws  IOException{
        match('[');
        Token token = look;
        match(Tag.NUM);
        match(']');
        if( look.getTag() == '['){
            type = dims(type);
        }
        return new Array(((Num)token).value,type);
    }

    Stmt stmts() throws IOException{
        if( look.getTag() == '}'){
            return Stmt.Null;
        }else {
            return new Seq(stmt(), stmts());
        }
    }

    Stmt stmt() throws IOException{
        Expr x;
        Stmt s, s1, s2;
        Stmt savedStmt; // 用于为break语句保存外层的循环语句
        switch (look.getTag()){
            case ':':
                move();
                return Stmt.Null;
            case Tag.IF:
                match(Tag.IF);
                match('(');
                x = bool();
                match(')');
                s1 = stmt();
                if(look.getTag() != Tag.ELSE){
                    return new If(x, s1);
                }
                match(Tag.ELSE);
                s2 = stmt();
                return new Else(x, s1, s2);
            case Tag.WHILE:
                While whileNode = new While();
                savedStmt = Stmt.Enclosing;
                Stmt.Enclosing = whileNode;
                match(Tag.WHILE);
                match('(');
                x = bool();
                match(')');
                s1 = stmt();
                whileNode.init(x, s1);
                Stmt.Enclosing = savedStmt;
                return whileNode;
            case Tag.DO:
                Do donode = new Do();
                savedStmt = Stmt.Enclosing;
                Stmt.Enclosing = donode;
                match(Tag.DO);
                s1 = stmt();
                match(Tag.WHILE);
                match('(');
                x = bool();
                match(')');
                match(';');
                donode.init(s1, x);
                Stmt.Enclosing = savedStmt;
                return donode;
            case Tag.BREAK:
                match(Tag.BREAK);
                match(';');
                return new Break();
            case '{':
                return block();
            default:
                    return assign();
        }
    }

    Stmt assign() throws IOException {
        Stmt stmt;
        Token token = look;
        match(Tag.ID);
        Id id = top.get(token);
        if(id == null){
            error(token.toString() + " undeclared");
        }
        if( look.getTag() == '='){
            move();
            stmt = new Set(id, bool());
        }else {
            Access x = offset(id);
            match('=');
            stmt = new SetElem(x, bool());
        }
        match(';');
        return stmt;
    }

    Expr bool() throws IOException{
        Expr expr = join();
        while (look.getTag() == Tag.OR){
            Token token = look;
            move();
            expr = new Or(token, expr, join());
        }

        return expr;
    }

    Expr join() throws IOException{
        Expr expr = equality();
        while (look.getTag() == Tag.AND){
            Token token = look;
            move();
            expr = new And(token, expr, equality());
        }
        return expr;
    }

    Expr equality() throws IOException {
        Expr expr = rel();
        while (look.getTag() == Tag.EQ || look.getTag() == Tag.NE){
            Token token = look;
            move();
            expr = new Rel(token, expr ,rel());
        }
        return expr;
    }

    Expr rel() throws IOException{
        Expr expr = expr();
        switch (look.getTag()){
            case '<':
            case Tag.LE:
            case Tag.GE:
            case '>':
                Token token = look;
                move();
                return new Rel(token, expr, expr());
            default:
                    return expr;
        }
    }

    Expr expr() throws IOException {
        Expr expr = term();
        while (look.getTag() == '+' ||  look.getTag() == '-'){
            Token token = look;
            move();
            expr = new Arith(token, expr, term());
        }
        return expr;
    }

    Expr term() throws IOException{
        Expr expr  = unary();
        while (look.getTag() == '*' || look.getTag() == '/' ){
            Token token = look;
            move();
            expr = new Arith(token, expr, unary());
        }
        return expr;
    }

    Expr unary() throws IOException{
        if( look.getTag() == '-'){
            move();
            return new Unary(Word.minus,unary());
        }else if(look.getTag() == '!'){
            Token token = look;
            move();
            return new Not(token, unary());
        }else {
            return factor();
        }
    }

    Expr factor() throws IOException{

        Expr expr = null;

        switch (look.getTag()){
            case '(':
                move();
                expr = bool();
                match(')');
                return expr;
            case Tag.NUM:
                expr = new Constant(look, Type.Int);
                move();
                return expr;
            case Tag.REAL:
                expr = new Constant(look, Type.Float);
                move();
                return expr;
            case Tag.TRUE:
                expr = Constant.True;
                move();
                return expr;
            case Tag.FALSE:
                expr = Constant.Flase;
                move();
                return expr;
            case Tag.ID:
                String string = look.toString();
                Id id = top.get(look);
                if(id == null){
                    error(look.toString() + "undeclared");
                }
                move();
                if(look.getTag() != '['){
                    return id;
                }else {
                    return offset(id);
                }
        }

        return expr;
    }

    Access offset(Id id) throws IOException{
        Expr i;
        Expr w;
        Expr t1, t2;
        Expr loc;
        Type type = id.type;
        match('[');
        i = bool();
        match(']');
        type = ((Array)type).elementType;
        w = new Constant(type.width);
        t1 = new Arith(new Token('*'),i,w);
        loc = t1;
        while (look.getTag() == '['){
            match('[');
            i = bool();
            match(']');
            type = ((Array)type).elementType;
            w = new Constant(type.width);
            t1 = new Arith(new Token('*'), i, w);
            t2 = new Arith(new Token('+'), loc, t1);
            loc = t2;
        }
        return new Access(id, loc, type);
    }


}
