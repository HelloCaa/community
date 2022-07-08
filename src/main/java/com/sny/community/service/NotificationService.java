package com.sny.community.service;

import com.sny.community.dto.NotificationDTO;
import com.sny.community.dto.NotificationQueryDTO;
import com.sny.community.dto.PaginationDTO;
import com.sny.community.enums.NotificationStatusEnum;
import com.sny.community.enums.NotificationTypeEnum;
import com.sny.community.exception.CustomizeErrorCode;
import com.sny.community.exception.CustomizeException;
import com.sny.community.mapper.NotificationExtMapper;
import com.sny.community.mapper.NotificationMapper;
import com.sny.community.model.Notification;
import com.sny.community.model.NotificationExample;
import com.sny.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {

    @Resource
    NotificationMapper notificationMapper;

    @Resource
    NotificationExtMapper notificationExtMapper;

    public PaginationDTO<NotificationDTO> list(Integer userId, Integer page, Integer size) {
        PaginationDTO<NotificationDTO> paginationDTO = new PaginationDTO<>();
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria().andReceiverEqualTo(userId);
        Integer totalCount = (int) notificationMapper.countByExample(notificationExample);
        paginationDTO.setPagination(totalCount, size, page);
        //对page进行有效性验证
        if (page < 1) {
            page = 1;
        }
        if (page > paginationDTO.getTotalPage()) {
            page = paginationDTO.getTotalPage();
        }

        //page = size * (page - 1);
        int offset = size * (page - 1);

        //调用dao层获取数据
        NotificationQueryDTO notificationQueryDTO = new NotificationQueryDTO();
        notificationQueryDTO.setUserId(userId);
        notificationQueryDTO.setPage(offset);
        notificationQueryDTO.setSize(size);
        List<Notification> notifications = notificationExtMapper.selectNotification(notificationQueryDTO);
//        NotificationExample example = new NotificationExample();
//        example.createCriteria().andReceiverEqualTo(userId);
//        List<Notification> notifications = notificationMapper.selectByExample(example);

        if (notifications.size() == 0) {
            return paginationDTO;
        }

        List<NotificationDTO> notificationDTOS = new ArrayList<>();
        for (Notification notification : notifications) {
            NotificationDTO notificationDTO = new NotificationDTO();
            BeanUtils.copyProperties(notification, notificationDTO);
            notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
            notificationDTOS.add(notificationDTO);
        }
        paginationDTO.setData(notificationDTOS);
        return paginationDTO;
    }

    public Long unreadCount(Integer userId) {
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria().andReceiverEqualTo(userId).andStatusEqualTo(NotificationStatusEnum.UNREAD.getStatus());
        return notificationMapper.countByExample(notificationExample);
    }

    public NotificationDTO read(Integer id, User user) {
        Notification notification = notificationMapper.selectByPrimaryKey(id);
        if (notification == null) {
            throw new CustomizeException(CustomizeErrorCode.NOTIFICATION_NOT_FOUND);
        }
        if (notification.getReceiver() != user.getId()) {
            throw new CustomizeException(CustomizeErrorCode.READ_NOTIFICATION_FAIL);
        }
        notification.setStatus(NotificationStatusEnum.READ.getStatus());
        notificationMapper.updateByPrimaryKey(notification);
        NotificationDTO notificationDTO = new NotificationDTO();
        BeanUtils.copyProperties(notification, notificationDTO);
        notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
        return notificationDTO;
    }
}
