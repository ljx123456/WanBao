package wanbao.yongchao.com.wanbao.ui.explore.mvp.bean;

import java.util.List;

public class ExploreLandMarkBean {

    /**
     * code : 0
     * message : 请求成功
     * data : [{"cityMapId":1,"cityMapName":"九远桥","avatar":"http://store.is.autonavi.com/showpic/a0350a170cd18c731e0bd87295a7fa3b","wantUserNum":2},{"cityMapId":2,"cityMapName":"九远桥2","avatar":"http://store.is.autonavi.com/showpic/a0350a170cd18c731e0bd87295a7fa3b","wantUserNum":0}]
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
         * cityMapId : 1
         * cityMapName : 九远桥
         * avatar : http://store.is.autonavi.com/showpic/a0350a170cd18c731e0bd87295a7fa3b
         * wantUserNum : 2
         */

        private int cityMapId;
        private String cityMapName;
        private String avatar;
        private int wantUserNum;

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

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public int getWantUserNum() {
            return wantUserNum;
        }

        public void setWantUserNum(int wantUserNum) {
            this.wantUserNum = wantUserNum;
        }
    }
}
