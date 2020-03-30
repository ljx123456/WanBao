package wanbao.yongchao.com.wanbao.ui.main.mvp.bean;

import java.util.ArrayList;
import java.util.List;

public class TopicBean {

    /**
     * code : 0
     * message : 请求成功
     * data : [{"id":5,"name":"#rabbitmq#"}]
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
         * id : 5
         * name : #rabbitmq#
         */

        private int id;
        private String name;
        private boolean flag=false;

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

        public boolean isFlag() {
            return flag;
        }

        public void setFlag(boolean flag) {
            this.flag = flag;
        }
    }
}
