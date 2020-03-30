package wanbao.yongchao.com.wanbao.ui.main.mvp.bean;

import java.io.Serializable;
import java.util.List;

public class CommunityCommentBean {


    /**
     * code : 0
     * message : 请求成功
     * data : {"comments":[{"id":3,"userId":2,"avatar":"http://baidu.com","nickname":"李四","content":"这是第三条评论","likeNum":2,"createTime":"10-28 18:19","hasReply":0,"isLike":0}],"totalCommentNum":7,"totalLikeNum":1}
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
         * comments : [{"id":3,"userId":2,"avatar":"http://baidu.com","nickname":"李四","content":"这是第三条评论","likeNum":2,"createTime":"10-28 18:19","hasReply":0,"isLike":0}]
         * totalCommentNum : 7
         * totalLikeNum : 1
         */

        private int totalCommentNum;
        private int totalLikeNum;
        private List<CommentsBean> comments;

        public int getTotalCommentNum() {
            return totalCommentNum;
        }

        public void setTotalCommentNum(int totalCommentNum) {
            this.totalCommentNum = totalCommentNum;
        }

        public int getTotalLikeNum() {
            return totalLikeNum;
        }

        public void setTotalLikeNum(int totalLikeNum) {
            this.totalLikeNum = totalLikeNum;
        }

        public List<CommentsBean> getComments() {
            return comments;
        }

        public void setComments(List<CommentsBean> comments) {
            this.comments = comments;
        }

        public static class CommentsBean implements Serializable {
            /**
             * id : 3
             * userId : 2
             * avatar : http://baidu.com
             * nickname : 李四
             * content : 这是第三条评论
             * likeNum : 2
             * createTime : 10-28 18:19
             * hasReply : 0
             * isLike : 0
             */

            private int id;
            private String userId;
            private String avatar;
            private String nickname;
            private String content;
            private int likeNum;
            private String createTime;
            private int hasReply;
            private int isLike;
            private User bean;

            public User getBean() {
                return bean;
            }

            public void setBean(User bean) {
                this.bean = bean;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
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

            public int getIsLike() {
                return isLike;
            }

            public void setIsLike(int isLike) {
                this.isLike = isLike;
            }


            public static class User{
                private String avatar;
                private String name;
                private String content;

                public String getAvatar() {
                    return avatar;
                }

                public void setAvatar(String avayar) {
                    this.avatar = avayar;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getContent() {
                    return content;
                }

                public void setContent(String content) {
                    this.content = content;
                }

                public User(String avatar, String name, String content) {
                    this.avatar = avatar;
                    this.name = name;
                    this.content = content;
                }

                public User() {
                }
            }

        }
    }
}
