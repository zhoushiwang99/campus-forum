package cn.zsw.campus.forum.bean;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Article implements Serializable {
    /**
     *
     */
    private Integer id;

    /**
     *
     */
    private String title;

    /**
     *
     */
    private String content;

    /**
     *
     */
    private Integer userId;

    /**
     *
     */
    private Date createTime;

    /**
     *
     */
    private Integer categoryId;

    /**
     *
     */
    private Boolean top;

    /**
     *
     */
    private Integer commentCount;

    private static final long serialVersionUID = 1L;
}