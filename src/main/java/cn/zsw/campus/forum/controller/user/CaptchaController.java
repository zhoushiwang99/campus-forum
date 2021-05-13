package cn.zsw.campus.forum.controller.user;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.ShearCaptcha;
import cn.hutool.captcha.generator.MathGenerator;
import cn.zsw.campus.forum.common.Constant;
import cn.zsw.campus.forum.util.IpUtil;
import cn.zsw.campus.forum.util.RedisKeyUtil;
import cn.zsw.campus.forum.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author zsw
 * @date 2021/5/10 20:19
 */
@Controller
public class CaptchaController implements Constant {

    @Autowired
    RedisUtil redisUtil;

    @GetMapping("/getValidateCode")
    public void getValidateCode(HttpServletRequest request,HttpServletResponse response) throws IOException {
        String ipAddr = IpUtil.getIpAddr(request);
        ShearCaptcha captcha = CaptchaUtil.createShearCaptcha(200, 45, 4, 4);
        // 自定义验证码内容为四则运算方式
        captcha.setGenerator(new MathGenerator());
        // 重新生成code
        captcha.createCode();
        captcha.write(response.getOutputStream());
        String captchaCode = captcha.getCode();

        redisUtil.setString(RedisKeyUtil.getCaptchacodeIpKey(ipAddr),captchaCode,3 * 60, TimeUnit.SECONDS);
    }
}
