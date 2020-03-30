package wanbao.yongchao.com.wanbao.ui.release.mvp.bean;

import java.util.List;

public class ClockAddressBean {

    /**
     * code : 0
     * message : 请求成功
     * data : [{"address":"四川省成都市新都区新都区","name":"余家巷子","cityName":"成都市","clockInNum":"1000","id":"B001786RF6"}]
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
         * address : 四川省成都市新都区新都区
         * name : 余家巷子
         * cityName : 成都市
         * clockInNum : 1000
         * id : B001786RF6
         */

        private String address;
        private String name;
        private String cityName;
        private String clockInNum;
        private String id;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        public String getClockInNum() {
            return clockInNum;
        }

        public void setClockInNum(String clockInNum) {
            this.clockInNum = clockInNum;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
