package com.sny.community.dto;

import lombok.Data;

import java.util.Objects;

/**
 * @Author sny
 * @CreateTime 2022-07-11  12:05
 * @Description TODO
 * @Version 1.0
 */
@Data
public class HotTagDTO {
    private String name;
    private Integer priority = 0;
}
