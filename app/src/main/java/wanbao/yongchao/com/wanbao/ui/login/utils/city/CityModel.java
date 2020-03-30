package wanbao.yongchao.com.wanbao.ui.login.utils.city;

import java.util.ArrayList;

public class CityModel {
    private String key;
    private ArrayList<CitiesBean.CityBean> list;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public ArrayList<CitiesBean.CityBean> getList() {
        return list;
    }

    public void setList(ArrayList<CitiesBean.CityBean> list) {
        this.list = list;
    }

    public CityModel() {
    }

    public CityModel(String key, ArrayList<CitiesBean.CityBean> list) {
        this.key = key;
        this.list = list;
    }
}
