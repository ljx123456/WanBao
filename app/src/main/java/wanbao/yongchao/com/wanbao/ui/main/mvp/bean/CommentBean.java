package wanbao.yongchao.com.wanbao.ui.main.mvp.bean;

public class CommentBean {

    /**
     * code : 0
     * message : 请求成功
     * data : {"id":1,"userId":88888888,"avatar":"http://pic.bixinyule.com/FhGOa1CTsQa55Y521Wd_c0RRpnTG","nickname":"测试","content":"测试","likeNum":1,"createTime":"12-23 11:07","hasReply":0,"totalCommentNum":0}
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
         * id : 1
         * userId : 88888888
         * avatar : http://pic.bixinyule.com/FhGOa1CTsQa55Y521Wd_c0RRpnTG
         * nickname : 测试
         * content : 测试
         * likeNum : 1
         * createTime : 12-23 11:07
         * hasReply : 0
         * totalCommentNum : 0
         */

        private int id;
        private int userId;
        private String avatar;
        private String nickname;
        private String content;
        private int likeNum;
        private int isLike;
        private String createTime;
        private int hasReply;
        private int totalCommentNum;

        public int getIsLike() {
            return isLike;
        }

        public void setIsLike(int isLike) {
            this.isLike = isLike;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getLikeNum() {
            return likeNum;
        }

        public void setLikeNum(int likeNum) {
            this.likeNum = likeNum;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getHasReply() {
            return hasReply;
        }

        public void setHasReply(int hasReply) {
            this.hasReply = hasReply;
        }

        public int getTotalCommentNum() {
            return totalCommentNum;
        }

        public void setTotalCommentNum(int totalCommentNum) {
            this.totalCommentNum = totalCommentNum;
        }
    }
}
