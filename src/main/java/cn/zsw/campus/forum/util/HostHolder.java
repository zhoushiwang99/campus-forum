package cn.zsw.campus.forum.util;


import cn.zsw.campus.forum.bean.User;
import org.springframework.stereotype.Component;

/**
 * 持有用户信息，用户代替Session对象
 * @author zsw
 * @date 2021/4/6 22:18
 */
@Component
public class HostHolder {

    private ThreadLocal<User> users = new ThreadLocal<>();

    public void setUser(User user) {
        users.set(user);
    }

    public User getUser() {
        return users.get();
    }

    public void clear() {
        users.remove();
    }

}
