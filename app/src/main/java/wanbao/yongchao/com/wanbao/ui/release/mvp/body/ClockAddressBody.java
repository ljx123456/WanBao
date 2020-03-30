package wanbao.yongchao.com.wanbao.ui.release.mvp.body;

public class ClockAddressBody {

    /**
     * longitude : 104.083628
     * latitude : 30.824224
     * pageIndex : 1
     * pageSize : 10
     */

    private String longitude;
    private String latitude;
    private String pageIndex;
    private String pageSize;

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

    public ClockAddressBody(String longitude, String latitude, String pageIndex, String pageSize) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
    }
}
