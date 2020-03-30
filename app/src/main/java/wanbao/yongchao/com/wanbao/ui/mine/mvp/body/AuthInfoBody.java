package wanbao.yongchao.com.wanbao.ui.mine.mvp.body;

public class AuthInfoBody {

    /**
     * token : n7NocJ3f0ZAUcwSNiceqK0rTsPPHaCdVGmvVKksRtsdWEcgPq72en/1ZgO6qpYcTmhFYgoRTK8wtGK3BOHJMmMCgFIBlHyoWi9gqZmJH6nA+ZBG5S2eeBygs9IDDFoTglXEBkCqhjyLo1BSPEHlf6g==
     */

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public AuthInfoBody(String token) {
        this.token = token;
    }
}
