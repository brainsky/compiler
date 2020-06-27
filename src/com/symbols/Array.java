package com.symbols;

import com.lexer.Tag;

/**
 * 数组
 */
public class Array extends Type {

    //元素类型
    public Type elementType;

    //元素个数
    public int size = 1;

    public Array(int size, Type type){
        super("[]", Tag.INDEX, size * type.width);
        this.size = size;
        elementType = type;
    }

    @Override
    public String toString() {
        return "[" + size + "]" + elementType.toString();
    }
}
