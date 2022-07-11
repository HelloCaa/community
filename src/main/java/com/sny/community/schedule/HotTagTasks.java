package com.sny.community.schedule;

import com.sny.community.cache.TagCache;
import com.sny.community.config.HotTagCache;
import com.sny.community.dto.HotTagDTO;
import com.sny.community.mapper.QuestionMapper;
import com.sny.community.model.Question;
import com.sny.community.model.QuestionExample;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Author sny
 * @CreateTime 2022-07-08  10:33
 * @Description TODO
 * @Version 1.0
 */
@Component
@Slf4j
public class HotTagTasks {

    @Resource
    QuestionMapper questionMapper;

    //    @Scheduled(cron = "0 0 6,19 * * *")
    @Scheduled(fixedRate = 1000 * 60 * 60 * 2)
    public void hotTagSchedule() {
        int offSet = 0;
        int limit = 5;
        log.info("HotSchedule start {}", new Date());
        List<Question> list = new LinkedList<>();
        // 初始化
        Map<String, HotTagDTO> priorities = HotTagCache.getTags();
        PriorityQueue<HotTagDTO> priorityQueue = HotTagCache.getPriority();
        priorities.clear();
        priorityQueue.clear();
        while (offSet == 0 || list.size() == limit) {
            list = questionMapper.selectByExampleWithRowbounds(new QuestionExample(), new RowBounds(offSet, limit));
            for (Question question : list) {
                String[] tags = StringUtils.split(question.getTag(), ",");
                for (String tag : tags) {
                    HotTagDTO tagDTO = priorities.getOrDefault(tag, new HotTagDTO());
                    tagDTO.setName(tag);
                    int curPriority = tagDTO.getPriority() + 5 + question.getCommentCount();
                    tagDTO.setPriority(curPriority);
                    priorities.put(tag, tagDTO);
                    if (priorityQueue.contains(tagDTO)) {
                        priorityQueue.remove(tagDTO);
                        priorityQueue.add(tagDTO);
                    } else if (priorityQueue.size() == HotTagCache.getMax() && priorityQueue.peek().getPriority() < curPriority) {
                        priorityQueue.poll();
                        priorityQueue.add(tagDTO);
                    } else if (priorityQueue.size() < HotTagCache.getMax()) {
                        priorityQueue.add(tagDTO);
                    }
                }
            }
            offSet += limit;
        }
        LinkedList<String> hots = new LinkedList<>();
        while (priorityQueue.size() != 0) {
            hots.addFirst(priorityQueue.poll().getName());
        }
        HotTagCache.setTopN(hots);
        log.info("HotSchedule stop {}", new Date());
    }
}
