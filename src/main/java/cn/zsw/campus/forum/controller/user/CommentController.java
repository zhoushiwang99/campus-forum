package cn.zsw.campus.forum.controller.user;

import cn.zsw.campus.forum.bean.Article;
import cn.zsw.campus.forum.bean.Comment;
import cn.zsw.campus.forum.bean.User;
import cn.zsw.campus.forum.common.CodeEnum;
import cn.zsw.campus.forum.common.ReturnData;
import cn.zsw.campus.forum.exception.BaseException;
import cn.zsw.campus.forum.mapper.ArticleMapper;
import cn.zsw.campus.forum.mapper.CommentMapper;
import cn.zsw.campus.forum.mapper.elasticsearch.ArticleRepository;
import cn.zsw.campus.forum.service.CommentService;
import cn.zsw.campus.forum.service.ForbiddenService;
import cn.zsw.campus.forum.util.BadWordUtil;
import cn.zsw.campus.forum.util.HostHolder;
import cn.zsw.campus.forum.vo.CommentVO;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

/**
 * @author zsw
 * @date 2021/5/13 21:37
 */
@RestController
public class CommentController {

    @Autowired
    CommentService commentService;

    @Autowired
    CommentMapper commentMapper;
    @Autowired
    ArticleMapper articleMapper;

    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    HostHolder hostHolder;

    @Autowired
    ForbiddenService forbiddenService;

    @PostMapping("/addComment")
    public ReturnData addComment(@NotNull Integer articleId, Integer commentId, @NotNull String content) {
        User user = hostHolder.getUser();
        forbiddenService.isForbidden(user.getId());

        Set<String> set = BadWordUtil.getBadWord(content, 2);
        if(set.size() != 0) {
            throw new BaseException(CodeEnum.SENSITIVE_WORD_FORBIDDEN.getCode(), "很抱歉，您发布的内容包含敏感词：" + set);
        }

        Article article = articleMapper.selectByPrimaryKey(articleId);
        //一条评论加10分
        article.setScore(article.getScore() + 10);
        articleMapper.updateByPrimaryKeySelective(article);
        commentService.addComment(articleId, commentId, content);
        articleRepository.save(article);
        return ReturnData.success();
    }

    @GetMapping("/getCommentByArticleId")
    public ReturnData getCommentByArticleId(@NotNull Integer articleId) {
        List<CommentVO> commentVOList = commentService.getCommentByArticleId(articleId);
        return ReturnData.success(commentVOList);
    }

    @GetMapping("/deleteCommentByCommentId")
    public ReturnData deleteCommentByCommentId(@NotNull Integer commentId) {
        Comment comment = commentMapper.selectByPrimaryKey(commentId);
        Article article = articleMapper.selectByPrimaryKey(comment.getArticleId());
        article.setScore(article.getScore() - 8);
        articleMapper.updateByPrimaryKey(article);
        articleRepository.save(article);
        commentMapper.deleteByPrimaryKey(commentId);
        return ReturnData.success();
    }

}
