package wanbao.yongchao.com.wanbao.ui.login.mvp.bean;

import java.util.List;

public class AreaBean {

    /**
     * code : 0
     * message : 请求成功
     * data : [{"key":"A","cities":[{"id":1098,"name":"安庆市","level":"2"}]}]
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
         * key : A
         * cities : [{"id":1098,"name":"安庆市","level":"2"}]
         */

        private String key;
        private List<CitiesBean> cities;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public List<CitiesBean> getCities() {
            return cities;
        }

        public void setCities(List<CitiesBean> cities) {
            this.cities = cities;
        }

        public static class CitiesBean {
            /**
             * id : 1098
             * name : 安庆市
             * level : 2
             */

            private int id;
            private String name;
            private String level;
            private Boolean isFlag=false;

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

            public String getLevel() {
                return level;
            }

            public void setLevel(String level) {
                this.level = level;
            }

            public Boolean getFlag() {
                return isFlag;
            }

            public void setFlag(Boolean flag) {
                isFlag = flag;
            }
        }
    }
}
