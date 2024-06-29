package com.astuba.apiclientsdk.client;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.astuba.apiclientsdk.model.User;
import com.astuba.apiclientsdk.utils.MD5Utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * 调用第三方接口的客户端
 */
public class ApiClient {

    private static final String GATEWAY_HOST = "http://localhost:8090";
    private String accessKey;
    private String secretKey;

    public ApiClient(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    public String getNameByGet(String name)
    {
        HashMap<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("name", name);
        return HttpUtil.get(GATEWAY_HOST + "/api/name/", paramsMap);
    }

    public String getNameByPost(String name)
    {
        HashMap<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("name", name);
        return HttpUtil.post(GATEWAY_HOST + "/api/name/", paramsMap);
    }

    public String getUserNameByPost(User user)
    {
        String json = JSONUtil.toJsonStr(user);
        HttpResponse response = HttpRequest.post(GATEWAY_HOST + "/api/name/user")
                .charset(StandardCharsets.UTF_8)
                .addHeaders(getHeaderMap(json))
                .body(json)
                .execute();
        return response.body();
    }

    private Map<String, String> getHeaderMap(String body)
    {
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("accessKey", accessKey);
        // 一定不能直接发送
//        hashMap.put("secretKey", secretKey);
        String encode = "";
        try {
            encode = URLEncoder.encode(body, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        hashMap.put("body", encode);
        hashMap.put("nonce", RandomUtil.randomNumbers(10));
        hashMap.put("timestamp", String.valueOf(System.currentTimeMillis()));
        hashMap.put("sign", MD5Utils.genSign(hashMap, secretKey));
        return hashMap;
    }


}
