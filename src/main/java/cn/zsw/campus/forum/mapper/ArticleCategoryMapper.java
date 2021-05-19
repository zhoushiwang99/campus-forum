package cn.zsw.campus.forum.mapper;

import cn.zsw.campus.forum.bean.ArticleCategory;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Entity cn.zsw.campus.forum.bean.ArticleCategory
 */
@Mapper
@Repository
public interface ArticleCategoryMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(ArticleCategory record);

    int insertSelective(ArticleCategory record);

    ArticleCategory selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ArticleCategory record);

    int updateByPrimaryKey(ArticleCategory record);

    List<ArticleCategory> getAllCategory();

}




