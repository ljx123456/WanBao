package wanbao.yongchao.com.wanbao.ui.main.mvp.bean;

import java.util.ArrayList;
import java.util.List;

public class FansBean {

    /**
     * code : 0
     * message : 请求成功
     * data : [{"id":3,"nickname":"王五","age":20,"role":2,"avatar":"http://baidu.com","gender":1,"signature":"no no no","mutualFocus":0}]
     */

    private int code;
    private String message;
    private ArrayList<DataBean> data;

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

    public ArrayList<DataBean> getData() {
        return data;
    }

    public void setData(ArrayList<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 3
         * nickname : 王五
         * age : 20
         * role : 2
         * avatar : http://baidu.com
         * gender : 1
         * signature : no no no
         * mutualFocus : 0
         */

        private String id;
        private String nickname;
        private int age;
        private int role;
        private String avatar;
        private int gender;
        private String signature;
        private int mutualFocus;
        private String address;
        private boolean flag=false;


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

        public int getMutualFocus() {
            return mutualFocus;
        }

        public void setMutualFocus(int mutualFocus) {
            this.mutualFocus = mutualFocus;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public boolean isFlag() {
            return flag;
        }

        public void setFlag(boolean flag) {
            this.flag = flag;
        }

        public DataBean(String id, String nickname, String avatar, boolean flag) {
            this.id = id;
            this.nickname = nickname;
            this.avatar = avatar;
            this.flag = flag;
        }

        public DataBean() {
        }
    }
}
