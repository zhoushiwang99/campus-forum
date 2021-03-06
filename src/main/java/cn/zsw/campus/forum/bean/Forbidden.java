package cn.zsw.campus.forum.bean;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @TableName t_forbidden
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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