package wanbao.yongchao.com.wanbao.ui.main.mvp.bean;

import java.util.List;

public class CommunityLikeBean {


    /**
     * code : 0
     * message : 请求成功
     * data : [{"id":1,"nickname":"美乐迪KTV(二环路东三段)","avatar":"https://back.tobosu.com/ke_file/2018-01-27/m_5a6c4f0a67861.jpg","signature":"hello world","createTime":"2019-11-16 17:53:45","releaseTime":"3小时前"}]
     */

    private int code;
    private String message;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1
         * nickname : 美乐迪KTV(二环路东三段)
         * avatar : https://back.tobosu.com/ke_file/2018-01-27/m_5a6c4f0a67861.jpg
         * signature : hello world
         * createTime : 2019-11-16 17:53:45
         * releaseTime : 3小时前
         */

        private int id;
        private String nickname;
        private String avatar;
        private String signature;
        private String createTime;
        private String releaseTime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getSignature() {
            return signature;
        }

        public void setSignature(String signature) {
            this.signature = signature;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getReleaseTime() {
            return releaseTime;
        }

        public void setReleaseTime(String releaseTime) {
            this.releaseTime = releaseTime;
        }
    }
}
