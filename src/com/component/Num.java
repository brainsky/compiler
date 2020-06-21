package com.component;

public class Num extends Token {

    private final int value;

    public Num(int value){
        super(Tag.NUM);
        this.value = value;
    }

    @Override
    public String toString() {
        return "Num = { tag: "+super.getTag()+", value:"+value+"}";
    }
}
