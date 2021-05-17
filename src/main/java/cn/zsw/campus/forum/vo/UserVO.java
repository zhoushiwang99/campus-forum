package cn.zsw.campus.forum.vo;

import cn.zsw.campus.forum.bean.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author zsw
 * @date 2021/5/14 17:33
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserVO {
    private User user;
    private String regTime;
    private List<ArticleVO> article;
}
