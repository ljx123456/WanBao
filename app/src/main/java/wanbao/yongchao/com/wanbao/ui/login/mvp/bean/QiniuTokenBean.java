package wanbao.yongchao.com.wanbao.ui.login.mvp.bean;

public class QiniuTokenBean {

    /**
     * code : 0
     * message : 请求成功
     * data : {"uploadToken":"a-eT7dVVA0PgfHo0rgGL1NMVtLLSqe5lyiVtU8YE:X4JgP6cSfal9_HXMjX-_B1t3iCY=:eyJzY29wZSI6ImJpeGlueXVsZSIsImRlYWRsaW5lIjoxNTczMjA0MTg3fQ=="}
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
         * uploadToken : a-eT7dVVA0PgfHo0rgGL1NMVtLLSqe5lyiVtU8YE:X4JgP6cSfal9_HXMjX-_B1t3iCY=:eyJzY29wZSI6ImJpeGlueXVsZSIsImRlYWRsaW5lIjoxNTczMjA0MTg3fQ==
         */

        private String uploadToken;

        public String getUploadToken() {
            return uploadToken;
        }

        public void setUploadToken(String uploadToken) {
            this.uploadToken = uploadToken;
        }
    }
}
