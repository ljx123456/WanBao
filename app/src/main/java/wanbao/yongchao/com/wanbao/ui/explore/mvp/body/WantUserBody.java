package wanbao.yongchao.com.wanbao.ui.explore.mvp.body;

public class WantUserBody {

    /**
     * cityMapId : 1
     * pageIndex : 1
     * pageSize : 10
     */

    private String cityMapId;
    private int pageIndex;
    private int pageSize;

    public String getCityMapId() {
        return cityMapId;
    }

    public void setCityMapId(String cityMapId) {
        this.cityMapId = cityMapId;
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

    public WantUserBody(String cityMapId, int pageIndex, int pageSize) {
        this.cityMapId = cityMapId;
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
    }
}
