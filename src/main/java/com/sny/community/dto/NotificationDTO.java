package com.sny.community.dto;

import com.sny.community.model.User;
import lombok.Data;

@Data
public class NotificationDTO {
    private Integer id;
    private Long gmtCreate;
    private Integer status;
    private Long notifier;
    private String notifierName;
    private String outerTitle;
    private String typeName;
    private Integer outerid;
    private Integer type;
}
