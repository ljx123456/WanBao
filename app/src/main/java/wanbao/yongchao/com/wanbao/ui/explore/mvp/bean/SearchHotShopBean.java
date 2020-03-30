package wanbao.yongchao.com.wanbao.ui.explore.mvp.bean;

import java.util.List;

public class SearchHotShopBean {

    /**
     * code : 0
     * message : 请求成功
     * data : [{"businessId":2,"businessName":"李四","clockNum":6},{"businessId":1,"businessName":"张三","clockNum":4}]
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
         * clockNum : 6
         */

        private int businessId;
        private String businessName;
        private int clockNum;

        public int getBusinessId() {
            return businessId;
        }

        public void setBusinessId(int businessId) {
            this.businessId = businessId;
        }

        public String getBusinessName() {
            return businessName;
        }

        public void setBusinessName(String businessName) {
            this.businessName = businessName;
        }

        public int getClockNum() {
            return clockNum;
        }

        public void setClockNum(int clockNum) {
            this.clockNum = clockNum;
        }
    }
}
