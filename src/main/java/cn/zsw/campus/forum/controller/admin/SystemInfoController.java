package cn.zsw.campus.forum.controller.admin;

import cn.zsw.campus.forum.common.ReturnData;
import cn.zsw.campus.forum.mapper.ArticleMapper;
import cn.zsw.campus.forum.mapper.CommentMapper;
import cn.zsw.campus.forum.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * @author zsw
 * @date 2021/5/18 22:58
 */
@RestController
public class SystemInfoController {

    @Autowired
    ArticleMapper articleMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    CommentMapper commentMapper;

    @GetMapping("/admin/getSystemInfo")
    public ReturnData getSystemInfo() {
        int articleCount = articleMapper.selectTotalCount();
        int userCount = userMapper.selectTotalCount();
        int totalCount = commentMapper.selectTotalCount();

        HashMap<String, Object> data = new HashMap<>();
        data.put("articleCount", articleCount);
        data.put("userCount", userCount);
        data.put("totalCount", totalCount);

        return ReturnData.success(data);
    }


}
