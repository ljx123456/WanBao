package wanbao.yongchao.com.wanbao.ui.explore.mvp.bean;

import java.util.List;

public class ActivitiesDetailsBean {

    /**
     * code : 0
     * message : 请求成功
     * data : {"data":{"id":2,"title":"这是个活动2的标题","address":"成都市 武侯区 人民南路 商鼎国际","content":"这是活动的内容2","startTime":"2019-10-24 09:32:08","endTime":"2019-10-30 09:32:12","phones":["13512345678","13512345676"],"wantNum":0,"isWant":0,"wantAvatars":[],"cityInfo":"四川-成都","videoSet":["http://","http://"]},"businessId":1,"businessAvatar":"http://baidu.com","businessName":"张三","releaseTime":"10-29 09:32","isFollow":0,"businessType":"北京-美食"}
     */

    private int code;
    private String message;
    private DataBeanX data;

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

    public DataBeanX getData() {
        return data;
    }

    public void setData(DataBeanX data) {
        this.data = data;
    }

    public static class DataBeanX {
        /**
         * data : {"id":2,"title":"这是个活动2的标题","address":"成都市 武侯区 人民南路 商鼎国际","content":"这是活动的内容2","startTime":"2019-10-24 09:32:08","endTime":"2019-10-30 09:32:12","phones":["13512345678","13512345676"],"wantNum":0,"isWant":0,"wantAvatars":[],"cityInfo":"四川-成都","videoSet":["http://","http://"]}
         * businessId : 1
         * businessAvatar : http://baidu.com
         * businessName : 张三
         * releaseTime : 10-29 09:32
         * isFollow : 0
         * businessType : 北京-美食
         */

        private DataBean data;
        private int businessId;
        private String businessAvatar;
        private String businessName;
        private String releaseTime;
        private int isFollow=-1;
        private String businessType;

        public DataBean getData() {
            return data;
        }

        public void setData(DataBean data) {
            this.data = data;
        }

        public int getBusinessId() {
            return businessId;
        }

        public void setBusinessId(int businessId) {
            this.businessId = businessId;
        }

        public String getBusinessAvatar() {
            return businessAvatar;
        }

        public void setBusinessAvatar(String businessAvatar) {
            this.businessAvatar = businessAvatar;
        }

        public String getBusinessName() {
            return businessName;
        }

        public void setBusinessName(String businessName) {
            this.businessName = businessName;
        }

        public String getReleaseTime() {
            return releaseTime;
        }

        public void setReleaseTime(String releaseTime) {
            this.releaseTime = releaseTime;
        }

        public int getIsFollow() {
            return isFollow;
        }

        public void setIsFollow(int isFollow) {
            this.isFollow = isFollow;
        }

        public String getBusinessType() {
            return businessType;
        }

        public void setBusinessType(String businessType) {
            this.businessType = businessType;
        }

        public static class DataBean {
            /**
             * id : 2
             * title : 这是个活动2的标题
             * address : 成都市 武侯区 人民南路 商鼎国际
             * content : 这是活动的内容2
             * startTime : 2019-10-24 09:32:08
             * endTime : 2019-10-30 09:32:12
             * phones : ["13512345678","13512345676"]
             * wantNum : 0
             * isWant : 0
             * wantAvatars : []
             * cityInfo : 四川-成都
             * videoSet : ["http://","http://"]
             */

            private int id;
            private String title;
            private String address;
            private String content;
            private String startTime;
            private String endTime;
            private int wantNum;
            private int isWant;
            private String cityInfo;
            private List<String> phones;
            private List<String> wantAvatars;
            private List<String> videoSet;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getStartTime() {
                return startTime;
            }

            public void setStartTime(String startTime) {
                this.startTime = startTime;
            }

            public String getEndTime() {
                return endTime;
            }

            public void setEndTime(String endTime) {
                this.endTime = endTime;
            }

            public int getWantNum() {
                return wantNum;
            }

            public void setWantNum(int wantNum) {
                this.wantNum = wantNum;
            }

            public int getIsWant() {
                return isWant;
            }

            public void setIsWant(int isWant) {
                this.isWant = isWant;
            }

            public String getCityInfo() {
                return cityInfo;
            }

            public void setCityInfo(String cityInfo) {
                this.cityInfo = cityInfo;
            }

            public List<String> getPhones() {
                return phones;
            }

            public void setPhones(List<String> phones) {
                this.phones = phones;
            }

            public List<String> getWantAvatars() {
                return wantAvatars;
            }

            public void setWantAvatars(List<String> wantAvatars) {
                this.wantAvatars = wantAvatars;
            }

            public List<String> getVideoSet() {
                return videoSet;
            }

            public void setVideoSet(List<String> videoSet) {
                this.videoSet = videoSet;
            }
        }
    }
}
