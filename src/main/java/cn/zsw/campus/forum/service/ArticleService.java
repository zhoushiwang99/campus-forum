package cn.zsw.campus.forum.service;

import cn.zsw.campus.forum.bean.Article;

/**
 * @author zsw
 * @date 2021/5/12 18:33
 */
public interface ArticleService {
    /**
     * 发布帖子
     * @param article
     * @return
     */
    boolean publishArticle(Article article);
}
