package com.symbols;

import com.lexer.Tag;
import com.lexer.Word;

public class Type extends Word {

    //存储字节长度
    public int width = 0;

    public Type(String token, int tag, int width){
        super(tag,token);
        this.width = width;
    }

    public static final Type
        Int = new Type("int", Tag.BASIC, 4),
        Float = new Type("float", Tag.BASIC, 8),
        Char = new Type("char", Tag.BASIC, 1),
        Bool = new Type("bool", Tag.BASIC, 1);

    /**
     * 判断类型是不是Char、Int、Float
     * @param type
     * @return
     */
    public static boolean numeric(Type type){
        return  type == Type.Char || type == Type.Int || type == Type.Float;
    }

    /**
     * 类型转换,当两个类型转运算时，类型采用的max返回的类型
     * @param type
     * @param tempType
     * @return
     */
    public static Type max(Type type, Type tempType){
        if(!numeric(type) || !numeric(tempType)) {
            return null;
        }else if(type == Type.Float || tempType == Type.Float){
            return Type.Float;
        }else if(type == Type.Int || tempType == Type.Int){
            return Type.Int;
        }else {
            return Type.Char;
        }
    }

    @Override
    public String toString() {
        return  "Type= {tag:"+ this.getTag()+", type:"+this.getLexeme()+", with:"+width+"}";
    }
}
