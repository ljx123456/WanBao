package wanbao.yongchao.com.wanbao.ui.explore.mvp.bean;

import java.util.List;

public class SearchHotActivitiesBean {

    /**
     * code : 0
     * message : 请求成功
     * data : [{"id":1,"title":"这是个活动的标题","wantNum":1}]
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
         * title : 这是个活动的标题
         * wantNum : 1
         */

        private int id;
        private String title;
        private int wantNum;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getWantNum() {
            return wantNum;
        }

        public void setWantNum(int wantNum) {
            this.wantNum = wantNum;
        }
    }
}
