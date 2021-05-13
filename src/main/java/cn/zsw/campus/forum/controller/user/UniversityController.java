package cn.zsw.campus.forum.controller.user;

import cn.zsw.campus.forum.bean.University;
import cn.zsw.campus.forum.common.ReturnData;
import cn.zsw.campus.forum.mapper.UniversityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author zsw
 * @date 2021/5/10 21:24
 */
@RestController
public class UniversityController {

    @Autowired
    UniversityMapper universityMapper;

    @GetMapping("/getAllUniversity")
    public ReturnData getAllUniversity() {
        List<University> universityList = universityMapper.selectALlUniversity();
        return ReturnData.success(universityList);
    }

}
