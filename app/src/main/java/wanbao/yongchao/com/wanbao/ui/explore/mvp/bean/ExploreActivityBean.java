package wanbao.yongchao.com.wanbao.ui.explore.mvp.bean;

import java.util.List;

public class ExploreActivityBean {

    /**
     * code : 0
     * message : 请求成功
     * data : [{"id":1,"title":"这是个活动的标题","previewImg":"https://www.wulihub.com.cn/go/JmVBkq/images/用户详情-基本资料-待审核/u736.jpg","previewContent":"这是个预览","startTime":"2019-10-29 09:32:08","endTime":"2019-10-31 09:32:12","wantNum":2,"sort":1,"wantAvatars":["http://baidu.com","http://baidu.com"]},{"id":2,"title":"这是个活动2的标题","previewImg":"https://www.wulihub.com.cn/go/JmVBkq/images/用户详情-基本资料-待审核/u736.jpg","previewContent":"这是个预览","startTime":"2019-10-24 09:32:08","endTime":"2019-10-30 09:32:12","wantNum":0,"wantAvatars":[]}]
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
         * previewImg : https://www.wulihub.com.cn/go/JmVBkq/images/用户详情-基本资料-待审核/u736.jpg
         * previewContent : 这是个预览
         * startTime : 2019-10-29 09:32:08
         * endTime : 2019-10-31 09:32:12
         * wantNum : 2
         * sort : 1
         * wantAvatars : ["http://baidu.com","http://baidu.com"]
         */

        private int id;
        private String title;
        private String previewImg;
        private String previewContent;
        private String startTime;
        private String endTime;
        private int wantNum;
        private int sort;
        private List<String> wantAvatars;

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

        public String getPreviewImg() {
            return previewImg;
        }

        public void setPreviewImg(String previewImg) {
            this.previewImg = previewImg;
        }

        public String getPreviewContent() {
            return previewContent;
        }

        public void setPreviewContent(String previewContent) {
            this.previewContent = previewContent;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public int getWantNum() {
            return wantNum;
        }

        public void setWantNum(int wantNum) {
            this.wantNum = wantNum;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public List<String> getWantAvatars() {
            return wantAvatars;
        }

        public void setWantAvatars(List<String> wantAvatars) {
            this.wantAvatars = wantAvatars;
        }
    }
}
