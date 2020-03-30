package wanbao.yongchao.com.wanbao.ui.login.mvp.body;

import java.util.List;

public class RegisterUserBody {

    /**
     * account : 18783014637
     * avatar : https://www.wulihub.com.cn/go/JmVBkq/images/%E7%94%A8%E6%88%B7%E8%AF%A6%E6%83%85-%E5%9F%BA%E6%9C%AC%E8%B5%84%E6%96%99-%E5%BE%85%E5%AE%A1%E6%A0%B8/u744.jpg
     * gender : 1
     * birthday : 1985-11-21 13:59:52
     * nickname : 123
     * phoneModel : mi6
     * longitude : 104.083628
     * latitude : 30.824224
     * tagIds : [1,2,3,4,5]
     */

    private String account;
    private String avatar;
    private String gender;
    private String birthday;
    private String nickname;
    private String phoneModel;
    private String longitude;
    private String latitude;
    private List<Integer> tagIds;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPhoneModel() {
        return phoneModel;
    }

    public void setPhoneModel(String phoneModel) {
        this.phoneModel = phoneModel;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public List<Integer> getTagIds() {
        return tagIds;
    }

    public void setTagIds(List<Integer> tagIds) {
        this.tagIds = tagIds;
    }
}
