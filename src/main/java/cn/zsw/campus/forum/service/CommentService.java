package cn.zsw.campus.forum.service;

import cn.zsw.campus.forum.vo.CommentVO;

import java.util.List;

/**
 * @author zsw
 * @date 2021/5/13 21:43
 */
public interface CommentService {
    /**
     * 发布评论
     * @param articleId 帖子id
     * @param commentId 回复的评论Id
     * @param content   评论内容
     * @return
     */
    boolean addComment(Integer articleId, Integer commentId,String content);


    List<CommentVO> getCommentByArticleId(Integer articleId);

}
