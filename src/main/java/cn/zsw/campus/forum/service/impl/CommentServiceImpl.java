package cn.zsw.campus.forum.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.bean.copier.ValueProvider;
import cn.zsw.campus.forum.bean.Article;
import cn.zsw.campus.forum.bean.Comment;
import cn.zsw.campus.forum.bean.User;
import cn.zsw.campus.forum.common.ReturnData;
import cn.zsw.campus.forum.mapper.ArticleMapper;
import cn.zsw.campus.forum.mapper.CommentMapper;
import cn.zsw.campus.forum.mapper.UserMapper;
import cn.zsw.campus.forum.service.CommentService;
import cn.zsw.campus.forum.util.HostHolder;
import cn.zsw.campus.forum.vo.CommentVO;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @author zsw
 * @date 2021/5/13 21:44
 */
@Service("commentService")
public class CommentServiceImpl implements CommentService {

    @Autowired
    ArticleMapper articleMapper;

    @Autowired
    CommentMapper commentMapper;


    @Autowired
    UserMapper userMapper;

    @Autowired
    HostHolder hostHolder;

    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    @Override
    public boolean addComment(Integer articleId, Integer commentId, String content) {

        User user = hostHolder.getUser();
        commentMapper.insert(Comment.builder().articleId(articleId).commentId(commentId).authorId(user.getId()).content(content).createTime(new Date()).build());
        Article article = articleMapper.selectByPrimaryKey(articleId);
        Integer commentCount = article.getCommentCount();
        articleMapper.updateByPrimaryKeySelective(Article.builder().id(articleId).commentCount(commentCount + 1).build());
        return true;
    }

    @Override
    public List<CommentVO> getCommentByArticleId(Integer articleId) {
        List<Comment> comments = commentMapper.selectByArticleId(articleId);
        List<CommentVO> commentVOList = new LinkedList<>();
        for (Comment comment : comments) {
            User targetUser = null;
            User fromUser;
            if (comment.getCommentId() != null) {
                Comment targetComment = commentMapper.selectByPrimaryKey(comment.getCommentId());
                targetUser = userMapper.selectByPrimaryKey(targetComment.getAuthorId());
            }
            fromUser = userMapper.selectByPrimaryKey(comment.getAuthorId());
            CommentVO commentVO = CommentVO.builder()
                    .comment(comment)
                    .fromUser(fromUser)
                    .targetUser(targetUser)
                    .createTime(sdf.format(comment.getCreateTime()))
                    .build();
            commentVOList.add(commentVO);
        }
        return commentVOList;
    }


}
