package wanbao.yongchao.com.wanbao.utils.pickers;

public class AddsModel {
    private String city;
    private String province;

    public AddsModel(String city, String province) {
        this.city = city;
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }
}
