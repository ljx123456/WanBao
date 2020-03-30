package wanbao.yongchao.com.wanbao.ui.main.mvp.bean;

import java.io.Serializable;
import java.util.List;

public class CommentDetailsBean {

    /**
     * code : 0
     * message : 请求成功
     * data : {"comments":[{"id":7,"userId":1,"avatar":"http://baidu.com","nickname":"张三","content":"这是一条回复","likeNum":0,"createTime":"10-28 18:17","toUser":{"userId":2,"avatar":"http://baidu.com","nickname":"李四"},"isLike":0},{"id":6,"userId":1,"avatar":"http://baidu.com","nickname":"张三","content":"这是一条回复","likeNum":0,"createTime":"10-28 18:17","isLike":0},{"id":5,"userId":1,"avatar":"http://baidu.com","nickname":"张三","content":"这是一条回复","likeNum":0,"createTime":"10-28 18:17","toUser":{"userId":2,"avatar":"http://baidu.com","nickname":"李四"},"isLike":0}],"totalCommentNum":4}
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
         * comments : [{"id":7,"userId":1,"avatar":"http://baidu.com","nickname":"张三","content":"这是一条回复","likeNum":0,"createTime":"10-28 18:17","toUser":{"userId":2,"avatar":"http://baidu.com","nickname":"李四"},"isLike":0},{"id":6,"userId":1,"avatar":"http://baidu.com","nickname":"张三","content":"这是一条回复","likeNum":0,"createTime":"10-28 18:17","isLike":0},{"id":5,"userId":1,"avatar":"http://baidu.com","nickname":"张三","content":"这是一条回复","likeNum":0,"createTime":"10-28 18:17","toUser":{"userId":2,"avatar":"http://baidu.com","nickname":"李四"},"isLike":0}]
         * totalCommentNum : 4
         */

        private int totalCommentNum;
        private List<CommentsBean> comments;

        public int getTotalCommentNum() {
            return totalCommentNum;
        }

        public void setTotalCommentNum(int totalCommentNum) {
            this.totalCommentNum = totalCommentNum;
        }

        public List<CommentsBean> getComments() {
            return comments;
        }

        public void setComments(List<CommentsBean> comments) {
            this.comments = comments;
        }

        public static class CommentsBean implements Serializable {
            /**
             * id : 7
             * userId : 1
             * avatar : http://baidu.com
             * nickname : 张三
             * content : 这是一条回复
             * likeNum : 0
             * createTime : 10-28 18:17
             * toUser : {"userId":2,"avatar":"http://baidu.com","nickname":"李四"}
             * isLike : 0
             */

            private int id;
            private int userId;
            private String avatar;
            private String nickname;
            private String content;
            private int likeNum;
            private String createTime;
            private ToUserBean toUser;
            private int isLike;

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

            public ToUserBean getToUser() {
                return toUser;
            }

            public void setToUser(ToUserBean toUser) {
                this.toUser = toUser;
            }

            public int getIsLike() {
                return isLike;
            }

            public void setIsLike(int isLike) {
                this.isLike = isLike;
            }

            public static class ToUserBean implements Serializable{
                /**
                 * userId : 2
                 * avatar : http://baidu.com
                 * nickname : 李四
                 */

                private int userId;
                private String avatar;
                private String nickname;

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
            }
        }
    }
}
