package wanbao.yongchao.com.wanbao.ui.main.mvp.body;

public class CommunityBody {

    /**
     * topicId : 1
     */

    private String topicId;
    private int pageIndex;
    private int pageSize;
    private String longitude;
    private String latitude;

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public CommunityBody(String topicId, int pageIndex, int pageSize) {
        this.topicId = topicId;
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
    }

    public CommunityBody(int pageIndex, int pageSize) {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
    }

    public CommunityBody(String topicId, int pageIndex, int pageSize, String longitude, String latitude) {
        this.topicId = topicId;
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public CommunityBody(int pageIndex, int pageSize, String longitude, String latitude) {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.longitude = longitude;
        this.latitude = latitude;
    }
}
