package com.sny.community.mapper;

import com.sny.community.dto.NotificationQueryDTO;
import com.sny.community.model.Notification;

import java.util.List;

public interface NotificationExtMapper {
    List<Notification> selectNotification(NotificationQueryDTO notificationQueryDTO);
}
