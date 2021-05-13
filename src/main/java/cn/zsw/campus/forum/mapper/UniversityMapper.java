package cn.zsw.campus.forum.mapper;

import cn.zsw.campus.forum.bean.University;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UniversityMapper {

    int deleteByPrimaryKey(Long id);

    int insert(University record);

    int insertSelective(University record);

    University selectByPrimaryKey(Integer id);

    University selectByUniversityName(String universityName);

    List<University> selectALlUniversity();

    int updateByPrimaryKeySelective(University record);

    int updateByPrimaryKey(University record);

}




