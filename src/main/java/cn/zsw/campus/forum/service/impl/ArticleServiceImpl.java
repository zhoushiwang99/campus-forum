package cn.zsw.campus.forum.service.impl;

import cn.zsw.campus.forum.bean.Article;
import cn.zsw.campus.forum.bean.User;
import cn.zsw.campus.forum.mapper.ArticleMapper;
import cn.zsw.campus.forum.service.ArticleService;
import cn.zsw.campus.forum.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author zsw
 * @date 2021/5/12 18:34
 */
@Service("articleService")
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    ArticleMapper articleMapper;

    @Autowired
    HostHolder hostHolder;

    @Override
    public boolean publishArticle(Article article) {
        User user = hostHolder.getUser();
        article.setCreateTime(new Date());
        article.setCommentCount(0);
        article.setTop(false);
        article.setUserId(user.getId());
        articleMapper.insert(article);
        return true;
    }
}
