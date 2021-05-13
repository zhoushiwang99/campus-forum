package cn.zsw.campus.forum.service;

import cn.zsw.campus.forum.bean.User;

/**
 * @author zsw
 * @date 2021/5/10 17:04
 */
public interface JwService {
    User getUser(String account, String password);

}
