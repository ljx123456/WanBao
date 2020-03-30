package wanbao.yongchao.com.wanbao.ui.mine.mvp.bean;

public class AuthInfoBean {

    /**
     * code : 0
     * message : 请求成功
     * data : {"auditState":3,"createTime":"2019-11-26 15:04:56","authEndTime":"2019-11-30 00:00:00","passTime":"2019-11-29 10:23:23","name":"张三","idCardNo":"1234567984","type":"报纸","authType":2,"handPhoto":"http://store.is.autonavi.com/showpic/23c7e641a33a98e0f994674807e5cca9","idCardFront":"http://store.is.autonavi.com/showpic/23c7e641a33a98e0f994674807e5cca9","idCardBack":"http://store.is.autonavi.com/showpic/23c7e641a33a98e0f994674807e5cca9","industryData":"http://store.is.autonavi.com/showpic/23c7e641a33a98e0f994674807e5cca9"}
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
         * auditState : 3
         * createTime : 2019-11-26 15:04:56
         * authEndTime : 2019-11-30 00:00:00
         * passTime : 2019-11-29 10:23:23
         * name : 张三
         * idCardNo : 1234567984
         * type : 报纸
         * authType : 2
         * handPhoto : http://store.is.autonavi.com/showpic/23c7e641a33a98e0f994674807e5cca9
         * idCardFront : http://store.is.autonavi.com/showpic/23c7e641a33a98e0f994674807e5cca9
         * idCardBack : http://store.is.autonavi.com/showpic/23c7e641a33a98e0f994674807e5cca9
         * industryData : http://store.is.autonavi.com/showpic/23c7e641a33a98e0f994674807e5cca9
         */

        private int auditState;
        private String createTime;
        private String authEndTime;
        private String passTime;
        private String refuseTime;
        private String name;
        private String idCardNo;
        private String type;
        private int authType;
        private String handPhoto;
        private String idCardFront;
        private String idCardBack;
        private String industryData;

        public String getRefuseTime() {
            return refuseTime;
        }

        public void setRefuseTime(String refuseTime) {
            this.refuseTime = refuseTime;
        }

        public int getAuditState() {
            return auditState;
        }

        public void setAuditState(int auditState) {
            this.auditState = auditState;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getAuthEndTime() {
            return authEndTime;
        }

        public void setAuthEndTime(String authEndTime) {
            this.authEndTime = authEndTime;
        }

        public String getPassTime() {
            return passTime;
        }

        public void setPassTime(String passTime) {
            this.passTime = passTime;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIdCardNo() {
            return idCardNo;
        }

        public void setIdCardNo(String idCardNo) {
            this.idCardNo = idCardNo;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getAuthType() {
            return authType;
        }

        public void setAuthType(int authType) {
            this.authType = authType;
        }

        public String getHandPhoto() {
            return handPhoto;
        }

        public void setHandPhoto(String handPhoto) {
            this.handPhoto = handPhoto;
        }

        public String getIdCardFront() {
            return idCardFront;
        }

        public void setIdCardFront(String idCardFront) {
            this.idCardFront = idCardFront;
        }

        public String getIdCardBack() {
            return idCardBack;
        }

        public void setIdCardBack(String idCardBack) {
            this.idCardBack = idCardBack;
        }

        public String getIndustryData() {
            return industryData;
        }

        public void setIndustryData(String industryData) {
            this.industryData = industryData;
        }
    }
}
