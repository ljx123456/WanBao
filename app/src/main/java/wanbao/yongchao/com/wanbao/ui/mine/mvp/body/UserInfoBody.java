package wanbao.yongchao.com.wanbao.ui.mine.mvp.body;

public class UserInfoBody {

    /**
     * token : {{usertoken}}
     */

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserInfoBody(String token) {
        this.token = token;
    }
}
