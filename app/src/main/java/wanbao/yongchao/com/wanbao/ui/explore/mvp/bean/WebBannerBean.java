package wanbao.yongchao.com.wanbao.ui.explore.mvp.bean;

public class WebBannerBean {

    /**
     * code : 0
     * message : 请求成功
     * data : {"id":10,"name":"banner测试3","htmlContent":"<html>n<\/html>"}
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
         * id : 10
         * name : banner测试3
         * htmlContent : <html>n</html>
         */

        private int id;
        private String name;
        private String htmlContent;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getHtmlContent() {
            return htmlContent;
        }

        public void setHtmlContent(String htmlContent) {
            this.htmlContent = htmlContent;
        }
    }
}
