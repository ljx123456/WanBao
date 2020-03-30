package wanbao.yongchao.com.wanbao.ui.main.mvp.bean;

import java.math.BigDecimal;
import java.util.List;

public class SearchAddressBean {


    /**
     * code : 0
     * message : 请求成功
     * data : [{"cityMapId":3,"cityMapName":"鸿恩寺森林公园","cityMapAddress":"重庆市重庆市江北区鸿恩路","type":2,"km":"269.4729145545619"}]
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
         * cityMapId : 3
         * cityMapName : 鸿恩寺森林公园
         * cityMapAddress : 重庆市重庆市江北区鸿恩路
         * type : 2
         * km : 269.4729145545619
         */

        private int cityMapId;
        private String cityMapName;
        private String cityMapAddress;
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
