package com.sny.community.provider;

import com.alibaba.fastjson.JSON;
import com.sny.community.dto.AccessTokenDTO;
import com.sny.community.dto.GitHubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GithubProvider {

    //通过okHttp向github发送获得令牌的请求
    public String getAccessToken(AccessTokenDTO accessTokenDTO){
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();

            RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
            Request request = new Request.Builder()
                    .url("https://github.com/login/oauth/access_token")
                    .post(body)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                //获得的访问令牌
                String token = response.body().string()
                        .split("&")[0]
                        .split("=")[1];
                return token;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
    }

    public GitHubUser getUser(String accessToken){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user")
                .addHeader("Authorization","token "+accessToken)
                .build();

        try {
            Response response = client.newCall(request).execute();
            String str = response.body().string();
            //将github返回给我们的用户信息通过JSON这个类转换为我们的GitHubUser对象
            GitHubUser gitHubUser = JSON.parseObject(str, GitHubUser.class);
            return gitHubUser;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
