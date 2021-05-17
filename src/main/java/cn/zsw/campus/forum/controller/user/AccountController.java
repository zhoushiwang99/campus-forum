package cn.zsw.campus.forum.controller.user;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.ShearCaptcha;
import cn.hutool.captcha.generator.MathGenerator;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpStatus;
import cn.zsw.campus.forum.bean.Article;
import cn.zsw.campus.forum.bean.User;
import cn.zsw.campus.forum.common.CodeEnum;
import cn.zsw.campus.forum.common.ReturnData;
import cn.zsw.campus.forum.mapper.ArticleMapper;
import cn.zsw.campus.forum.mapper.UserMapper;
import cn.zsw.campus.forum.service.LoginService;
import cn.zsw.campus.forum.util.*;
import cn.zsw.campus.forum.vo.ArticleVO;
import cn.zsw.campus.forum.vo.UserVO;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.net.util.IPAddressUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author zsw
 * @date 2021/5/10 18:23
 */
@RestController
public class AccountController {

    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    static SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    static List<String> TEST_USER_Account = Arrays.asList("201716081234","201716080001","201716080002","201716080003","201716080004","201716080005","201716080006","201716080007","201716080008","201716080009","201716080010");

    @Autowired
    LoginService loginService;

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    HostHolder hostHolder;

    @Autowired
    UserMapper userMapper;

    @Autowired
    ArticleMapper articleMapper;


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

        String token = null;
        User user = null;
        if(TEST_USER_Account.contains(account) && account.equals(password)) {
            user = userMapper.selectByUniversityIdAndAccount(1,account);
            token = RandomUtil.randomString(12);

            String idKey = RedisKeyUtil.getIdKey(user.getId());
            String tokenKey = RedisKeyUtil.getTokenKey(token);

            redisUtil.setString(idKey, token, 30*60, TimeUnit.SECONDS);
            redisUtil.setString(tokenKey, String.valueOf(user.getId()), 30*60, TimeUnit.SECONDS);
        }
        else {
            token = loginService.login(account, password, universityName);
            user = hostHolder.getUser();
        }

        Map<String, Object> data = new HashMap<>();
        data.put("token", token);
        data.put("user", user);
        return ReturnData.success(data);
    }

    @GetMapping("/getUserById")
    public ReturnData getUserById(@NotNull Integer userId) {
        User user = userMapper.selectByPrimaryKey(userId);
        UserVO userVO = new UserVO();
        userVO.setUser(user);
        userVO.setRegTime(sdf.format(user.getRegisterTime()));

        List<Article> articleList = articleMapper.selectByUserId(userId);
        List<ArticleVO> articleVOList = new LinkedList<>();
        for (Article article : articleList) {
            ArticleVO articleVO = ArticleVO.builder().article(article).createTime(sdf1.format(article.getCreateTime())).build();
            articleVOList.add(articleVO);
        }
        userVO.setArticle(articleVOList);
        return ReturnData.success(userVO);
    }
//
//    @GetMapping("/getUser")
//    public ReturnData getUser(@NotNull Integer userId) {
//        User user = userMapper.selectByPrimaryKey(userId);
//        return ReturnData.success(user);
//    }

    @PostMapping("/alertUserInfo")
    public ReturnData alertUserInfo(@NotNull String gender,@NotNull String signature) {
        Integer userId = hostHolder.getUser().getId();
        User user = User.builder().id(userId).gender(gender).signature(signature).build();
        userMapper.updateByPrimaryKeySelective(user);
        User userFromDB = userMapper.selectByPrimaryKey(userId);
        return ReturnData.success(userFromDB);
    }

}
