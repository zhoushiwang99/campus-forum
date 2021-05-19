package cn.zsw.campus.forum.mapper;

import cn.zsw.campus.forum.bean.Forbidden;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Date;


@Repository
@Mapper
public interface ForbiddenMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Forbidden record);

    int insertSelective(Forbidden record);

    Forbidden selectByUserId(Integer userId);

    int updateByPrimaryKeySelective(Forbidden record);

    int updateByPrimaryKey(Forbidden record);

    int replaceForbidden(Forbidden record);


}




