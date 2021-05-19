package cn.zsw.campus.forum.mapper;

import cn.zsw.campus.forum.bean.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


@Repository
@Mapper
public interface AdminMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Admin record);

    int insertSelective(Admin record);

    Admin selectByPrimaryKey(Long id);

    Admin selectByAccount(String account);

    int updateByPrimaryKeySelective(Admin record);

    int updateByPrimaryKey(Admin record);

}




