package cn.zsw.campus.forum.service.impl;

import cn.zsw.campus.forum.bean.Article;
import cn.zsw.campus.forum.bean.User;
import cn.zsw.campus.forum.mapper.ArticleMapper;
import cn.zsw.campus.forum.mapper.elasticsearch.ArticleRepository;
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

    @Autowired
    ArticleRepository articleRepository;


    //2021-05-18 00:00:00的时间戳
    final static int TIME_BEGIN = 1619798400;

    @Override
    public boolean publishArticle(Article article) {
        Date now = new Date();
        //计算初始分数
        int score = TIME_BEGIN / (int)(now.getTime()/1000);
        User user = hostHolder.getUser();
        article.setCreateTime(now);
        article.setCommentCount(0);
        article.setTop(false);
        article.setUserId(user.getId());
        article.setScore(score);
        articleMapper.insert(article);
        articleRepository.save(article);
        return true;
    }
}
