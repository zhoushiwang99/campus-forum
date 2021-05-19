package cn.zsw.campus.forum.controller.admin;

import cn.zsw.campus.forum.bean.ArticleCategory;
import cn.zsw.campus.forum.common.ReturnData;
import cn.zsw.campus.forum.mapper.ArticleCategoryMapper;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author zsw
 * @date 2021/5/19 0:26
 */
@RestController
public class ArticleCategoryController {

    @Autowired
    ArticleCategoryMapper articleCategoryMapper;

    @GetMapping("/deleteCategory")
    public ReturnData deleteArticleCategory(@NotNull Integer categoryId) {
        articleCategoryMapper.deleteByPrimaryKey(categoryId);
        return ReturnData.success();
    }
    @PostMapping("/updateCategory")
    public ReturnData updateCategory(@NotNull Integer categoryId,@NotNull String name) {
        ArticleCategory articleCategory = ArticleCategory.builder().id(categoryId).createTime(new Date()).name(name).build();
        articleCategoryMapper.updateByPrimaryKey(articleCategory);
        return ReturnData.success();
    }

    @PostMapping("/addCategory")
    public ReturnData addCategory(@NotNull String name) {
        ArticleCategory articleCategory = ArticleCategory.builder().createTime(new Date()).name(name).build();
        articleCategoryMapper.insert(articleCategory);
        return ReturnData.success();
    }

}
