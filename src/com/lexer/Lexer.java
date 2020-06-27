package com.lexer;



import com.symbols.Type;

import java.io.IOException;
import java.util.Hashtable;

/**
 * 词法分析器
 */
public class Lexer {

    //遍历行数
    public static int line = 1;

    //存储输入的下一个字符
    private char peek = ' ' ;

    private Hashtable<String,Word> words = new Hashtable();

    public Lexer(){
        reserve(new Word(Tag.IF, "if"));
        reserve(new Word(Tag.ELSE, "else"));
        reserve(new Word(Tag.WHILE, "while"));
        reserve(new Word(Tag.DO, "do"));
        reserve(new Word(Tag.BREAK, "break"));
        reserve(Word.True);
        reserve(Word.False);
        reserve(Type.Int);
        reserve(Type.Char);
        reserve(Type.Bool);
        reserve(Type.Float);
    }

    private void reserve(Word word){
        words.put(word.getLexeme(), word);
    }

    public Token scan() throws IOException{

        Token result = null;

        boolean hasToken = filterCharater();

        if(hasToken){
            //处理操作符
            result = getOperation();
            if(result == null) {
                //处理数字
                result = getNum();
            }
            if(result == null){
                //处理字符串
                result = getWord();
            }
            System.out.println(result);
        }

        return result;
    }

    /**
     * 处理操作符, && 、 || 、==
     * @return
     */
    private Token getOperation() throws IOException {
        switch (peek){
            case '&':
               return readch('&') ? Word.and : new Token('&');
            case '|':
                return readch('|') ? Word.or : new Token('|') ;
            case '=':
                return readch('=') ? Word.eq : new Token('=');
            case '!':
                return readch('=') ? Word.ne : new Token('!');
            case '<':
                return readch('=') ? Word.le : new Token('<');
            case '>':
                return readch('=') ? Word.ge : new Token('>');
        }
        return null;

    }

    /**
     * 获取下一个字符
     * @throws IOException
     */
    void readch() throws IOException {
        peek = (char) System.in.read();
    }

    /**
     * 判断下一个是否和peek一样的字符
     * @param c
     * @throws IOException
     */
    boolean readch(char c) throws IOException{
        readch();
        boolean res = false;
        if(peek == c ){
            peek = ' ';
            res = true;
        }
        return res;
    }

    /**
     * 过滤注释
     * @return
     * @throws IOException
     */
    public boolean filterCharater() throws IOException{
        boolean res ;
        StringBuffer sb = new StringBuffer();
        for (; ; peek = (char) System.in.read()) {
            //System.out.print(peek);
            if( peek == ' ' || peek == '\t'){
                continue;
            }else if(peek == '\n'){
                line = line + 1;
                //sb中包含//并且首次遇到\n清空
                if(sb.length() > 0 && sb.substring(0,2).contains("//")){
                    //清空
                    sb.setLength(0);
                }

                System.out.println();
            }else if( peek == '/' || peek == '*'){
                sb.append(peek);
                //在sb中包含/* */这样的字符的时候。清空sb
                if(sb.length() >= 3 && sb.toString().contains("/*")  && sb.toString().contains("*/")){
                    sb.setLength(0);
                }
            } else{
                if(sb.toString().contains("//") || sb.toString().contains("/*")){
                    continue;
                }else {
                    res = true;
                    break;
                }

            }

        }
        return res;

    }

    /**
     * 获取数字(包括整形和浮点形)
     * @return
     * @throws IOException
     */
    public Token getNum() throws IOException {
        if(Character.isDigit(peek)){
            int value = 0;

            do{
                // digit peek 输入的字符 radix = 进制
                value = 10*value + Character.digit(peek, 10);
                //获取下一个字符
                peek = (char) System.in.read();
            }while (Character.isDigit(peek));

            if(peek != '.') {
                return new Num(value);
            }else {
                float tempFloat = value;
                float constantValue = 10;
                for (; ;) {
                    readch();
                    if( !Character.isDigit(peek)) break;
                    tempFloat = tempFloat + Character.digit(peek, 10) / constantValue;
                    //移位操作
                    constantValue = constantValue*10;
                }
                return new Real(tempFloat);
            }

        }
        return null;
    }

    /**
     * 获取普通字符
     * @return
     * @throws IOException
     */
    public Word getWord() throws IOException {
        Word word = null;
        if(Character.isLetter(peek)){
            StringBuffer sb = new StringBuffer();
            do{
                sb.append(peek);

                peek = (char) System.in.read();

            }while (Character.isLetter(peek));

            String result = sb.toString();

            word = words.get(result);

            if(word == null){
                word = new Word(Tag.ID, result);

                words.put(result, word);
            }

        }

        return  word;
    }

    public static void main(String[] args) throws IOException {
     /*  Lexer lexer = new Lexer();

       lexer.scan();*/

       System.out.println(Character.isLetter('='));

    }

}
