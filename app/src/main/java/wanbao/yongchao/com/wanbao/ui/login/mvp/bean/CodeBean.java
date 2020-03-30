package wanbao.yongchao.com.wanbao.ui.login.mvp.bean;

public class CodeBean {


    /**
     * code : 0
     * message : 请求成功
     * data : {"registry":true}
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
         * registry : true
         */

        private boolean registry;

        public boolean isRegistry() {
            return registry;
        }

        public void setRegistry(boolean registry) {
            this.registry = registry;
        }
    }
}
