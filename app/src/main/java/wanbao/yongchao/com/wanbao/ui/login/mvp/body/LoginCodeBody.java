package wanbao.yongchao.com.wanbao.ui.login.mvp.body;

public class LoginCodeBody {

    /**
     * account : 18783014637
     * type : 0
     * code : 1234
     */

    private String account;
    private String type;
    private String code;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LoginCodeBody(String account, String type, String code) {
        this.account = account;
        this.type = type;
        this.code = code;
    }


}
