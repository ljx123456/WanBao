package wanbao.yongchao.com.wanbao.ui.news.mvp.bean;

import java.util.List;

public class NoticeAtBean {

    /**
     * code : 0
     * message : 请求成功
     * data : [{"id":6,"type":1,"createTime":"2019-11-28 20:30:11","user":{"id":1,"nickname":"美乐迪KTV(二环路东三段)","avatar":"http://pic1.win4000.com/pic/9/eb/529c543283_250_350.jpg"},"dynamic":{"id":1,"state":2,"content":"@12345678 &h;易易易易易易易易易易易易易易易易易易易易@15288885 &h;易易易易易易易易易易易易易易易易易易易易@12345689 &h;易易易易易易易易易易易易易易易易易易易易易易易易易易易易易易易易易易易易易易易易","images":["http://pic1.win4000.com/pic/9/eb/529c543282.jpg","http://pic1.win4000.com/pic/9/eb/529c543283.jpg"]}},{"id":7,"type":1,"createTime":"2019-11-28 20:30:11","user":{"id":1,"nickname":"美乐迪KTV(二环路东三段)","avatar":"http://pic1.win4000.com/pic/9/eb/529c543283_250_350.jpg"},"dynamic":{"id":1,"state":2,"content":"@12345678 &h;易易易易易易易易易易易易易易易易易易易易@15288885 &h;易易易易易易易易易易易易易易易易易易易易@12345689 &h;易易易易易易易易易易易易易易易易易易易易易易易易易易易易易易易易易易易易易易易易","video":"http://pic1.win4000.com/pic/9/eb/529c543282.jpg"},"comment":{"commentId":19,"userId":11,"avatar":"http://pic1.win4000.com/pic/7/8b/bbf6b03996_250_350.jpg","nickname":"1张老三","content":"这是一条评论","likeNum":0,"createTime":"11-25 15:59","hasReply":1,"isLike":0,"state":2}},{"id":8,"type":1,"createTime":"2019-11-28 20:30:11","user":{"id":1,"nickname":"美乐迪KTV(二环路东三段)","avatar":"http://pic1.win4000.com/pic/9/eb/529c543283_250_350.jpg"},"dynamic":{"id":1,"state":2,"content":"@12345678 &h;易易易易易易易易易易易易易易易易易易易易@15288885 &h;易易易易易易易易易易易易易易易易易易易易@12345689 &h;易易易易易易易易易易易易易易易易易易易易易易易易易易易易易易易易易易易易易易易易","images":["http://pic1.win4000.com/pic/9/eb/529c543282.jpg","http://pic1.win4000.com/pic/9/eb/529c543283.jpg"]},"comment":{"commentId":19,"replyId":13,"userId":11,"avatar":"http://pic1.win4000.com/pic/7/8b/bbf6b03996_250_350.jpg","nickname":"1张老三","content":"这是一条回复","likeNum":0,"createTime":"11-27 17:22","toUser":{"id":11,"avatar":"http://pic1.win4000.com/pic/7/8b/bbf6b03996_250_350.jpg","nickname":"1张老三"},"isLike":0,"state":2}},{"id":9,"type":1,"createTime":"2019-11-28 20:30:11","user":{"id":1,"nickname":"美乐迪KTV(二环路东三段)","avatar":"http://pic1.win4000.com/pic/9/eb/529c543283_250_350.jpg"},"information":{"id":1,"state":2,"previewImg":"https://back.tobosu.com/ke_file/2018-01-27/m_5a6c4f0a67861.jpg"},"comment":{"commentId":19,"userId":11,"avatar":"http://pic1.win4000.com/pic/7/8b/bbf6b03996_250_350.jpg","nickname":"1张老三","content":"这是一条评论","likeNum":0,"createTime":"11-25 15:59","hasReply":1,"isLike":0,"state":2}},{"id":10,"type":1,"createTime":"2019-11-28 20:30:11","user":{"id":1,"nickname":"美乐迪KTV(二环路东三段)","avatar":"http://pic1.win4000.com/pic/9/eb/529c543283_250_350.jpg"},"information":{"id":1,"state":2,"previewImg":"https://back.tobosu.com/ke_file/2018-01-27/m_5a6c4f0a67861.jpg"},"comment":{"commentId":19,"userId":11,"avatar":"http://pic1.win4000.com/pic/7/8b/bbf6b03996_250_350.jpg","nickname":"1张老三","content":"这是一条评论","likeNum":0,"createTime":"11-25 15:59","toUser":{"id":11,"avatar":"http://pic1.win4000.com/pic/7/8b/bbf6b03996_250_350.jpg","nickname":"1张老三"},"hasReply":1,"isLike":0,"state":2}}]
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
         * id : 6
         * type : 1
         * createTime : 2019-11-28 20:30:11
         * user : {"id":1,"nickname":"美乐迪KTV(二环路东三段)","avatar":"http://pic1.win4000.com/pic/9/eb/529c543283_250_350.jpg"}
         * dynamic : {"id":1,"state":2,"content":"@12345678 &h;易易易易易易易易易易易易易易易易易易易易@15288885 &h;易易易易易易易易易易易易易易易易易易易易@12345689 &h;易易易易易易易易易易易易易易易易易易易易易易易易易易易易易易易易易易易易易易易易","images":["http://pic1.win4000.com/pic/9/eb/529c543282.jpg","http://pic1.win4000.com/pic/9/eb/529c543283.jpg"]}
         * comment : {"commentId":19,"userId":11,"avatar":"http://pic1.win4000.com/pic/7/8b/bbf6b03996_250_350.jpg","nickname":"1张老三","content":"这是一条评论","likeNum":0,"createTime":"11-25 15:59","hasReply":1,"isLike":0,"state":2}
         * information : {"id":1,"state":2,"previewImg":"https://back.tobosu.com/ke_file/2018-01-27/m_5a6c4f0a67861.jpg"}
         */

        private int id;
        private int type;
        private String createTime;
        private UserBean user;
        private DynamicBean dynamic;
        private CommentBean comment;
        private InformationBean information;
        private String formatTime;

        public String getFormatTime() {
            return formatTime;
        }

        public void setFormatTime(String formatTime) {
            this.formatTime = formatTime;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public DynamicBean getDynamic() {
            return dynamic;
        }

        public void setDynamic(DynamicBean dynamic) {
            this.dynamic = dynamic;
        }

        public CommentBean getComment() {
            return comment;
        }

        public void setComment(CommentBean comment) {
            this.comment = comment;
        }

        public InformationBean getInformation() {
            return information;
        }

        public void setInformation(InformationBean information) {
            this.information = information;
        }

        public static class UserBean {
            /**
             * id : 1
             * nickname : 美乐迪KTV(二环路东三段)
             * avatar : http://pic1.win4000.com/pic/9/eb/529c543283_250_350.jpg
             */

            private int id;
            private String nickname;
            private String avatar;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }
        }

        public static class DynamicBean {
            /**
             * id : 1
             * state : 2
             * content : @12345678 &h;易易易易易易易易易易易易易易易易易易易易@15288885 &h;易易易易易易易易易易易易易易易易易易易易@12345689 &h;易易易易易易易易易易易易易易易易易易易易易易易易易易易易易易易易易易易易易易易易
             * images : ["http://pic1.win4000.com/pic/9/eb/529c543282.jpg","http://pic1.win4000.com/pic/9/eb/529c543283.jpg"]
             */

            private int id;
            private int state;
            private String content;
            private List<String> images;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getState() {
                return state;
            }

            public void setState(int state) {
                this.state = state;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public List<String> getImages() {
                return images;
            }

            public void setImages(List<String> images) {
                this.images = images;
            }
        }

        public static class CommentBean {
            /**
             * commentId : 19
             * userId : 11
             * avatar : http://pic1.win4000.com/pic/7/8b/bbf6b03996_250_350.jpg
             * nickname : 1张老三
             * content : 这是一条评论
             * likeNum : 0
             * createTime : 11-25 15:59
             * hasReply : 1
             * isLike : 0
             * state : 0
             */

            private int commentId;
            private int userId;
            private String avatar;
            private String nickname;
            private String content;
            private int likeNum;
            private String createTime;
            private int hasReply;
            private int isLike;
            private int state;
            private int replyId;
            private ToUserBean toUser;

            public int getReplyId() {
                return replyId;
            }

            public void setReplyId(int replyId) {
                this.replyId = replyId;
            }

            public ToUserBean getToUser() {
                return toUser;
            }

            public void setToUser(ToUserBean toUser) {
                this.toUser = toUser;
            }

            public int getCommentId() {
                return commentId;
            }

            public void setCommentId(int commentId) {
                this.commentId = commentId;
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

            public int getIsLike() {
                return isLike;
            }

            public void setIsLike(int isLike) {
                this.isLike = isLike;
            }

            public int getState() {
                return state;
            }

            public void setState(int state) {
                this.state = state;
            }

            public static class ToUserBean {
                /**
                 * id : 11
                 * avatar : http://pic1.win4000.com/pic/7/8b/bbf6b03996_250_350.jpg
                 * nickname : 1张老三
                 */

                private int id;
                private String avatar;
                private String nickname;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
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

        public static class InformationBean {
            /**
             * id : 1
             * state : 2
             * previewImg : https://back.tobosu.com/ke_file/2018-01-27/m_5a6c4f0a67861.jpg
             */

            private int id;
            private int state;
            private String previewImg;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getState() {
                return state;
            }

            public void setState(int state) {
                this.state = state;
            }

            public String getPreviewImg() {
                return previewImg;
            }

            public void setPreviewImg(String previewImg) {
                this.previewImg = previewImg;
            }
        }
    }
}
