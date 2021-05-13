package cn.zsw.campus.forum.bean;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * null
 * @TableName t_category
 */
@Data
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