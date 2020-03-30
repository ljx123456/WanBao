package wanbao.yongchao.com.wanbao.ui.main.mvp.body;

public class SearchBody {

    /**
     * like : 1
     * type : 7
     * state : 1
     * pageIndex : 1
     * pageSize : 10
     * lng : 104.10194
     * lat : 30.782618
     */

    private String like;
    private String type;
    private String state;
    private String pageIndex;
    private String pageSize;
    private String lng;
    private String lat;

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(String pageIndex) {
        this.pageIndex = pageIndex;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public SearchBody(String like, String type, String state, String pageIndex, String pageSize) {
        this.like = like;
        this.type = type;
        this.state = state;
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
    }

    public SearchBody(String like, String type, String pageIndex, String pageSize) {
        this.like = like;
        this.type = type;
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
    }

    public SearchBody(String like, String type, String pageIndex, String pageSize, String lng, String lat) {
        this.like = like;
        this.type = type;
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.lng = lng;
        this.lat = lat;
    }

    public SearchBody(String like, String type, String state, String pageIndex, String pageSize, String lng, String lat) {
        this.like = like;
        this.type = type;
        this.state = state;
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.lng = lng;
        this.lat = lat;
    }
}
