package com.astuba.apiclientsdk.utils;

import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;

import java.util.Map;

public class MD5Utils {

    /**
     * 生成签名
     */
    public static String genSign(Map<String, String> hashMap, String secretKey)
    {
        Digester digester = new Digester(DigestAlgorithm.SHA256);
        String content = hashMap.toString() + "." + secretKey;
        return digester.digestHex(content);
    }
}
