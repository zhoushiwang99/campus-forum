package cn.zsw.campus.forum.controller.user;

import cn.zsw.campus.forum.bean.Article;
import cn.zsw.campus.forum.bean.ArticleCategory;
import cn.zsw.campus.forum.bean.User;
import cn.zsw.campus.forum.common.ReturnData;
import cn.zsw.campus.forum.mapper.ArticleCategoryMapper;
import cn.zsw.campus.forum.mapper.ArticleMapper;
import cn.zsw.campus.forum.mapper.UserMapper;
import cn.zsw.campus.forum.service.ArticleService;
import cn.zsw.campus.forum.util.RedisUtil;
import cn.zsw.campus.forum.vo.ArticleVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author zsw
 * @date 2021/5/12 17:50
 */
@RestController
public class ArticleController {

    @Autowired
    ArticleCategoryMapper articleCategoryMapper;

    @Autowired
    ArticleService articleService;

    @Autowired
    ArticleMapper articleMapper;

    @Autowired
    UserMapper userMapper;

    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    @GetMapping("/getAllCategory")
    public ReturnData getAllCategory() {
        List<ArticleCategory> categoryList = articleCategoryMapper.getAllCategory();
        return ReturnData.success(categoryList);
    }


    @GetMapping("/getArticleById")
    public ReturnData getArticleById( Integer articleId) {
        Article article = articleMapper.selectByPrimaryKey(articleId);
        User author = userMapper.selectByPrimaryKey(article.getUserId());
        String category = articleCategoryMapper.selectByPrimaryKey(article.getCategoryId()).getName();
        ArticleVO articleVO = ArticleVO.builder().article(article).user(author).createTime(sdf.format(article.getCreateTime())).category(category).build();
        return ReturnData.success(articleVO);
    }



    @PostMapping("/addArticle")
    public ReturnData addArticle(Integer categoryId, String title, String content) {
        Article article = Article.builder().categoryId(categoryId).title(title).content(content).build();
        articleService.publishArticle(article);
        return ReturnData.success();
    }

    @GetMapping("/getArticleNum")
    public ReturnData getArticleNumByCategoryId(Integer categoryId) {
        if (categoryId == null) {
            int total = articleMapper.selectTotalCount();
            return ReturnData.success(total);
        } else {
            int total = articleMapper.selectCountByCategoryId(categoryId);
            return ReturnData.success(total);
        }
    }

    @GetMapping("/getArticleByTime")
    public ReturnData getArticle(Integer categoryId, @RequestParam(defaultValue = "1") int pageNo, @RequestParam(defaultValue = "5") int pageSize,@RequestParam(defaultValue = "0") int sort) {
        String orderBy="";
        if(sort == 0) {
            orderBy = "id desc";
        }else {
            orderBy="id asc";
        }
        PageHelper.startPage(pageNo, pageSize,orderBy);
        List<Article> articleList;
        if (categoryId == null) {
            articleList = articleMapper.selectAllArticle();
        } else {
            articleList = articleMapper.selectAllByCategoryId(categoryId);
        }
        LinkedList<ArticleVO> articleVOList = new LinkedList<>();
        if (articleList.size() != 0) {
            for (Article article : articleList) {
                User user = userMapper.selectByPrimaryKey(article.getUserId());
                String category = articleCategoryMapper.selectByPrimaryKey(article.getCategoryId()).getName();
                ArticleVO articleVO = ArticleVO.builder().article(article).user(user).createTime(sdf.format(article.getCreateTime())).category(category).build();
                articleVOList.add(articleVO);
            }
        }
        PageInfo<Article> pageInfo = new PageInfo<>(articleList);
        Map<String,Object> data = new HashMap<>();
        data.put("article",articleVOList);
        data.put("total",pageInfo.getTotal());
        return ReturnData.success(data);
    }


}
