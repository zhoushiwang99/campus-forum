package cn.zsw.campus.forum.interceptor;


import cn.zsw.campus.forum.bean.User;
import cn.zsw.campus.forum.common.Constant;
import cn.zsw.campus.forum.exception.BaseException;
import cn.zsw.campus.forum.common.CodeEnum;
import cn.zsw.campus.forum.mapper.UserMapper;
import cn.zsw.campus.forum.util.HostHolder;
import cn.zsw.campus.forum.util.IpUtil;
import cn.zsw.campus.forum.util.RedisKeyUtil;
import cn.zsw.campus.forum.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

/**
 * @author zsw
 * @date 2019/11/18 22:35
 */
@Component
public class AuthorizationInterceptor implements HandlerInterceptor, Constant {
    @Autowired
    RedisUtil redisUtil;

    @Autowired
    HostHolder hostHolder;

    @Autowired
    UserMapper userMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String ipAddr = IpUtil.getIpAddr(request);
        String token = request.getHeader("token");
        if(token == null|| "".equals(token.trim())) {
            throw new BaseException(CodeEnum.REQUEST_FAILED.getCode(), "尚未登录");
        }
        Integer userId = Integer.valueOf(redisUtil.getString(RedisKeyUtil.getTokenKey(token)));

        if ( userId == null) {
            throw new BaseException(CodeEnum.REQUEST_FAILED.getCode(), "尚未登录");
        }

        String realToken = redisUtil.getString(RedisKeyUtil.getIdKey(userId));
        if (!token.equals(realToken)) {
            throw new BaseException(CodeEnum.REQUEST_FAILED.getCode(), "登录失效");
        }
        redisUtil.setString(RedisKeyUtil.getIdKey(userId), token, LOGIN_EXPIRE_TIME, TimeUnit.SECONDS);
        redisUtil.setString(RedisKeyUtil.getTokenKey(token), String.valueOf(userId), LOGIN_EXPIRE_TIME, TimeUnit.SECONDS);

        User user = userMapper.selectByPrimaryKey(userId);
        user.setIpAddr(ipAddr);
        hostHolder.setUser(user);

        return true;
    }
}
