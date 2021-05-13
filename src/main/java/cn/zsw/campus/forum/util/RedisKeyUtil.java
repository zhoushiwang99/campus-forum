package cn.zsw.campus.forum.util;

/**
 * @author zsw
 * @date 2021/4/6 20:58
 */
public class RedisKeyUtil {

    private static final String USER_TOKEN_PREFIX = "user:token:";
    private static final String USER_ID_PREFIX = "user:id:";
    private static final String CAPTCHACODE_IP_PREFIX = "captcha-ip-";

    public static String getIdKey(int id) {
        return USER_ID_PREFIX + id;
    }

    public static String getTokenKey(String token) {
        return USER_TOKEN_PREFIX + token;
    }

    public static String getCaptchacodeIpKey(String ip) {
        return CAPTCHACODE_IP_PREFIX + ip;
    }

}
