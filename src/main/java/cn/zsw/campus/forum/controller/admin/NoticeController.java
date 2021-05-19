package cn.zsw.campus.forum.controller.admin;

import cn.zsw.campus.forum.bean.Notice;
import cn.zsw.campus.forum.common.ReturnData;
import cn.zsw.campus.forum.mapper.NoticeMapper;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author zsw
 * @date 2021/5/18 1:31
 */
@RestController
public class NoticeController {

    @Autowired
    NoticeMapper noticeMapper;

    @GetMapping("/getNotice")
    public ReturnData getNotice() {
        Notice notice = noticeMapper.selectByPrimaryKey(1);
        return ReturnData.success(notice);
    }

    @PostMapping("/admin/addNotice")
    public ReturnData addNotice(@NotNull String content) {
        Notice notice = Notice.builder().content(content).id(1).createTime(new Date()).build();
        noticeMapper.updateByPrimaryKeySelective(notice);
        return ReturnData.success();
    }

}
