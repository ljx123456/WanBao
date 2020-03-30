package wanbao.yongchao.com.wanbao.ui.main.mvp.body;

public class AddressHomePageBody {

    /**
     * cityMapId : 3
     * lat : 36.08
     * lng : 106.23
     */

    private String cityMapId;
    private String lat;
    private String lng;

    public String getCityMapId() {
        return cityMapId;
    }

    public void setCityMapId(String cityMapId) {
        this.cityMapId = cityMapId;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public AddressHomePageBody(String cityMapId, String lat, String lng) {
        this.cityMapId = cityMapId;
        this.lat = lat;
        this.lng = lng;
    }

    public AddressHomePageBody(String cityMapId) {
        this.cityMapId = cityMapId;
    }
}
