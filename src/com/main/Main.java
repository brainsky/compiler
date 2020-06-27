package com.main;

import com.lexer.Lexer;
import com.parser.Parser;

import java.io.IOException;

/**
 * 入口程序
 */
public class Main {

    public static void main(String[] args) throws IOException {

        Lexer lex = new Lexer();

        Parser parser = new Parser(lex);

        parser.program();

        System.out.println("\n");

    }

}
