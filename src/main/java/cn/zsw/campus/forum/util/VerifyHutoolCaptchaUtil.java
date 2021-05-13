package cn.zsw.campus.forum.util;

import cn.hutool.captcha.generator.MathGenerator;

/**
 * @author zsw
 * @date 2021/5/10 21:00
 */
public class VerifyHutoolCaptchaUtil {

    private static MathGenerator mathGenerator = new MathGenerator();

    public static boolean verifyCaptchaCode(String code, String inputCode) {
        return mathGenerator.verify(code, inputCode);
    }

}
