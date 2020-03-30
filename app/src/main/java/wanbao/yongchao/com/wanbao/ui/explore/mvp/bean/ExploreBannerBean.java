package wanbao.yongchao.com.wanbao.ui.explore.mvp.bean;

import java.util.List;

public class ExploreBannerBean {

    /**
     * code : 0
     * message : 请求成功
     * data : [{"id":10,"image":"http://pic.bixinyule.com/service/banner/service_banner_05_01.png","name":"banner测试3","objectType":3,"objectId":1}]
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
         * id : 10
         * image : http://pic.bixinyule.com/service/banner/service_banner_05_01.png
         * name : banner测试3
         * objectType : 3
         * objectId : 1
         */

        private int id;
        private String image;
        private String name;
        private int objectType;
        private int objectId;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getObjectType() {
            return objectType;
        }

        public void setObjectType(int objectType) {
            this.objectType = objectType;
        }

        public int getObjectId() {
            return objectId;
        }

        public void setObjectId(int objectId) {
            this.objectId = objectId;
        }
    }
}
