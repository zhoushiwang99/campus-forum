package cn.zsw.campus.forum.bean;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Comment implements Serializable {
    /**
     * 评论id
     */
    private Integer id;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 评论时间
     */
    private Date createTime;

    /**
     * 对应的帖子id
     */
    private Integer articleId;

    /**
     * 对应的评论id
     */
    private Integer commentId;

    /**
     * 评论者id
     */
    private Integer authorId;

    private static final long serialVersionUID = 1L;
}