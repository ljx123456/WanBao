package wanbao.yongchao.com.wanbao.ui.main.mvp.body;

public class HomePageLocationCommunityBody {

    /**
     * locationId : B001786RF6
     * pageIndex : 1
     * pageSize : 10
     */

    private String locationId;
    private int pageIndex;
    private int pageSize;

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
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

    public HomePageLocationCommunityBody(String locationId, int pageIndex, int pageSize) {
        this.locationId = locationId;
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
    }
}
