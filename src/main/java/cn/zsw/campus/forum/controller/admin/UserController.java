package cn.zsw.campus.forum.controller.admin;

import cn.zsw.campus.forum.bean.Forbidden;
import cn.zsw.campus.forum.bean.University;
import cn.zsw.campus.forum.bean.User;
import cn.zsw.campus.forum.common.ReturnData;
import cn.zsw.campus.forum.mapper.ForbiddenMapper;
import cn.zsw.campus.forum.mapper.UniversityMapper;
import cn.zsw.campus.forum.mapper.UserMapper;
import cn.zsw.campus.forum.vo.UserManageInfoVo;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @author zsw
 * @date 2021/5/19 16:39
 */
@RestController
public class UserController {

    @Autowired
    UserMapper userMapper;

    @Autowired
    ForbiddenMapper forbiddenMapper;

    @Autowired
    UniversityMapper universityMapper;


    static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    @GetMapping("/admin/getUserList")
    public ReturnData getUserList(String keyword, @RequestParam(defaultValue = "1") int pageNo, @RequestParam(defaultValue = "5") int pageSize) {
        List<User> users;
        if (keyword != null && !"".equals(keyword)) {
            users = userMapper.selectUserByKeyword(keyword);
        } else {
            PageHelper.startPage(pageNo, pageSize, "id asc");
            users = userMapper.selectAllUser();
        }

        Date date = new Date();
        LinkedList<UserManageInfoVo> userManageInfoVos = new LinkedList<UserManageInfoVo>();
        for (User user : users) {
            UserManageInfoVo userManageInfoVo = new UserManageInfoVo();
            Forbidden forbidden = forbiddenMapper.selectByUserId(user.getId());
            if (forbidden == null || (forbidden.getForbiddenTime().getTime() - date.getTime()) < 0) {
                userManageInfoVo.setForbidden(0);
            } else {
                userManageInfoVo.setForbidden(1);
                userManageInfoVo.setForbiddenTime(forbidden.getForbiddenTime());
            }
            userManageInfoVo.setUser(user);
            University university = universityMapper.selectByPrimaryKey(user.getUniversityId());
            userManageInfoVo.setUniversityName(university.getUniversityName());

            userManageInfoVos.add(userManageInfoVo);
        }
        return ReturnData.success(userManageInfoVos);
    }

}
