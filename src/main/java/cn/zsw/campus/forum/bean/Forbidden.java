package cn.zsw.campus.forum.bean;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName t_forbidden
 */
@Data
public class Forbidden implements Serializable {
    /**
     * 
     */
    private Integer id;

    /**
     * 
     */
    private Integer userId;

    /**
     * 
     */
    private Integer forbiddenStatus;

    /**
     * 
     */
    private Date forbiddenTime;

    private static final long serialVersionUID = 1L;
}