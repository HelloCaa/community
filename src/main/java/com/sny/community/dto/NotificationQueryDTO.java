package com.sny.community.dto;

import lombok.Data;

/**
 * @Author sny
 * @CreateTime 2022-07-08  09:07
 * @Description TODO
 * @Version 1.0
 */
@Data
public class NotificationQueryDTO {
    private Integer userId;
    private Integer page;
    private Integer size;
}
