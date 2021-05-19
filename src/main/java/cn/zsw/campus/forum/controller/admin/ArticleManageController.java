package cn.zsw.campus.forum.controller.admin;

import cn.zsw.campus.forum.bean.Article;
import cn.zsw.campus.forum.bean.User;
import cn.zsw.campus.forum.common.ReturnData;
import cn.zsw.campus.forum.mapper.ArticleCategoryMapper;
import cn.zsw.campus.forum.mapper.ArticleMapper;
import cn.zsw.campus.forum.mapper.UserMapper;
import cn.zsw.campus.forum.mapper.elasticsearch.ArticleRepository;
import cn.zsw.campus.forum.vo.ArticleVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author zsw
 * @date 2021/5/19 14:45
 */
@RestController
public class ArticleManageController {

    @Autowired
    ArticleMapper articleMapper;

    @Autowired
    ArticleCategoryMapper articleCategoryMapper;

    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    UserMapper userMapper;

    static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @GetMapping("/admin/setTopArticle")
    public ReturnData topArticle(@NotNull Integer articleId) {
        Article article = articleMapper.selectByPrimaryKey(articleId);
        if(article != null) {
            article.setPriority(1);
            articleRepository.save(article);
            articleMapper.updateByPrimaryKeySelective(article);
        }
        return ReturnData.success();
    }

    @GetMapping("/admin/canCelTopArticle")
    public ReturnData canCelTopArticle(@NotNull Integer articleId) {
        Article article = articleMapper.selectByPrimaryKey(articleId);
        if(article != null) {
            article.setPriority(0);
            articleRepository.save(article);
            articleMapper.updateByPrimaryKeySelective(article);
        }
        return ReturnData.success();
    }

    @GetMapping("/admin/deleteArticle")
    public ReturnData deleteArticle(@NotNull Integer articleId) {
        articleMapper.deleteByPrimaryKey(articleId);
        articleRepository.deleteById(articleId);
        return ReturnData.success();
    }


    @GetMapping("/admin/getArticle")
    public ReturnData getArticle(String keyword, @RequestParam(defaultValue = "1") int pageNo, @RequestParam(defaultValue = "5") int pageSize,@RequestParam(defaultValue = "0")int sort) {
        String orderBy;
        if(sort == 0) {
            orderBy = "priority desc,id asc";
        }else {
            orderBy = "priority desc,id desc";
        }

        PageHelper.startPage(pageNo, pageSize, orderBy);
        List<Article> articleList;
        if (keyword == null) {
            articleList = articleMapper.selectAllArticle();
        } else {
            articleList = articleMapper.selectByKeyWord(keyword);
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
        Map<String, Object> data = new HashMap<>();
        data.put("article", articleVOList);
        data.put("total", pageInfo.getTotal());
        return ReturnData.success(data);
    }
}
