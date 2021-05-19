package cn.zsw.campus.forum.controller.user;

import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.io.FileUtil;
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

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
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
        User user = hostHolder.getUser();
        String imgName = RandomUtil.randomString(10);
        String originFileName = file.getOriginalFilename();
        String fileType = originFileName.substring(originFileName.lastIndexOf(".") + 1);
        if (!SUPPORT_IMG_TYPE.contains(fileType)) {
            return ReturnData.fail(CodeEnum.REQUEST_FAILED.getCode(), "仅支持jpg,jpeg,png,gif类型的图片上传!");
        }
        String fileName = AVATAR_PATH + imgName + "." + fileType;
        file.transferTo(new File(fileName));


        String newRandom2 = RandomUtil.randomString(10);
        String newFileName2 = AVATAR_PATH + newRandom2 + "." + fileType;

        File picture = new File(fileName);

        BufferedImage sourceImg = ImageIO.read(new FileInputStream(picture));


        System.out.println(String.format("%.1f",picture.length()/1024.0));// 源图大小
        System.out.println(sourceImg.getWidth()); // 源图宽度
        System.out.println(sourceImg.getHeight()); // 源图高度

        float bili = (float) (300*1.0 / sourceImg.getHeight());


        BigDecimal b = new BigDecimal(bili);
        float num = b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();

        ImgUtil.scale(
                FileUtil.file(fileName),
                FileUtil.file(newFileName2),
                num//缩放比例
        );

        String newRandom1 = RandomUtil.randomString(10);
        String newFileName = AVATAR_PATH + newRandom1 + "." + fileType;

        ImgUtil.pressText(//
                FileUtil.file(newFileName2), //
                FileUtil.file(newFileName), //
                user.getName() + " 版权所有", Color.gray, //文字
                new Font("黑体", Font.ITALIC, 30), //字体
                0, //x坐标修正值。 默认在中间，偏移量相对于中间偏移
                0, //y坐标修正值。 默认在中间，偏移量相对于中间偏移
                0.5f//透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
        );
        return ReturnData.success("http://localhost:8889/img/" + newRandom1 + "." + fileType);
    }


}
