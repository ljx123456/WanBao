package wanbao.yongchao.com.wanbao.ui.main.mvp.bean;

import java.util.ArrayList;
import java.util.List;

public class CityBean {

    /**
     * code : 0
     * message : 请求成功
     * data : [{"cityId":2368,"name":"成都市","image":"http://m.tuniucdn.com/fb2/t1/G1/M00/43/4B/Cii9EVb1Ap6IO-GdAATPPRw43MIAACxqAOqcYEABM9V898_w500_h280_c1_t0.jpg"}]
     */

    private int code;
    private String message;
    private ArrayList<DataBean> data;

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

    public ArrayList<DataBean> getData() {
        return data;
    }

    public void setData(ArrayList<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * cityId : 2368
         * name : 成都市
         * image : http://m.tuniucdn.com/fb2/t1/G1/M00/43/4B/Cii9EVb1Ap6IO-GdAATPPRw43MIAACxqAOqcYEABM9V898_w500_h280_c1_t0.jpg
         */

        private int cityId;
        private String name;
        private String image;
        private String shortname;

        public String getShortname() {
            return shortname;
        }

        public void setShortname(String shortname) {
            this.shortname = shortname;
        }

        public int getCityId() {
            return cityId;
        }

        public void setCityId(int cityId) {
            this.cityId = cityId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }
}
