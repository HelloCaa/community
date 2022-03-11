package com.sny.community.model;

import lombok.Data;

//数据库信息对象类
@Data //使用lombok简化set与get
public class User {
    private Integer id;
    private String name;
    private String accountId;
    private String token;
    private Long gmtCreate;
    private Long gmtModified;
    private String avatarUrl;

}
