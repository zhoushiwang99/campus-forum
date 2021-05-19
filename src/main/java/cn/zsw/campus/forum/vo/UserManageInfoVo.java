package cn.zsw.campus.forum.vo;

import cn.zsw.campus.forum.bean.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.print.attribute.standard.PrinterURI;
import java.util.Date;

/**
 * @author zsw
 * @date 2021/5/19 17:18
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserManageInfoVo {
    private User user;
    private String regTime;
    private String universityName;
    private int forbidden;
    private Date forbiddenTime;
}
