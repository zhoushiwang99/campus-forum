package cn.zsw.campus.forum.service.impl;

import cn.zsw.campus.forum.bean.University;
import cn.zsw.campus.forum.bean.User;
import cn.zsw.campus.forum.common.CodeEnum;
import cn.zsw.campus.forum.exception.BaseException;
import cn.zsw.campus.forum.mapper.UniversityMapper;
import cn.zsw.campus.forum.service.JwService;
import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author zsw
 * @date 2021/5/10 17:51
 */
@Slf4j
@Service("csustJwService")
public class CsustJwServiceImpl implements JwService {
    private static final String SCHOOL_NAME="长沙理工大学";
    @Autowired
    UniversityMapper universityMapper;

    /**
     * 从教务系统获取code的url
     */
    private static final String GET_CODE_URL = "http://xk.csust.edu.cn/Logon.do?method=logon&flag=sess";

    /**
     * JSESSIONID匹配的正则表达式
     */
    private static final Pattern JW_JSESSIONID_Patten = Pattern.compile("^JSESSIONID=.*");


    /**
     * 教务系统登录的url
     */
    private static final String LOGIN_URL = "http://xk.csust.edu.cn/Logon.do?method=logon";

    /**
     * 教务系统学生主页的url
     */
    private static final String XS_MAIN_JSP_URL = "http://xk.csust.edu.cn/jsxsd/framework/xsMain_new.jsp?t1=1";

    private static Integer universityId;

    @PostConstruct
    public void init() {
        University university = universityMapper.selectByUniversityName(SCHOOL_NAME);
        universityId = university.getId();
    }

    private String cookie = null;
    @Override
    public User getUser(String account, String password) {
        String[] jwCode = getJwCode();
        boolean loginStatus = login(account, password, jwCode[0], jwCode[1]);
        if (loginStatus) {
            User userInfo = getUserInfo();
            userInfo.setUniversityId(universityId);
            return userInfo;
        }
        return null;
    }


    private User getUserInfo() {
        if (cookie == null) {
            throw new BaseException(CodeEnum.SYSTEM_ERROR.getCode(), "未登录无法获取信息");
        }
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .header("Cookie", cookie)
                .header("Host", "xk.csust.edu.cn")
                .header("Referer", "http://xk.csust.edu.cn/jsxsd/framework/xsMain.jsp")
                .url(XS_MAIN_JSP_URL)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String htmlText = response.body().string();
            response.close();
            Document doc = Jsoup.parse(htmlText);
            Element body = doc.body();
            Elements stuInfoDivs = body.getElementsByClass("middletopdwxxcont");
            if (stuInfoDivs == null) {
                throw new BaseException(CodeEnum.REQUEST_FAILED.getCode(), "Cookie错误或已失效");
            }
            User user = new User();
            int index = 0;
            for (Element infoDiv : stuInfoDivs) {
                switch (index) {
                    case 1:
                        user.setName(infoDiv.text());
                        break;
                    case 4:
                        user.setMajor(infoDiv.text());
                        break;
                    case 5:
                        user.setClassName(infoDiv.text());
                        break;
                }
                index++;
            }
            return user;
        } catch (Exception e) {
            throw new BaseException(CodeEnum.JW_SYSTEM_ERROR.getCode(), "教务系统无响应");
        }
    }


    /**
     * 从教务系统获取一个 JSESSIONID和code
     *
     * @return
     */
    private String[] getJwCode() {
        try {
            OkHttpClient okHttpClient = new OkHttpClient();
            Request jwCodeRequest = new Request.Builder().url(GET_CODE_URL).build();
            Response response = okHttpClient.newCall(jwCodeRequest).execute();
            String header = response.header("Set-Cookie");
            String[] split = header.split(";");
            String[] result = new String[2];
            for (String s : split) {
                if (JW_JSESSIONID_Patten.matcher(s).matches()) {
                    result[0] = s.split("=")[1];
                }
            }
            result[1] = response.body().string();
            response.body().close();
            return result;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BaseException(CodeEnum.JW_SYSTEM_ERROR.getCode(), "长沙理工大学教务系统无回应");
        }
    }

    private boolean login(String account, String password, String sessionId, String code) {
        try {
            String[] jwCode = getJwCode();
            String encoded = encodePsd(account, password, jwCode[1]);
            OkHttpClient okHttpClient = new OkHttpClient.Builder().followRedirects(false).build();
            FormBody formBody = new FormBody.Builder()
                    .add("userAccount", account)
                    .add("userPassword", "")
                    .add("encoded", encoded).build();

            Request jwLoginRequest = new Request.Builder()
                    .header("Cookie", "JSESSIONID=" + jwCode[0])
                    .header("Host", "xk.csust.edu.cn")
                    .header("Origin", "http://xk.csust.edu.cn")
                    .header("Referer", "http://xk.csust.edu.cn/")
                    .url(LOGIN_URL)
                    .post(formBody)
                    .build();
            Response response = okHttpClient.newCall(jwLoginRequest).execute();
            String updateCookieUrl = response.header("Location");
            response.close();
            if (updateCookieUrl == null) {
                return false;
            }
            OkHttpClient updateCookieClient = new OkHttpClient.Builder().followRedirects(false).build();
            Request updateCookieRequest = new Request.Builder()
                    .header("Cookie", jwCode[0])
                    .header("Referer", "http://xk.csust.edu.cn/")
                    .url(updateCookieUrl)
                    .build();
            Response updateCookieResponse = updateCookieClient.newCall(updateCookieRequest).execute();
            String header = updateCookieResponse.header("Set-Cookie");
            updateCookieResponse.close();
            if (header == null) {
                return false;
            }

            String[] split = header.split(";");
            for (String s : split) {
                if (JW_JSESSIONID_Patten.matcher(s).matches()) {
                    // 登录成功
                    cookie = s;
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static String encodePsd(String username, String password, String dataStr) {
        String[] splitCode = dataStr.split("#");
        String scode = splitCode[0];
        String sxh = splitCode[1];
        String code = username + "%%%" + password;
        String encode = "";
        for (int i = 0; i < code.length(); i++) {
            if (i < 20) {
                encode = encode + code.substring(i, i + 1) + scode.substring(0, Integer.parseInt(sxh.substring(i, i + 1)));
                scode = scode.substring(Integer.parseInt(sxh.substring(i, i + 1)), scode.length());
            } else {
                encode = encode + code.substring(i, code.length());
                i = code.length();
            }
        }
        return encode;
    }

}
