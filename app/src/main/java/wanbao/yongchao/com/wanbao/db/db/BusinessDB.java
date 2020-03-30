package wanbao.yongchao.com.wanbao.db.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class BusinessDB {
    @Id(autoincrement = true)
    private Long id;
    private String token;
    private String avatar;
    private String businessId;//商家id
    private String nickName;
    private String signature;
    private int role;//角色：1 用户，2 商家
    private int accountState;//账号状态：0 封禁，1 正常
    private int authState;//认证状态：-1 未提交认证，0 未通过，1 已通过，2 已提交
    @Generated(hash = 136371872)
    public BusinessDB(Long id, String token, String avatar, String businessId,
            String nickName, String signature, int role, int accountState,
            int authState) {
        this.id = id;
        this.token = token;
        this.avatar = avatar;
        this.businessId = businessId;
        this.nickName = nickName;
        this.signature = signature;
        this.role = role;
        this.accountState = accountState;
        this.authState = authState;
    }
    @Generated(hash = 310162069)
    public BusinessDB() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getToken() {
        return this.token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public String getAvatar() {
        return this.avatar;
    }
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
    public String getBusinessId() {
        return this.businessId;
    }
    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }
    public String getNickName() {
        return this.nickName;
    }
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    public int getRole() {
        return this.role;
    }
    public void setRole(int role) {
        this.role = role;
    }
    public int getAccountState() {
        return this.accountState;
    }
    public void setAccountState(int accountState) {
        this.accountState = accountState;
    }
    public int getAuthState() {
        return this.authState;
    }
    public void setAuthState(int authState) {
        this.authState = authState;
    }
    public String getSignature() {
        return this.signature;
    }
    public void setSignature(String signature) {
        this.signature = signature;
    }
    
}
