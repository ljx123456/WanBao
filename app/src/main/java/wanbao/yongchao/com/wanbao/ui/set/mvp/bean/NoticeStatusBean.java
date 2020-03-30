package wanbao.yongchao.com.wanbao.ui.set.mvp.bean;

public class NoticeStatusBean {

    /**
     * code : 0
     * message : 请求成功
     * data : {"messageLike":true,"messageComment":true,"messageFocus":true,"messageAtMe":true}
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
         * messageLike : true
         * messageComment : true
         * messageFocus : true
         * messageAtMe : true
         */

        private boolean messageLike;
        private boolean messageComment;
        private boolean messageFocus;
        private boolean messageAtMe;

        public boolean isMessageLike() {
            return messageLike;
        }

        public void setMessageLike(boolean messageLike) {
            this.messageLike = messageLike;
        }

        public boolean isMessageComment() {
            return messageComment;
        }

        public void setMessageComment(boolean messageComment) {
            this.messageComment = messageComment;
        }

        public boolean isMessageFocus() {
            return messageFocus;
        }

        public void setMessageFocus(boolean messageFocus) {
            this.messageFocus = messageFocus;
        }

        public boolean isMessageAtMe() {
            return messageAtMe;
        }

        public void setMessageAtMe(boolean messageAtMe) {
            this.messageAtMe = messageAtMe;
        }
    }
}
