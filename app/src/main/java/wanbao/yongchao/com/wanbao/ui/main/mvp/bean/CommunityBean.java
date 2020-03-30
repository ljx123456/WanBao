package wanbao.yongchao.com.wanbao.ui.main.mvp.bean;

import java.util.List;

public class CommunityBean {

    /**
     * code : 0
     * message : 请求成功
     * data : [{"id":13,"createTime":"2019-11-06 13:30:43","releaseTime":"3小时前","likeNum":0,"shareNum":0,"commentNum":0,"collectNum":0,"location":"重庆市·鸿恩寺森林公园","locationType":2,"objectId":2,"content":"@张三 &h;@lisi &h;@fjdsk &h;@nn@fz","images":["http://","http://"],"video":"","userId":1,"topicId":1,"topicName":"话题","topicDynamicNum":22,"isLike":0,"isCollect":0,"isFocus":0,"user":{"id":1,"nickname":"张三","role":1,"avatar":"http://baidu.com","authType":2}}]
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
         * id : 13
         * createTime : 2019-11-06 13:30:43
         * releaseTime : 3小时前
         * likeNum : 0
         * shareNum : 0
         * commentNum : 0
         * collectNum : 0
         * location : 重庆市·鸿恩寺森林公园
         * locationType : 2
         * objectId : 2
         * content : @张三 &h;@lisi &h;@fjdsk &h;@nn@fz
         * images : ["http://","http://"]
         * video :
         * userId : 1
         * topicId : 1
         * topicName : 话题
         * topicDynamicNum : 22
         * isLike : 0
         * isCollect : 0
         * isFocus : 0
         * user : {"id":1,"nickname":"张三","role":1,"avatar":"http://baidu.com","authType":2}
         */

        private int id;
        private String createTime;
        private String releaseTime;
        private int likeNum;
        private int shareNum;
        private int commentNum;
        private int collectNum;
        private String location;
        private int locationType;
        private int objectId;
        private String content;
        private String video;
        private int userId;
        private int topicId;
        private String topicName;
        private int topicDynamicNum;
        private int isLike;
        private int isCollect;
        private int isFocus;
        private UserBean user;
        private List<String> images;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getReleaseTime() {
            return releaseTime;
        }

        public void setReleaseTime(String releaseTime) {
            this.releaseTime = releaseTime;
        }

        public int getLikeNum() {
            return likeNum;
        }

        public void setLikeNum(int likeNum) {
            this.likeNum = likeNum;
        }

        public int getShareNum() {
            return shareNum;
        }

        public void setShareNum(int shareNum) {
            this.shareNum = shareNum;
        }

        public int getCommentNum() {
            return commentNum;
        }

        public void setCommentNum(int commentNum) {
            this.commentNum = commentNum;
        }

        public int getCollectNum() {
            return collectNum;
        }

        public void setCollectNum(int collectNum) {
            this.collectNum = collectNum;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public int getLocationType() {
            return locationType;
        }

        public void setLocationType(int locationType) {
            this.locationType = locationType;
        }

        public int getObjectId() {
            return objectId;
        }

        public void setObjectId(int objectId) {
            this.objectId = objectId;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getVideo() {
            return video;
        }

        public void setVideo(String video) {
            this.video = video;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getTopicId() {
            return topicId;
        }

        public void setTopicId(int topicId) {
            this.topicId = topicId;
        }

        public String getTopicName() {
            return topicName;
        }

        public void setTopicName(String topicName) {
            this.topicName = topicName;
        }

        public int getTopicDynamicNum() {
            return topicDynamicNum;
        }

        public void setTopicDynamicNum(int topicDynamicNum) {
            this.topicDynamicNum = topicDynamicNum;
        }

        public int getIsLike() {
            return isLike;
        }

        public void setIsLike(int isLike) {
            this.isLike = isLike;
        }

        public int getIsCollect() {
            return isCollect;
        }

        public void setIsCollect(int isCollect) {
            this.isCollect = isCollect;
        }

        public int getIsFocus() {
            return isFocus;
        }

        public void setIsFocus(int isFocus) {
            this.isFocus = isFocus;
        }

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }

        public static class UserBean {
            /**
             * id : 1
             * nickname : 张三
             * role : 1
             * avatar : http://baidu.com
             * authType : 2
             */

            private int id;
            private String nickname;
            private int role;
            private String avatar;
            private int authType;

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

            public int getRole() {
                return role;
            }

            public void setRole(int role) {
                this.role = role;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public int getAuthType() {
                return authType;
            }

            public void setAuthType(int authType) {
                this.authType = authType;
            }
        }
    }
}
