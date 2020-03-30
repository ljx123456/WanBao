package wanbao.yongchao.com.wanbao.ui.news.mvp.bean;

import java.util.List;

public class NoticeFansBean {

    /**
     * code : 0
     * message : 请求成功
     * data : [{"createTime":"2019-11-28 20:30:11","formatTime":"12分钟前","id":1,"user":{"id":1,"nickname":"美乐迪KTV(二环路东三段)","avatar":"http://pic1.win4000.com/pic/9/eb/529c543283_250_350.jpg","focusType":3}}]
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
         * createTime : 2019-11-28 20:30:11
         * formatTime : 12分钟前
         * id : 1
         * user : {"id":1,"nickname":"美乐迪KTV(二环路东三段)","avatar":"http://pic1.win4000.com/pic/9/eb/529c543283_250_350.jpg","focusType":3}
         */

        private String createTime;
        private String formatTime;
        private int id;
        private UserBean user;

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getFormatTime() {
            return formatTime;
        }

        public void setFormatTime(String formatTime) {
            this.formatTime = formatTime;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public static class UserBean {
            /**
             * id : 1
             * nickname : 美乐迪KTV(二环路东三段)
             * avatar : http://pic1.win4000.com/pic/9/eb/529c543283_250_350.jpg
             * focusType : 3
             */

            private int id;
            private String nickname;
            private String avatar;
            private int focusType=0;

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

            public int getFocusType() {
                return focusType;
            }

            public void setFocusType(int focusType) {
                this.focusType = focusType;
            }
        }
    }
}
