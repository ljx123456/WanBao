package wanbao.yongchao.com.wanbao.ui.main.mvp.bean;

import java.math.BigDecimal;
import java.util.List;

public class SearchLandMarkBean {

    /**
     * code : 0
     * message : 请求成功
     * data : [{"cityMapId":2,"cityMapName":"小院子","cityMapAddress":"成都市-九远桥","avatar":"http://store.is.autonavi.com/showpic/a0350a170cd18c731e0bd87295a7fa3b","type":1,"km":"0.00009835206249420025"}]
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
         * cityMapId : 2
         * cityMapName : 小院子
         * cityMapAddress : 成都市-九远桥
         * avatar : http://store.is.autonavi.com/showpic/a0350a170cd18c731e0bd87295a7fa3b
         * type : 1
         * km : 0.00009835206249420025
         */

        private int cityMapId;
        private String cityMapName;
        private String cityMapAddress;
        private String avatar;
        private int type;
        private BigDecimal km=new BigDecimal("0");

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

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public BigDecimal getKm() {
            return km;
        }

        public void setKm(BigDecimal km) {
            this.km = km;
        }
    }
}
