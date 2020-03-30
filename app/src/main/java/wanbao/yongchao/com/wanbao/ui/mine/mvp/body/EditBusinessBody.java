package wanbao.yongchao.com.wanbao.ui.mine.mvp.body;

import java.util.ArrayList;
import java.util.List;

public class EditBusinessBody {

    /**
     * type : 1
     * images : ["https://www.wulihub.com.cn/go/JmVBkq/images/%E7%94%A8%E6%88%B7%E8%AF%A6%E6%83%85-%E5%9F%BA%E6%9C%AC%E8%B5%84%E6%96%99-%E5%BE%85%E5%AE%A1%E6%A0%B8/u744.jpg","https://www.wulihub.com.cn/go/JmVBkq/images/%E7%94%A8%E6%88%B7%E8%AF%A6%E6%83%85-%E5%9F%BA%E6%9C%AC%E8%B5%84%E6%96%99-%E5%BE%85%E5%AE%A1%E6%A0%B8/u744.jpg"]
     * avatar : https://www.wulihub.com.cn/go/JmVBkq/images/%E7%94%A8%E6%88%B7%E8%AF%A6%E6%83%85-%E5%9F%BA%E6%9C%AC%E8%B5%84%E6%96%99-%E5%BE%85%E5%AE%A1%E6%A0%B8/u744.jpg
     * nickname : 怎么没来过
     * phones : [132596366,233466548]
     * signature : 这是一条简介
     * openTime : 13:59:52
     * closeTime : 13:59:52
     * typeId : 1
     * countyId : 1
     * address : 武侯大道 晋级南路
     * locationId :
     * longitude : 66.99
     * latitude : 50.001
     * perPersonConsume : 999.99
     */

    private String type;
    private String avatar;
    private String nickname;
    private String signature;
    private String openTime;
    private String closeTime;
    private String typeId;
    private String countyId;
    private String address;
    private String locationId;
    private String longitude;
    private String latitude;
    private String perPersonConsume;
    private List<String> images;
    private ArrayList<String> phones;

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

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public String getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(String closeTime) {
        this.closeTime = closeTime;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getCountyId() {
        return countyId;
    }

    public void setCountyId(String countyId) {
        this.countyId = countyId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
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

    public String getPerPersonConsume() {
        return perPersonConsume;
    }

    public void setPerPersonConsume(String perPersonConsume) {
        this.perPersonConsume = perPersonConsume;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public ArrayList<String> getPhones() {
        return phones;
    }

    public void setPhones(ArrayList<String> phones) {
        this.phones = phones;
    }
}
