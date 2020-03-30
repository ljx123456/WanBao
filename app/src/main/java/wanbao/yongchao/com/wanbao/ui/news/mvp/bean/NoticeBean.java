package wanbao.yongchao.com.wanbao.ui.news.mvp.bean;

import java.util.List;

public class NoticeBean {

    /**
     * code : 0
     * message : 请求成功
     * data : [{"title":"国庆快乐","content":"这是一个消息","createTime":"2019-11-06 11:32:14","formatTime":"11-06 11:32","jumpType":0,"jumpId":1}]
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
         * title : 国庆快乐
         * content : 这是一个消息
         * createTime : 2019-11-06 11:32:14
         * formatTime : 11-06 11:32
         * jumpType : 0
         * jumpId : 1
         */

        private String title;
        private String content;
        private String createTime;
        private String formatTime;
        private int jumpType;
        private int jumpId;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

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

        public int getJumpType() {
            return jumpType;
        }

        public void setJumpType(int jumpType) {
            this.jumpType = jumpType;
        }

        public int getJumpId() {
            return jumpId;
        }

        public void setJumpId(int jumpId) {
            this.jumpId = jumpId;
        }
    }
}
