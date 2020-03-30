package wanbao.yongchao.com.wanbao.ui.explore.mvp.bean;

import java.util.List;

public class ExploreShopBean {

    /**
     * code : 0
     * message : 请求成功
     * data : [{"businessId":2,"businessName":"李四","avatar":"http://baidu.com","cityInfo":"成都·奶茶店","describe":"HI","km":"356.72km","clockNum":0,"businessImages":["http://pic.bixinyule.com/29c2255a-9305-4af6-8d67-259196f59a91"],"clockUsers":[{"id":49878080,"avatar":"http://pic.bixinyule.com/FmohjOXqFBcEf3NDTcng9nM8Y-Vn"},{"id":42728373,"avatar":"http://pic.bixinyule.com/ff8b0922-e0e1-4953-8021-3eaf39670cbf"}]}]
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
         * businessId : 2
         * businessName : 李四
         * avatar : http://baidu.com
         * cityInfo : 成都·奶茶店
         * describe : HI
         * km : 356.72km
         * clockNum : 0
         * businessImages : ["http://pic.bixinyule.com/29c2255a-9305-4af6-8d67-259196f59a91"]
         * clockUsers : [{"id":49878080,"avatar":"http://pic.bixinyule.com/FmohjOXqFBcEf3NDTcng9nM8Y-Vn"},{"id":42728373,"avatar":"http://pic.bixinyule.com/ff8b0922-e0e1-4953-8021-3eaf39670cbf"}]
         */

        private String businessId;
        private String businessName;
        private String avatar;
        private String cityInfo;
        private String describe;
        private String km;
        private int clockNum;
        private List<String> businessImages;
        private List<ClockUsersBean> clockUsers;

        public String getBusinessId() {
            return businessId;
        }

        public void setBusinessId(String businessId) {
            this.businessId = businessId;
        }

        public String getBusinessName() {
            return businessName;
        }

        public void setBusinessName(String businessName) {
            this.businessName = businessName;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getCityInfo() {
            return cityInfo;
        }

        public void setCityInfo(String cityInfo) {
            this.cityInfo = cityInfo;
        }

        public String getDescribe() {
            return describe;
        }

        public void setDescribe(String describe) {
            this.describe = describe;
        }

        public String getKm() {
            return km;
        }

        public void setKm(String km) {
            this.km = km;
        }

        public int getClockNum() {
            return clockNum;
        }

        public void setClockNum(int clockNum) {
            this.clockNum = clockNum;
        }

        public List<String> getBusinessImages() {
            return businessImages;
        }

        public void setBusinessImages(List<String> businessImages) {
            this.businessImages = businessImages;
        }

        public List<ClockUsersBean> getClockUsers() {
            return clockUsers;
        }

        public void setClockUsers(List<ClockUsersBean> clockUsers) {
            this.clockUsers = clockUsers;
        }

        public static class ClockUsersBean {
            /**
             * id : 49878080
             * avatar : http://pic.bixinyule.com/FmohjOXqFBcEf3NDTcng9nM8Y-Vn
             */

            private int id;
            private String avatar;

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
        }
    }
}
