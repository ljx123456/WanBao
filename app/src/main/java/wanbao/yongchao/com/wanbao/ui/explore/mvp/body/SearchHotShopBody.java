package wanbao.yongchao.com.wanbao.ui.explore.mvp.body;

public class SearchHotShopBody {

    /**
     * pageSize : 10
     * pageIndex : 1
     * cityId : 2368
     */

    private int pageSize;
    private int pageIndex;
    private int cityId;

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public SearchHotShopBody( int cityId) {
        this.cityId = cityId;
    }
}
