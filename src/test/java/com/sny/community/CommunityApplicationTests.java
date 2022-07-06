package com.sny.community;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

@SpringBootTest
class CommunityApplicationTests {

    @Test
    void contextLoads() {
        String json = "{\"cId\": 10004,\"apTime\": 1657326600000,\"docName\": \"陈萍\",\"dName\": \"全科医学科门诊\",\"cName\": \"全科医学科\",\"apCost\": 35,\"sId\": 3}";
        JSONObject jsonObject = JSON.parseObject(json);
        System.out.println(jsonObject.get("cId"));
        System.out.println(jsonObject.get("dName"));
    }

}
