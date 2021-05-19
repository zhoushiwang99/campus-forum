package cn.zsw.campus.forum.service;

import cn.zsw.campus.forum.bean.Forbidden;
import cn.zsw.campus.forum.common.CodeEnum;
import cn.zsw.campus.forum.exception.BaseException;
import cn.zsw.campus.forum.mapper.ForbiddenMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author zsw
 * @date 2021/5/19 21:49
 */
@Service
public class ForbiddenService {

    @Autowired
    ForbiddenMapper forbiddenMapper;

    static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public void isForbidden(Integer userId) {
        Date date = new Date();
        Forbidden forbidden = forbiddenMapper.selectByUserId(userId);
        if (forbidden == null || (forbidden.getForbiddenTime().getTime() - date.getTime()) < 0) {
        } else {
            throw new BaseException(CodeEnum.FORBIDDEN_USER.getCode(), "很抱歉，您被禁言了，将在" + sdf.format(forbidden.getForbiddenTime()) + "解除禁言");
        }
    }

}
