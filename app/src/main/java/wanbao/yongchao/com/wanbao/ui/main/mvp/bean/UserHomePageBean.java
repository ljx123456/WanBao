package wanbao.yongchao.com.wanbao.ui.main.mvp.bean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class UserHomePageBean {

    /**
     * code : 0
     * message : 请求成功
     * data : {"id":1,"nickname":"张三","age":20,"role":2,"avatar":"http://baidu.com","images":["https://www.wulihub.com.cn/go/JmVBkq/images/用户详情-基本资料-待审核/u744.jpg","https://www.wulihub.com.cn/go/JmVBkq/images/用户详情-基本资料-待审核/u744.jpg"],"gender":1,"tags":[{"description":"小清新"}],"signature":"hello world","phone":["132456132"],"locationId":"fdafsg4324","longitude":66.99,"latitude":50.001,"address":"武侯区大悦城1","openTime":"18:00:00","closeTime":"01:00:00","perPersonConsume":500.001,"authType":0,"fansNum":2,"dynamicNum":12,"focusNum":2,"focusType":"1"}
     */

    private int code;
    private String message;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1
         * nickname : 张三
         * age : 20
         * role : 2
         * avatar : http://baidu.com
         * images : ["https://www.wulihub.com.cn/go/JmVBkq/images/用户详情-基本资料-待审核/u744.jpg","https://www.wulihub.com.cn/go/JmVBkq/images/用户详情-基本资料-待审核/u744.jpg"]
         * gender : 1
         * tags : [{"description":"小清新"}]
         * signature : hello world
         * phone : ["132456132"]
         * locationId : fdafsg4324
         * longitude : 66.99
         * latitude : 50.001
         * address : 武侯区大悦城1
         * openTime : 18:00:00
         * closeTime : 01:00:00
         * perPersonConsume : 500.001
         * authType : 0
         * fansNum : 2
         * dynamicNum : 12
         * focusNum : 2
         * focusType : 1
         */

        private String id;
        private String nickname;
        private int age;
        private int role;
        private String avatar;
        private int gender;
        private String signature;
        private String locationId;
        private double longitude;
        private double latitude;
        private String address;
        private String openTime;
        private String closeTime;
        private BigDecimal perPersonConsume=new BigDecimal(0);
        private int authType;
        private String authTypeName;
        private int fansNum;
        private int dynamicNum;
        private int focusNum;
        private String focusType;
        private List<String> images;
        private List<TagsBean> tags;
        private ArrayList<String> phone;

        public String getAuthTypeName() {
            return authTypeName;
        }

        public void setAuthTypeName(String authTypeName) {
            this.authTypeName = authTypeName;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public int getRole() {
            return role;
        }

        public void setRole(int role) {
            this.role = role;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public String getSignature() {
            return signature;
        }

        public void setSignature(String signature) {
            this.signature = signature;
        }

        public String getLocationId() {
            return locationId;
        }

        public void setLocationId(String locationId) {
            this.locationId = locationId;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
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

        public BigDecimal getPerPersonConsume() {
            return perPersonConsume;
        }

        public void setPerPersonConsume(BigDecimal perPersonConsume) {
            this.perPersonConsume = perPersonConsume;
        }

        public int getAuthType() {
            return authType;
        }

        public void setAuthType(int authType) {
            this.authType = authType;
        }

        public int getFansNum() {
            return fansNum;
        }

        public void setFansNum(int fansNum) {
            this.fansNum = fansNum;
        }

        public int getDynamicNum() {
            return dynamicNum;
        }

        public void setDynamicNum(int dynamicNum) {
            this.dynamicNum = dynamicNum;
        }

        public int getFocusNum() {
            return focusNum;
        }

        public void setFocusNum(int focusNum) {
            this.focusNum = focusNum;
        }

        public String getFocusType() {
            return focusType;
        }

        public void setFocusType(String focusType) {
            this.focusType = focusType;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }

        public List<TagsBean> getTags() {
            return tags;
        }

        public void setTags(List<TagsBean> tags) {
            this.tags = tags;
        }

        public ArrayList<String> getPhone() {
            return phone;
        }

        public void setPhone(ArrayList<String> phone) {
            this.phone = phone;
        }

        public static class TagsBean {
            /**
             * description : 小清新
             */

            private String description;

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }
        }
    }
}
