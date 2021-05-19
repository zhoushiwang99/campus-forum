package cn.zsw.campus.forum.mapper;

import cn.zsw.campus.forum.bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User selectByUniversityIdAndAccount(Integer universityId, String account);


    List<User> selectAllUser();

    List<User> selectUserByKeyword(String keyword);


    int updateAvatarByUserId(Integer id,String avatar);

    int selectTotalCount();
}




