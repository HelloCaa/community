package com.sny.community.enums;

public enum CommentTypeEnum {
    QUESTION(1),
    COMMENT(2);
    private int type;

    public int getType() {
        return type;
    }

    CommentTypeEnum(int type) {
        this.type = type;
    }
}
