package cn.zsw.campus.forum.controller.user;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.ShearCaptcha;
import cn.hutool.captcha.generator.MathGenerator;
import cn.hutool.http.HttpStatus;
import cn.zsw.campus.forum.common.CodeEnum;
import cn.zsw.campus.forum.common.ReturnData;
import cn.zsw.campus.forum.mapper.UserMapper;
import cn.zsw.campus.forum.service.LoginService;
import cn.zsw.campus.forum.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.net.util.IPAddressUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zsw
 * @date 2021/5/10 18:23
 */
@RestController
public class AccountController {

    @Autowired
    LoginService loginService;

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    HostHolder hostHolder;

    @Autowired
    UserMapper userMapper;


    @PostMapping("/login")
    public ReturnData login(HttpServletRequest request, String account, String password, String universityName, String inputValidateCode) {

        String ipAddr = IpUtil.getIpAddr(request);
        System.out.println(account);
        System.out.println(inputValidateCode);
        // 从redis获取验证码
        String captchaCode = redisUtil.getString(RedisKeyUtil.getCaptchacodeIpKey(ipAddr));
        if (!VerifyHutoolCaptchaUtil.verifyCaptchaCode(captchaCode, inputValidateCode)) {
            return ReturnData.fail(CodeEnum.REQUEST_FAILED.getCode(), "验证码错误");
        }
        String token = loginService.login(account, password, universityName);

        Map<String, Object> data = new HashMap<>();
        data.put("token", token);
        data.put("user", hostHolder.getUser());
        return ReturnData.success(data);
    }


}
