package com.sny.community.config;

import com.sny.community.dto.HotTagDTO;

import java.util.*;

/**
 * @Author sny
 * @CreateTime 2022-07-11  11:26
 * @Description TODO
 * @Version 1.0
 */
public class HotTagCache {
    static final int MAX = 3;
    private static final Map<String, HotTagDTO> TAGS = new HashMap<>();
    private static final PriorityQueue<HotTagDTO> PRIORITY = new PriorityQueue<>(MAX, Comparator.comparing(HotTagDTO::getPriority));

    public static synchronized Map<String, HotTagDTO> getTags() {
        return TAGS;
    }

    public static synchronized PriorityQueue<HotTagDTO> getPriority(){
        return PRIORITY;
    }

    public static int getMax(){
        return MAX;
    }

}
