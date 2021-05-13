package cn.zsw.campus.forum.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class University implements Serializable {

    private Integer id;

    private String universityName;
    private String serviceName;

    private static final long serialVersionUID = 1L;

}