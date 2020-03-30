package wanbao.yongchao.com.wanbao.ui.set.mvp.body;

public class ChangePhoneBody {

    /**
     * account : 18783014630
     * code : 1234
     */

    private String account;
    private String code;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ChangePhoneBody(String account, String code) {
        this.account = account;
        this.code = code;
    }
}
