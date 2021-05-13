package cn.zsw.campus.forum.vo;

import cn.zsw.campus.forum.bean.Article;
import cn.zsw.campus.forum.bean.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zsw
 * @date 2021/5/12 22:34
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArticleVO {
    Article article;
    User user;
    String category;
    String createTime;
}
