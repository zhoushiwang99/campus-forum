package cn.zsw.campus.forum.bean;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @TableName t_notice
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notice implements Serializable {
    /**
     * 
     */
    private Integer id;

    /**
     * 
     */
    private String content;

    /**
     * 
     */
    private Date createTime;

    private static final long serialVersionUID = 1L;
}