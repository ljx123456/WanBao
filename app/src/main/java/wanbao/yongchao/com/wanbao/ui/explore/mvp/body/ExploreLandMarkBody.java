package wanbao.yongchao.com.wanbao.ui.explore.mvp.body;

public class ExploreLandMarkBody {

    /**
     * cityId : 1
     * pageIndex : 1
     * pageSize : 10
     */

    private int cityId;
    private int pageIndex;
    private int pageSize;

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
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

    public ExploreLandMarkBody(int cityId, int pageIndex, int pageSize) {
        this.cityId = cityId;
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
    }
}
