package wanbao.yongchao.com.wanbao.ui.main.mvp.bean;

import java.util.List;

public class HotTopicBean {


    /**
     * code : 0
     * message : 请求成功
     * data : [{"id":1,"name":"#话题#","dynamicNum":2}]
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
         * id : 1
         * name : #话题#
         * dynamicNum : 2
         */

        private int id;
        private String name;
        private int dynamicNum;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getDynamicNum() {
            return dynamicNum;
        }

        public void setDynamicNum(int dynamicNum) {
            this.dynamicNum = dynamicNum;
        }
    }
}
