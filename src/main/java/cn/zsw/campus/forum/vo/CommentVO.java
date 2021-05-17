package cn.zsw.campus.forum.vo;

import cn.zsw.campus.forum.bean.Comment;
import cn.zsw.campus.forum.bean.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author zsw
 * @date 2021/5/13 21:54
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentVO {

    private Comment comment;

    /**
     * 回复的对应用户
     */
    private User targetUser;

    /**
     * 发表评论的用户
     */
    private User fromUser;

    private String createTime;


    private static final long serialVersionUID = 1L;
}
