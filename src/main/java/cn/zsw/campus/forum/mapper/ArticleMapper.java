package cn.zsw.campus.forum.mapper;

import cn.zsw.campus.forum.bean.Article;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@Mapper
public interface ArticleMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Article record);

    int insertSelective(Article record);

    Article selectByPrimaryKey(Integer id);

    List<Article> selectAllArticle();

    List<Article> selectAllByCategoryId(Integer categoryId);

    List<Article> selectByUserId(Integer userId);


    List<Article> selectByKeyWord(String keyword);


    int updateByPrimaryKeySelective(Article record);

    int updateByPrimaryKey(Article record);

    int selectCountByCategoryId(Integer categoryId);

    int selectTotalCount();


}




