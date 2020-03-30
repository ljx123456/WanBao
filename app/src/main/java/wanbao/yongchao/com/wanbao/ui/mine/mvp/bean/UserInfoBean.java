package wanbao.yongchao.com.wanbao.ui.mine.mvp.bean;

import java.util.ArrayList;
import java.util.List;

public class UserInfoBean {


    /**
     * code : 0
     * message : 请求成功
     * data : {"id":"1","nickname":"张三","birthday":"1999-07-01 13:59:52","age":"20","role":"2","avatar":"http://baidu.com","images":["https://www.wulihub.com.cn/go/JmVBkq/images/用户详情-基本资料-待审核/u744.jpg","https://www.wulihub.com.cn/go/JmVBkq/images/用户详情-基本资料-待审核/u744.jpg"],"gender":"1","tags":[{"description":"小清新"}],"signature":"hello world","phone":["132456132"],"locationId":"fdafsg4324","address":"武侯区大悦城1","openTime":"18:00:00","closeTime":"01:00:00","perPersonConsume":"500.001","authState":"1","authType":"0","authTypeName":"报纸","likeNum":"0","fansNum":"2","dynamicNum":"12","focusNum":"2","weiBoId":"fdafda","wxOpenId":"56dsjflzm","qqOpenId":"jhkjghfwe"}
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
         * birthday : 1999-07-01 13:59:52
         * age : 20
         * role : 2
         * avatar : http://baidu.com
         * images : ["https://www.wulihub.com.cn/go/JmVBkq/images/用户详情-基本资料-待审核/u744.jpg","https://www.wulihub.com.cn/go/JmVBkq/images/用户详情-基本资料-待审核/u744.jpg"]
         * gender : 1
         * tags : [{"description":"小清新"}]
         * signature : hello world
         * phone : ["132456132"]
         * locationId : fdafsg4324
         * address : 武侯区大悦城1
         * openTime : 18:00:00
         * closeTime : 01:00:00
         * perPersonConsume : 500.001
         * authState : 1
         * authType : 0
         * authTypeName : 报纸
         * likeNum : 0
         * fansNum : 2
         * dynamicNum : 12
         * focusNum : 2
         * weiBoId : fdafda
         * wxOpenId : 56dsjflzm
         * qqOpenId : jhkjghfwe
         */

        private String id;
        private String nickname;
        private String account;
        private String birthday;
        private String age;
        private String role;
        private String avatar;
        private String gender;
        private String signature;
        private String locationId;
        private String address;
        private String openTime;
        private String closeTime;
        private String perPersonConsume;
        private String authState;
        private String authType;
        private String authTypeName;
        private int likeNum;
        private int fansNum;
        private int dynamicNum;
        private int focusNum;
        private String weiBoId;
        private String wxOpenId;
        private String qqOpenId;
        private ArrayList<String> images;
        private ArrayList<TagsBean> tags;
        private ArrayList<String> phone;
        private int typeId;
        private String businessTypeName;
        private String provinceName;
        private String cityName;
        private String county;

        public String getProvinceName() {
            return provinceName;
        }

        public void setProvinceName(String provinceName) {
            this.provinceName = provinceName;
        }

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        public String getCounty() {
            return county;
        }

        public void setCounty(String county) {
            this.county = county;
        }

        public int getTypeId() {
            return typeId;
        }

        public void setTypeId(int typeId) {
            this.typeId = typeId;
        }

        public String getBusinessTypeName() {
            return businessTypeName;
        }

        public void setBusinessTypeName(String businessTypeName) {
            this.businessTypeName = businessTypeName;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
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

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
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

        public String getPerPersonConsume() {
            return perPersonConsume;
        }

        public void setPerPersonConsume(String perPersonConsume) {
            this.perPersonConsume = perPersonConsume;
        }

        public String getAuthState() {
            return authState;
        }

        public void setAuthState(String authState) {
            this.authState = authState;
        }

        public String getAuthType() {
            return authType;
        }

        public void setAuthType(String authType) {
            this.authType = authType;
        }

        public String getAuthTypeName() {
            return authTypeName;
        }

        public void setAuthTypeName(String authTypeName) {
            this.authTypeName = authTypeName;
        }

        public int getLikeNum() {
            return likeNum;
        }

        public void setLikeNum(int likeNum) {
            this.likeNum = likeNum;
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

        public String getWeiBoId() {
            return weiBoId;
        }

        public void setWeiBoId(String weiBoId) {
            this.weiBoId = weiBoId;
        }

        public String getWxOpenId() {
            return wxOpenId;
        }

        public void setWxOpenId(String wxOpenId) {
            this.wxOpenId = wxOpenId;
        }

        public String getQqOpenId() {
            return qqOpenId;
        }

        public void setQqOpenId(String qqOpenId) {
            this.qqOpenId = qqOpenId;
        }

        public ArrayList<String> getImages() {
            return images;
        }

        public void setImages(ArrayList<String> images) {
            this.images = images;
        }

        public ArrayList<TagsBean> getTags() {
            return tags;
        }

        public void setTags(ArrayList<TagsBean> tags) {
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
            private int id;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }
        }
    }
}
