package cn.zsw.campus.forum.bean;

import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @author zsw
 */
@Data
public class Admin implements Serializable {

    private Integer id;
    private String account;
    private String userRole;
    private static final long serialVersionUID = 1L;
}