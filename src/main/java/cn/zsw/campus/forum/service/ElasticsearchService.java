package cn.zsw.campus.forum.service;

import cn.zsw.campus.forum.bean.Article;
import cn.zsw.campus.forum.mapper.elasticsearch.ArticleRepository;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.util.CloseableIterator;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;

/**
 * @author zsw
 * @date 2021/5/17 17:53
 */
@Service
public class ElasticsearchService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    public void saveArticle(Article article) {
        articleRepository.save(article);
    }

    public void deleteArticle(int id) {
        articleRepository.deleteById(id);
    }


    public Page<Article> searchArticle(String keyword, int pageNo, int pageSize) {
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.multiMatchQuery(keyword, "title", "content"))
                .withSort(SortBuilders.fieldSort("id").order(SortOrder.DESC))
                .withSort(SortBuilders.fieldSort("commentCount").order(SortOrder.DESC))
                .withSort(SortBuilders.fieldSort("createTime").order(SortOrder.DESC))
                //分页
                .withPageable(PageRequest.of(pageNo, pageSize))
                //字段高亮显示
                .withHighlightFields(
                        new HighlightBuilder.Field("title").preTags("<em>").postTags("</em>"),
                        new HighlightBuilder.Field("content").preTags("<em>").postTags("</em>")
                )
                .build();


        Page<Article> page = articleRepository.search(searchQuery);
        return page;
        /*System.out.println(page.getTotalElements());
        System.out.println(page.getTotalPages());
        System.out.println(page.getSize());

        for (Article article : page) {
            System.out.println(article);
        }*/
    }

    public Page<Article> searchAllArticleByScoreDesc(Integer categoryId, int pageNo, int pageSize) {
        if (categoryId == null || categoryId == 0) {
            NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
//                .withQuery(QueryBuilders.multiMatchQuery(keyword, "title", "content"))
                    .withSort(SortBuilders.fieldSort("score").order(SortOrder.DESC))
                    .withSort(SortBuilders.fieldSort("id").order(SortOrder.DESC))
                    //分页
                    .withPageable(PageRequest.of(pageNo, pageSize))
                    .build();
            Page<Article> page = articleRepository.search(searchQuery);
            return page;
        }
        else {
            NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.multiMatchQuery(categoryId, "categoryId"))
                    .withSort(SortBuilders.fieldSort("score").order(SortOrder.DESC))
                    .withSort(SortBuilders.fieldSort("id").order(SortOrder.DESC))
                    //分页
                    .withPageable(PageRequest.of(pageNo, pageSize))
                    .build();
            Page<Article> page = articleRepository.search(searchQuery);
            return page;
        }

    }
}
