package cn.zsw.campus.forum.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Builder
@NoArgsConstructor
@Data
@AllArgsConstructor
public class User implements Serializable {

    private Integer id;
    private String ipAddr;
    /**
     * 姓名
     */
    private String name;

    private Integer universityId;
    /**
     * 账号
     */
    private String account;

    /**
     * 专业
     */
    private String major;

    /**
     * 班级
     */
    private String className;

    /**
     * 注册时间
     */
    private Date registerTime;

    /**
     * 性别
     */
    private String gender;

    /**
     * 签名
     */
    private String signature;
    /**
     * 头像
     */
    private String avatar;

    private static final long serialVersionUID = 1L;


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", account=").append(account);
        sb.append(", major=").append(major);
        sb.append(", className=").append(className);
        sb.append(", registerTime=").append(registerTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}