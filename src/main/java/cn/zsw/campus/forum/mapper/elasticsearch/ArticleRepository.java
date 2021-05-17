package cn.zsw.campus.forum.mapper.elasticsearch;

import cn.zsw.campus.forum.bean.Article;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @author zsw
 * @date 2021/5/16 22:06
 */
@Repository
public interface ArticleRepository extends ElasticsearchRepository<Article,Integer> {
}
