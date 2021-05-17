package cn.zsw.campus.forum.controller.user;

import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.util.RandomUtil;
import cn.zsw.campus.forum.bean.User;
import cn.zsw.campus.forum.common.CodeEnum;
import cn.zsw.campus.forum.common.ReturnData;
import cn.zsw.campus.forum.mapper.UserMapper;
import cn.zsw.campus.forum.util.HostHolder;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * @author zsw
 * @date 2021/5/11 23:55
 */
@RestController
public class ImgController {

    static List<String> SUPPORT_IMG_TYPE = Arrays.asList("jpg", "jpeg", "png", "gif");
    static String AVATAR_PATH = "E:\\校园论坛\\后端\\images\\";

    @Autowired
    UserMapper userMapper;

    @Autowired
    HostHolder hostHolder;

    @PostMapping("/uploadAvatar")
    public ReturnData uploadAvatar(@RequestParam("avatar") MultipartFile file) throws IOException {
        User user = hostHolder.getUser();
        String imgName = RandomUtil.randomString(10);
        String originFileName = file.getOriginalFilename();
        String fileType = originFileName.substring(originFileName.lastIndexOf(".") + 1);
        if (!SUPPORT_IMG_TYPE.contains(fileType)) {
            return ReturnData.fail(CodeEnum.REQUEST_FAILED.getCode(), "仅支持jpg,jpeg,png,gif类型的图片上传!");
        }
        String fileName = AVATAR_PATH + imgName + "." + fileType;
        file.transferTo(new File(fileName));
        userMapper.updateAvatarByUserId(user.getId(), "http://localhost:8889/img/" + imgName + "." + fileType);
        User userFromDB = userMapper.selectByPrimaryKey(user.getId());
        return ReturnData.success(userFromDB);
    }

    @PostMapping("/uploadImage")
    public ReturnData uploadImage(@RequestParam("image") MultipartFile file) throws IOException {
        String imgName = RandomUtil.randomString(10);
        String originFileName = file.getOriginalFilename();
        String fileType = originFileName.substring(originFileName.lastIndexOf(".") + 1);
        if (!SUPPORT_IMG_TYPE.contains(fileType)) {
            return ReturnData.fail(CodeEnum.REQUEST_FAILED.getCode(), "仅支持jpg,jpeg,png,gif类型的图片上传!");
        }
        String fileName = AVATAR_PATH + imgName + "." + fileType;
        file.transferTo(new File(fileName));
        return ReturnData.success("http://localhost:8889/img/" + imgName + "." + fileType);
    }


}
