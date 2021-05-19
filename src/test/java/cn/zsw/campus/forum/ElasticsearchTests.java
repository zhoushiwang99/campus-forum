package cn.zsw.campus.forum;

import cn.zsw.campus.forum.bean.Article;
import cn.zsw.campus.forum.mapper.ArticleMapper;
import cn.zsw.campus.forum.mapper.elasticsearch.ArticleRepository;
import cn.zsw.campus.forum.service.ElasticsearchService;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

/**
 * @author zsw
 * @date 2021/5/16 22:08
 */
@SpringBootTest
@ContextConfiguration(classes = ForumApplication.class)
public class ElasticsearchTests {
    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    ElasticsearchService elasticsearchService;

//    @Autowired
//    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;


    @Test
    public void testInsert() {
//        articleRepository.save(articleMapper.selectByPrimaryKey(1));
//        articleRepository.save(articleMapper.selectByPrimaryKey(2));
//        articleRepository.save(articleMapper.selectByPrimaryKey(19));
//        for (int i = 20; i <= 20; i++) {
//            articleRepository.save(articleMapper.selectByPrimaryKey(i));
//        }
        articleRepository.saveAll(articleMapper.selectAllArticle());

    }

    @Test
    public void testInsertList() {
        //Elasticsearch批量保存
//        articleRepository.saveAll();

        Page<Article> articles = elasticsearchService.searchArticle(" ", 0, 5);
        System.out.println(articles);
    }

    @Test
    public void testUpdate() {
        articleRepository.save(articleMapper.selectByPrimaryKey(19));
    }

    @Test
    public void testDelete() {
//        articleRepository.deleteById(7);
        articleRepository.deleteAll();
    }

    @Test
    public void testSearchByRepository() {
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.multiMatchQuery("日常","title","content"))
                .withSort(SortBuilders.fieldSort("commentCount").order(SortOrder.DESC))
                .withSort(SortBuilders.fieldSort("createTime").order(SortOrder.DESC))
                .withSort(SortBuilders.fieldSort("id").order(SortOrder.DESC))
                //分页
                .withPageable(PageRequest.of(0,10))
                //字段高亮显示
                .withHighlightFields(
                        new HighlightBuilder.Field("title").preTags("<em>").postTags("</em>"),
                        new HighlightBuilder.Field("content").preTags("<em>").postTags("</em>")
                )
                .build();



        Page<Article> page = articleRepository.search(searchQuery);
        System.out.println(page.getTotalElements());
        System.out.println(page.getTotalPages());
        System.out.println(page.getSize());

        for (Article article : page) {
            System.out.println(article);
        }
    }

    @Test
    public void testSearchByTemplate() {
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.multiMatchQuery("日常","title","content"))
                .withSort(SortBuilders.fieldSort("commentCount").order(SortOrder.DESC))
                .withSort(SortBuilders.fieldSort("createTime").order(SortOrder.DESC))
                .withSort(SortBuilders.fieldSort("id").order(SortOrder.DESC))
                //分页
                .withPageable(PageRequest.of(0,10))
                //字段高亮显示
                .withHighlightFields(
                        new HighlightBuilder.Field("title").preTags("<em>").postTags("</em>"),
                        new HighlightBuilder.Field("content").preTags("<em>").postTags("</em>")
                )
                .build();


    }

    @Test
    public void testFind(){
        // 查询全部，并安装价格降序排序
//        Iterable<Article> items = articleRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
//        items.forEach(item-> System.out.println(item));
        Page<Article> articles = elasticsearchService.searchAllArticleByScoreDesc(9,0, 5);
        List<Article> content = articles.getContent();
        for (Article article : content) {
            System.out.println(article);
        }
//        System.out.println(articles);
    }

}
