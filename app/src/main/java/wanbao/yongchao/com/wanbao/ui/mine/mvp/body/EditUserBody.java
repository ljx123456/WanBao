package wanbao.yongchao.com.wanbao.ui.mine.mvp.body;

import java.util.ArrayList;
import java.util.List;

public class EditUserBody {

    /**
     * type : 1
     * images : ["https://www.wulihub.com.cn/go/JmVBkq/images/%E7%94%A8%E6%88%B7%E8%AF%A6%E6%83%85-%E5%9F%BA%E6%9C%AC%E8%B5%84%E6%96%99-%E5%BE%85%E5%AE%A1%E6%A0%B8/u744.jpg","https://www.wulihub.com.cn/go/JmVBkq/images/%E7%94%A8%E6%88%B7%E8%AF%A6%E6%83%85-%E5%9F%BA%E6%9C%AC%E8%B5%84%E6%96%99-%E5%BE%85%E5%AE%A1%E6%A0%B8/u744.jpg"]
     * avatar : https://www.wulihub.com.cn/go/JmVBkq/images/%E7%94%A8%E6%88%B7%E8%AF%A6%E6%83%85-%E5%9F%BA%E6%9C%AC%E8%B5%84%E6%96%99-%E5%BE%85%E5%AE%A1%E6%A0%B8/u744.jpg
     * nickname :
     * birthday :
     * signature :
     * tagIds : [1,2,3]
     */

    private String type;
    private String avatar;
    private String nickname;
    private String birthday;
    private String signature;
    private ArrayList<String> images;
    private ArrayList<Integer> tagIds;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

    public ArrayList<Integer> getTagIds() {
        return tagIds;
    }

    public void setTagIds(ArrayList<Integer> tagIds) {
        this.tagIds = tagIds;
    }
}
