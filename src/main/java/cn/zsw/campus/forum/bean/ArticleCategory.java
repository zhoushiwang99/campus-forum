package cn.zsw.campus.forum.bean;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;


/**
 * @author zsw
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticleCategory implements Serializable {
    /**
     * 
     */
    private Integer id;

    /**
     * 
     */
    private String name;

    /**
     * 
     */
    private Date createTime;

    private static final long serialVersionUID = 1L;
}