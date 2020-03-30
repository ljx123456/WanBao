package wanbao.yongchao.com.wanbao.ui.login.mvp.body;

public class GetCodeBody {


    /**
     * account : 18783014637
     * type : 0
     * thirdPartyCode : 2.00iGglzH06uHYB4cf2e883ae88suJD
     */

    private String account;
    private String type;
    private String thirdPartyCode;
    private String token;

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

    public String getThirdPartyCode() {
        return thirdPartyCode;
    }

    public void setThirdPartyCode(String thirdPartyCode) {
        this.thirdPartyCode = thirdPartyCode;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public GetCodeBody(String account, String type) {
        this.account = account;
        this.type = type;
    }

    public GetCodeBody(String account, String type, String thirdPartyCode) {
        this.account = account;
        this.type = type;
        this.thirdPartyCode = thirdPartyCode;
    }

    public GetCodeBody() {
    }
}
