package wanbao.yongchao.com.wanbao.ui.set.mvp.bean;

public class UpdateBean {

    /**
     * code : 0
     * message : 请求成功
     * data : {"version":1,"versionStr":"1.0.0","url":"http://","isForce":0,"description":"初次更新","createTime":"2020-01-02 11:06:55"}
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
         * version : 1
         * versionStr : 1.0.0
         * url : http://
         * isForce : 0
         * description : 初次更新
         * createTime : 2020-01-02 11:06:55
         */

        private int version;
        private String versionStr;
        private String url;
        private int isForce;
        private String description;
        private String createTime;

        public int getVersion() {
            return version;
        }

        public void setVersion(int version) {
            this.version = version;
        }

        public String getVersionStr() {
            return versionStr;
        }

        public void setVersionStr(String versionStr) {
            this.versionStr = versionStr;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getIsForce() {
            return isForce;
        }

        public void setIsForce(int isForce) {
            this.isForce = isForce;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }
    }
}
