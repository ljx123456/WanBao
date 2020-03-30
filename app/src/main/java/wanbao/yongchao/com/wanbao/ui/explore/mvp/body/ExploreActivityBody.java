package wanbao.yongchao.com.wanbao.ui.explore.mvp.body;

public class ExploreActivityBody {

    /**
     * cityId : 1
     * like : 这
     * type : 1
     * pageIndex : 1
     * pageSize : 10
     */

    private int cityId;
    private String like;
    private String type;
    private int pageIndex;
    private int pageSize;

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

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


}
