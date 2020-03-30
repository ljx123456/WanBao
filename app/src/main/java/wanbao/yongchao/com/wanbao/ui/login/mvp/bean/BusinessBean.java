package wanbao.yongchao.com.wanbao.ui.login.mvp.bean;

public class BusinessBean {


    /**
     * code : 0
     * message : 请求成功
     * data : {"token":"H nOdJ7Jgh82jgFXD0HFWkrTsPPHaCdVy5f7hQXNyy5lwoHNa8NwyP1ZgO6qpYcT rkmNTgoidH/DVQ3lG6aghu3KjTgBPWTs9V6OUhPM3ZSC9hqFD3m3a0hrTwXCfebRgrrQNfMoe0=","data":{"id":10,"nickname":"123","role":2,"accountState":1,"avatar":"https://www.wulihub.com.cn/go/JmVBkq/images/用户详情-基本资料-待审核/u744.jpg","authState":-1}}
     */

    private int code;
    private String message;
    private DataBeanX data;

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

    public DataBeanX getData() {
        return data;
    }

    public void setData(DataBeanX data) {
        this.data = data;
    }

    public static class DataBeanX {
        /**
         * token : H nOdJ7Jgh82jgFXD0HFWkrTsPPHaCdVy5f7hQXNyy5lwoHNa8NwyP1ZgO6qpYcT rkmNTgoidH/DVQ3lG6aghu3KjTgBPWTs9V6OUhPM3ZSC9hqFD3m3a0hrTwXCfebRgrrQNfMoe0=
         * data : {"id":10,"nickname":"123","role":2,"accountState":1,"avatar":"https://www.wulihub.com.cn/go/JmVBkq/images/用户详情-基本资料-待审核/u744.jpg","authState":-1}
         */

        private String token;
        private DataBean data;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public DataBean getData() {
            return data;
        }

        public void setData(DataBean data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * id : 10
             * nickname : 123
             * role : 2
             * accountState : 1
             * avatar : https://www.wulihub.com.cn/go/JmVBkq/images/用户详情-基本资料-待审核/u744.jpg
             * authState : -1
             */

            private String id;
            private String nickname;
            private int role;
            private int accountState;
            private String avatar;
            private int authState;
            private String signature;

            public String getSignature() {
                return signature;
            }

            public void setSignature(String signature) {
                this.signature = signature;
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

            public int getRole() {
                return role;
            }

            public void setRole(int role) {
                this.role = role;
            }

            public int getAccountState() {
                return accountState;
            }

            public void setAccountState(int accountState) {
                this.accountState = accountState;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public int getAuthState() {
                return authState;
            }

            public void setAuthState(int authState) {
                this.authState = authState;
            }
        }
    }
}
