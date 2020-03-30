package wanbao.yongchao.com.wanbao.ui.explore.mvp.bean;

import java.util.List;

public class WantUserBean {

    /**
     * code : 0
     * message : 请求成功
     * data : [{"id":2,"nickname":"李四","avatar":"http://baidu.com","signature":"HI"}]
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
         * id : 2
         * nickname : 李四
         * avatar : http://baidu.com
         * signature : HI
         */

        private int id;
        private String nickname;
        private String avatar;
        private String signature;

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
    }
}
