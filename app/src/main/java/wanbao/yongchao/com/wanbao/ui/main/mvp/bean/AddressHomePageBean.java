package wanbao.yongchao.com.wanbao.ui.main.mvp.bean;

import java.util.List;

public class AddressHomePageBean {

    /**
     * code : 0
     * message : 请求成功
     * data : {"cityMapId":1,"cityMapName":"九远桥","cityMapAddress":"成都市-九远桥","videoSet":["http://","http://"],"longitude":"104.10194","latitude":"30.782618983520624","describe":"九远桥描述","wantUserNum":2,"wantUserAvaterSet":["http://baidu.com","http://baidu.com"],"km":"621.91km","isWant":0,"clockNum":1,"locationId":"B001786RF1"}
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
         * cityMapId : 1
         * cityMapName : 九远桥
         * cityMapAddress : 成都市-九远桥
         * videoSet : ["http://","http://"]
         * longitude : 104.10194
         * latitude : 30.782618983520624
         * describe : 九远桥描述
         * wantUserNum : 2
         * wantUserAvaterSet : ["http://baidu.com","http://baidu.com"]
         * km : 621.91km
         * isWant : 0
         * clockNum : 1
         * locationId : B001786RF1
         */

        private int cityMapId;
        private String cityMapName;
        private String cityMapAddress;
        private String longitude;
        private String latitude;
        private String content;
        private int wantUserNum;
        private String km;
        private int isWant;
        private int clockNum;
        private String locationId;
        private List<String> videoSet;
        private List<String> wantUserAvaterSet;

        public int getCityMapId() {
            return cityMapId;
        }

        public void setCityMapId(int cityMapId) {
            this.cityMapId = cityMapId;
        }

        public String getCityMapName() {
            return cityMapName;
        }

        public void setCityMapName(String cityMapName) {
            this.cityMapName = cityMapName;
        }

        public String getCityMapAddress() {
            return cityMapAddress;
        }

        public void setCityMapAddress(String cityMapAddress) {
            this.cityMapAddress = cityMapAddress;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getWantUserNum() {
            return wantUserNum;
        }

        public void setWantUserNum(int wantUserNum) {
            this.wantUserNum = wantUserNum;
        }

        public String getKm() {
            return km;
        }

        public void setKm(String km) {
            this.km = km;
        }

        public int getIsWant() {
            return isWant;
        }

        public void setIsWant(int isWant) {
            this.isWant = isWant;
        }

        public int getClockNum() {
            return clockNum;
        }

        public void setClockNum(int clockNum) {
            this.clockNum = clockNum;
        }

        public String getLocationId() {
            return locationId;
        }

        public void setLocationId(String locationId) {
            this.locationId = locationId;
        }

        public List<String> getVideoSet() {
            return videoSet;
        }

        public void setVideoSet(List<String> videoSet) {
            this.videoSet = videoSet;
        }

        public List<String> getWantUserAvaterSet() {
            return wantUserAvaterSet;
        }

        public void setWantUserAvaterSet(List<String> wantUserAvaterSet) {
            this.wantUserAvaterSet = wantUserAvaterSet;
        }
    }
}
