package wanbao.yongchao.com.wanbao.ui.set.mvp.body;

public class UpdateLocationBody {

    /**
     * longitude : 104.065735
     * latitude : 30.659462
     */

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

    public UpdateLocationBody(String longitude, String latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }
}
