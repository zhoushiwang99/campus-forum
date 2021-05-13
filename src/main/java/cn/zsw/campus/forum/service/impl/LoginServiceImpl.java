package cn.zsw.campus.forum.service.impl;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.zsw.campus.forum.bean.University;
import cn.zsw.campus.forum.bean.User;
import cn.zsw.campus.forum.common.CodeEnum;
import cn.zsw.campus.forum.common.Constant;
import cn.zsw.campus.forum.exception.BaseException;
import cn.zsw.campus.forum.mapper.UniversityMapper;
import cn.zsw.campus.forum.mapper.UserMapper;
import cn.zsw.campus.forum.service.JwService;
import cn.zsw.campus.forum.service.LoginService;
import cn.zsw.campus.forum.util.HostHolder;
import cn.zsw.campus.forum.util.RedisKeyUtil;
import cn.zsw.campus.forum.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author zsw
 * @date 2021/5/10 18:46
 */
@Service("loginService")
public class LoginServiceImpl implements LoginService, Constant {
    @Autowired
    UniversityMapper universityMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    HostHolder hostHolder;

    @Override
    public String login(String account, String password, String universityName) {
        University university = universityMapper.selectByUniversityName(universityName);
        if (university == null || university.getServiceName() == null) {
            throw new BaseException(CodeEnum.REQUEST_FAILED.getCode(), "该高校论坛尚未开放");
        }
        String serviceName = university.getServiceName();
        JwService jwService = SpringUtil.getBean(serviceName);
        User userFromJw = jwService.getUser(account, password);
        if (userFromJw == null) {
            throw new BaseException(CodeEnum.REQUEST_FAILED.getCode(), "用户名或密码错误");
        }
        User userFromDb = userMapper.selectByUniversityIdAndAccount(university.getId(), account);
        if (userFromDb == null) {
            userFromDb = User.builder().registerTime(new Date()).account(account)
                    .name(userFromJw.getName())
                    .className(userFromJw.getClassName())
                    .major(userFromJw.getMajor())
                    .universityId(university.getId())
                    .avatar("http://localhost:8889/img/pikapikaqiu.jpg")
                    .gender("男")
                    .signature("这个人很懒，什么都没有写哦！")
                    .build();
            userMapper.insert(userFromDb);
        }
        hostHolder.setUser(userFromDb);
        String token = RandomUtil.randomString(12);
        String idKey = RedisKeyUtil.getIdKey(userFromDb.getId());
        String tokenKey = RedisKeyUtil.getTokenKey(token);

        redisUtil.setString(idKey, token, LOGIN_EXPIRE_TIME, TimeUnit.SECONDS);
        redisUtil.setString(tokenKey, String.valueOf(userFromDb.getId()), LOGIN_EXPIRE_TIME, TimeUnit.SECONDS);
        return token;
    }
}
