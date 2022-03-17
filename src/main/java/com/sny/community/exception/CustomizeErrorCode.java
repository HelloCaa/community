package com.sny.community.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode{

    QUESTION_NOT_FOUND(2001, "你找的问题不在了，要不要换个试试～"),
    TARGET_PARAM_NOT_FOUND(2002, "未选中任何问题或评论进行回复。"),
    NO_LOGIN(2003, "当前未登陆，请先登陆。"),
    SYSTEM_ERROR(2004, "当前未登陆，请先登陆。");
    private Integer code;
    private String message;

    CustomizeErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }
}
