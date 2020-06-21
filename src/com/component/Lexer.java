package com.component;

import java.io.IOException;
import java.util.Hashtable;

/**
 * 词法分析器
 */
public class Lexer {

    //遍历行数
    private int line = 1;

    //存储输入的下一个字符
    private char peek = ' ' ;

    private Hashtable<String,Word> words = new Hashtable();

    public Lexer(){
        reserve(new Word(Tag.TRUE, "true"));
        reserve(new Word(Tag.FALSE, "false"));
    }

    private void reserve(Word word){
        words.put(word.getLexeme(), word);
    }

    public Token scan() throws IOException{

        Token result = null;

        boolean hasToken = filterCharater();

        if(hasToken){
            result = getNum();

            if(result == null){
                result = getWord();
            }

            //  result = getNum() != null ? getNum() : getWord();

            System.out.println(result);
        }

        return result;
    }


    public boolean filterCharater() throws IOException{
        boolean res ;
        StringBuffer sb = new StringBuffer();
        for (; ; peek = (char) System.in.read()) {
            System.out.print(peek);
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
     * 获取十进制数字
     * @return
     * @throws IOException
     */
    public Num getNum() throws IOException {
        if(Character.isDigit(peek)){
            int value = 0;

            do{
                // digit peek 输入的字符 radix = 进制
                value = 10*value + Character.digit(peek, 10);

                peek = (char) System.in.read();
            }while (Character.isDigit(peek));

            return new Num(value);
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
       Lexer lexer = new Lexer();

       lexer.scan();

       System.out.println("end");

    }

}
