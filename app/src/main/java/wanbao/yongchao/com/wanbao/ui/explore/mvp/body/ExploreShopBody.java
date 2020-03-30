package wanbao.yongchao.com.wanbao.ui.explore.mvp.body;

public class ExploreShopBody {


    /**
     * cityId : 512
     * sort : 1
     * type : 16
     * lng : 105.1364313
     * lat : 50.1325340
     * like :
     * pageIndex : 1
     * pageSize : 10
     */

    private String cityId;
    private int sort;
    private int type;
    private String lng;
    private String lat;
    private String like;
    private int pageIndex;
    private int pageSize;

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
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
