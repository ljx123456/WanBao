package wanbao.yongchao.com.wanbao.ui.news.mvp.bean;

public class NewsBean {


    /**
     * code : 0
     * message : 请求成功
     * data : {"systemMessageNum":1,"fansMessageNum":0,"likeMessageNum":0,"atMessageNum":0,"commentMessageNum":0,"systemMessage":"这是一个消息","systemMessageCreateTime":"2019-12-24 14:45:56"}
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
         * systemMessageNum : 1
         * fansMessageNum : 0
         * likeMessageNum : 0
         * atMessageNum : 0
         * commentMessageNum : 0
         * systemMessage : 这是一个消息
         * systemMessageCreateTime : 2019-12-24 14:45:56
         */

        private int systemMessageNum;
        private int fansMessageNum;
        private int likeMessageNum;
        private int atMessageNum;
        private int commentMessageNum;
        private String systemMessage;
        private String systemMessageCreateTime;

        public int getSystemMessageNum() {
            return systemMessageNum;
        }

        public void setSystemMessageNum(int systemMessageNum) {
            this.systemMessageNum = systemMessageNum;
        }

        public int getFansMessageNum() {
            return fansMessageNum;
        }

        public void setFansMessageNum(int fansMessageNum) {
            this.fansMessageNum = fansMessageNum;
        }

        public int getLikeMessageNum() {
            return likeMessageNum;
        }

        public void setLikeMessageNum(int likeMessageNum) {
            this.likeMessageNum = likeMessageNum;
        }

        public int getAtMessageNum() {
            return atMessageNum;
        }

        public void setAtMessageNum(int atMessageNum) {
            this.atMessageNum = atMessageNum;
        }

        public int getCommentMessageNum() {
            return commentMessageNum;
        }

        public void setCommentMessageNum(int commentMessageNum) {
            this.commentMessageNum = commentMessageNum;
        }

        public String getSystemMessage() {
            return systemMessage;
        }

        public void setSystemMessage(String systemMessage) {
            this.systemMessage = systemMessage;
        }

        public String getSystemMessageCreateTime() {
            return systemMessageCreateTime;
        }

        public void setSystemMessageCreateTime(String systemMessageCreateTime) {
            this.systemMessageCreateTime = systemMessageCreateTime;
        }
    }
}
